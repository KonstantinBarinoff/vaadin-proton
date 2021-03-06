package proton.base;

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
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import util.ProtonConfirmationDialog;
import util.ProtonStrings;
import util.ProtonWarningDialog;

import java.util.Arrays;
import java.util.NoSuchElementException;


@Slf4j
//@CssImport(themeFor = "vaadin-grid", value = "./styles/layout-with-border.css")
public abstract class BaseDictViewEditor<E extends BaseDict, S extends BaseService<E>> extends Dialog implements KeyNotifier {

    private enum EditorMode {
        /** Форма редактора открыта для редактирования строки таблицы */
        MODE_EDIT,
        /**
         * Форма редактора открыта при вставке строки в таблицу
         */
        MODE_INSERT
    }

    private EditorMode editorMode;

    /**
     * Ссылка на View-владельца, которому принадлежит данная форма редактирования
     */
    private BaseDictView<E, S> ownerDictView;

    protected final TextField nameField = new TextField("Наименование (Alt+N)");
    protected final TextField descriptionField = new TextField("Примечание");

    protected final Button saveButton = new Button(ProtonStrings.SAVE, VaadinIcon.CHECK.create());
    protected final Button revertButton = new Button(ProtonStrings.REVERT, VaadinIcon.REFRESH.create());
    protected final Button closeButton = new Button(ProtonStrings.CLOSE, VaadinIcon.CLOSE.create());

    protected BaseService<E> service;
    protected Binder<E> binder = null;
    protected final FormLayout form = new FormLayout();
    protected E item;

    public interface OnSaveHandler {
        void onSave();
    }
    private OnSaveHandler onSaveHandler;
    public void setOnSave(OnSaveHandler h) {
        onSaveHandler = h;
    }

    public interface OnCancelHandler {
        void onCancel();
    }
    private OnCancelHandler onCancelHandler;
    public void setOnCancel(OnCancelHandler h) {
        onCancelHandler = h;
    }

    protected void closeEditor() {
        if (binder.hasChanges()) {
            ProtonConfirmationDialog dialog = new ProtonConfirmationDialog("Есть несохраненные изменения. Вы уверены?");
            dialog.showConfirmation(e -> {
                    dialog.close();
                    return;
            });
        }
        onCancelHandler.onCancel();
    }

    public BaseDictViewEditor() {
        super();
    }

    public void setupView() {
        setResizable(true);
        setDraggable(true);
        setModal(true);

        setupLayout();
        addKeyPressListener(Key.ENTER, e -> saveItem());
    }

    /**
     * Создаем поле, настраиваем валидатор, добавляем поле в форму
     */
    public void setupFields() {
        binder.forField(nameField)
                .asRequired(ProtonStrings.REQUIRED)
                .withValidator(string -> !string.isBlank(), ProtonStrings.REQUIRED)
                .bind(E::getName, E::setName);
        nameField.addFocusShortcut(Key.KEY_N, KeyModifier.ALT);
        nameField.setValueChangeMode(ValueChangeMode.EAGER);
        form.add(nameField, 4);

        binder.forField(descriptionField)
                .withValidator(new StringLengthValidator(ProtonStrings.NOT_IN_RANGE, 0, 50)) // MSSQL VARCHAR(50)
                .bind(E::getDescription, E::setDescription);
        descriptionField.setValueChangeMode(ValueChangeMode.EAGER);
        descriptionField.getElement().setProperty("placeholder", "");
        form.add(descriptionField, 4);
    }

    public void setupLayout() {
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3),
                new FormLayout.ResponsiveStep("48em", 4),
                new FormLayout.ResponsiveStep("56em", 6),
                new FormLayout.ResponsiveStep("64em", 8));

        setCloseOnOutsideClick(false);
        setCloseOnEsc(true);

        setupFields();

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

    void saveItem() {
        if (!binder.validate().isOk())
            return;
        try {
            binder.writeBean(item);
            service.save(item);
            ownerDictView.grid.getDataProvider().refreshItem(item);

//            if (editorMode == EditorMode.MODE_EDIT) { // При редактировании достаточно обновить строку таблицы
//                ownerDictView.refreshGrid();
//                //ownerDictView.grid.getDataProvider().refreshItem(item);
//            }
//            if (editorMode == EditorMode.MODE_INSERT) {
//                ownerDictView.refreshGrid();
//            }
            onSaveHandler.onSave();
        } catch (DataIntegrityViolationException e) {
            new ProtonWarningDialog(ProtonStrings.ERROR, NestedExceptionUtils.getMostSpecificCause(e).getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
        } catch (Exception e) {
            editItem(item);
            new ProtonWarningDialog(ProtonStrings.OPTLOCK_UPDATE_ERROR, NestedExceptionUtils.getMostSpecificCause(e).getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }


    public final void editItem(@NotNull E i) {
        editorMode = EditorMode.MODE_EDIT;
        if (!service.existsById(i.getId())) {
            throw new NoSuchElementException(ProtonStrings.RECORD_NOT_FOUND + ": " + i);
        }
        if (i.isPersisted()) {
            item = service.findById(i.getId()).get();
        } else {
            assert true : "Вызов editItem c non Persisted аргументом";
            item = i;
        }
        revertButton.setEnabled(true);
//        binder.setBean(item);
        binder.readBean(item);
        nameField.focus();
        log.debug("EDIT ITEM: {}", item);
    }

    public final void newItem(@NotNull E i) {
        editorMode = EditorMode.MODE_INSERT;
        revertButton.setEnabled(false);
        if (i.isPersisted()) {
            assert true : "Вызов newItem c Persisted аргументом";
            item = service.findById(i.getId()).get();
        } else {
            item = i;
        }
//        binder.setBean(item);
        binder.readBean(item);
        nameField.focus();
        log.debug("NEW ITEM: {}", item);
    }

    public BaseDictView<E, S> getOwnerDictView() {
        return ownerDictView;
    }

    public void setOwnerDictView(BaseDictView<E, S> ownerDictView) {
        this.ownerDictView = ownerDictView;
    }

}
