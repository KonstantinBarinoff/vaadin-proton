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
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import proton.entities.BaseDict;
import proton.repositories.BaseRepo;
import proton.repositories.BaseService;
import util.ProtonStrings;
import util.ProtonWarningDialog;

import java.util.Arrays;
import java.util.NoSuchElementException;


@Slf4j
public abstract class BaseFormDictEditor<E extends BaseDict, S extends BaseService<E>> extends Dialog implements KeyNotifier {

    private final TextField nameField = new TextField("Наименование (Alt+N)");
    private final TextField descriptionField = new TextField("Примечание");

    private final Button saveButton = new Button(ProtonStrings.SAVE, VaadinIcon.CHECK.create());
    private final Button revertButton = new Button(ProtonStrings.REVERT, VaadinIcon.REFRESH.create());
    private final Button closeButton = new Button(ProtonStrings.CLOSE, VaadinIcon.CLOSE.create());

    protected BaseRepo<E> repo;
    protected Binder<E> binder = null;
    private S service = null;
    private final FormLayout form = new FormLayout();
    private E item;

    private ChangeHandler changeHandler;

    public BaseFormDictEditor(S service) {
        this.service = service;
    }

    public void setupView() {
        setupLayout();
        setupFields();
        addKeyPressListener(Key.ENTER, e -> saveItem());
    }

    public void setupFields() {
        binder.forField(nameField)
                .asRequired(ProtonStrings.REQUIRED)
                .withValidator(string -> !string.isBlank(), ProtonStrings.REQUIRED)
                .bind(E::getName, E::setName);
        nameField.addFocusShortcut(Key.KEY_N, KeyModifier.ALT);
        nameField.setValueChangeMode(ValueChangeMode.EAGER);

        binder.forField(descriptionField)
                .withValidator(new StringLengthValidator(ProtonStrings.NOT_IN_RANGE, 0, 50)) // MSSQL VARCHAR(50)
                .bind(E::getDescription, E::setDescription);
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
        saveButton.addClickListener(e -> saveItem());
        revertButton.addClickListener(e -> editItem(item));
        closeButton.addClickListener(e -> closeEditor());
        return new HorizontalLayout(saveButton, revertButton, closeButton);
    }

    void closeEditor() {
        changeHandler.onChange();
    }

    void saveItem() {
        if (!binder.validate().isOk())
            return;
        try {
            repo.save(item);
            changeHandler.onChange();
        } catch (Exception e) {
            editItem(item);
            new ProtonWarningDialog(ProtonStrings.OPTLOCK_UPDATE_ERROR);
            new ProtonWarningDialog(e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editItem(@NotNull E i) {
        if (!repo.existsById(i.getId())) {
            throw new NoSuchElementException(ProtonStrings.RECORD_NOT_FOUND + ": " + i);
        }
        if (i.isPersisted()) {
            item = repo.findById(i.getId()).get();
        } else {
            item = i;
        }
        revertButton.setEnabled(true);
        binder.setBean(item);
        nameField.focus();
        log.debug("EDIT ITEM: {}", item);
    }

    public final void newItem(@NotNull E i) {
        revertButton.setEnabled(false);
        if (i.isPersisted()) {
            item = repo.findById(i.getId()).get();
        } else {
            item = i;
        }
        binder.setBean(item);
        nameField.focus();
//        log.debug("NEW ITEM: {}", item);
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}
