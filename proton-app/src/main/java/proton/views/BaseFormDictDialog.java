package proton.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import proton.entities.CustomDict;
import proton.repositories.CustomDictRepo;
import util.ProtonConfirmationDialog;
import util.ProtonStrings;


@SpringComponent
@UIScope
@org.springframework.transaction.annotation.Transactional
@Slf4j
public class BaseFormDictDialog extends Dialog implements KeyNotifier {

    private final TextField nameField = new TextField("Наименование (Alt+N)");
    private final TextField descriptionField = new TextField("Примечание");

    private final Button saveButton = new Button(ProtonStrings.SAVE, VaadinIcon.CHECK.create());
    private final Button revertButton = new Button(ProtonStrings.REVERT, VaadinIcon.REFRESH.create());
    private final Button closeButton = new Button(ProtonStrings.CLOSE, VaadinIcon.CLOSE.create());
    private final Button deleteButton = new Button(ProtonStrings.DELETE, VaadinIcon.TRASH.create());

    private final CustomDictRepo repository;
    private final Binder<CustomDict> binder = new Binder<>(CustomDict.class);
    private final FormLayout form = new FormLayout();
    private CustomDict item;

    private ChangeHandler changeHandler;

    @Autowired
    public BaseFormDictDialog(CustomDictRepo repository) {
        this.repository = repository;
        setupLayout();
        setupFields();
        addKeyPressListener(Key.ENTER, e -> save());
    }

    public void setupFields() {


        binder.forField(nameField)
                .asRequired(ProtonStrings.REQUIRED)
                .withValidator(string -> !string.isBlank(), ProtonStrings.REQUIRED)
                .bind(CustomDict::getName, CustomDict::setName);
        nameField.addFocusShortcut(Key.KEY_N, KeyModifier.ALT);
        nameField.setValueChangeMode(ValueChangeMode.EAGER);

        binder.forField(descriptionField)
                .withValidator(new StringLengthValidator(ProtonStrings.NOT_IN_RANGE, 0, 50)) // MSSQL VARCHAR(50)
                .bind(CustomDict::getDescription, CustomDict::setDescription);
        descriptionField.setValueChangeMode(ValueChangeMode.EAGER);
        descriptionField.getElement().setProperty("placeholder", "Пример");

    }

    public void setupLayout() {
        setModal(true);
        setCloseOnOutsideClick(false);
        setCloseOnEsc(true);

        form.add(nameField, descriptionField);

        add(form);
        add(setupButtons());
    }

    public HorizontalLayout setupButtons() {
        saveButton.getElement().getThemeList().add("primary");
        deleteButton.getElement().getThemeList().add("error");

        // wire action buttons to save, delete and reset
        saveButton.addClickListener(e -> save());
        revertButton.addClickListener(e -> editItem(item));
        closeButton.addClickListener(e -> closeEditor());
        deleteButton.addClickListener(e -> {

            ProtonConfirmationDialog dialog = new ProtonConfirmationDialog(ProtonStrings.DELETE_RECORD_Q);
            dialog.showConfirmation(l -> {
                delete();
                dialog.close();
            });
        });
        return new HorizontalLayout(saveButton, revertButton, deleteButton, closeButton);
    }

    void delete() {
        repository.delete(item);
        changeHandler.onChange();
    }

    void closeEditor() {
        changeHandler.onChange();
    }

    void save() {
        if (!binder.validate().isOk())
            return;
        repository.save(item);
        changeHandler.onChange();
        log.debug("SAVE ITEM: {}", item);
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editItem(CustomDict i) {
        revertButton.setEnabled(true);
        deleteButton.setEnabled(true);
        if (i == null) {
            return;
        }
        if (i.isPersisted()) {
            item = repository.findById(i.getId()).get();
        } else {
            item = i;
        }
        binder.setBean(item);
        nameField.focus();
        log.debug("EDIT ITEM: {}", item);
    }

    public final void newItem(CustomDict i) {
        revertButton.setEnabled(false);
        deleteButton.setEnabled(false);
        if (i == null) {
            return;
        }
        if (i.isPersisted()) {
            item = repository.findById(i.getId()).get();
        } else {
            item = i;
        }
        binder.setBean(item);
        nameField.focus();
        log.debug("NEW ITEM: {}", item);
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}
