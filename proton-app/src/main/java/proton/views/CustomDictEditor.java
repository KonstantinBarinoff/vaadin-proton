package proton.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.BigDecimalRangeValidator;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import proton.entities.CustomDict;
import proton.repositories.CustomDictRepo;
import util.ProtonConfirmationDialog;
import util.ProtonDatePicker;
import util.ProtonStrings;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;



@SpringComponent
@UIScope
@org.springframework.transaction.annotation.Transactional
@Slf4j
public class CustomDictEditor extends Dialog implements KeyNotifier {

    private final TextField nameField = new TextField("Наименование (5-20 знаков) (Alt+N)");
    private final IntegerField numberField = new IntegerField("Количество (Integer) (Alt+K)");
    private final BigDecimalField priceField = new BigDecimalField("Цена (BigDecimal)");
    private final ProtonDatePicker dateField = new ProtonDatePicker("Дата (будущая)");
    private final EmailField emailField = new EmailField("E-mail");
    private final TextField descriptionField = new TextField("Примечание");
    private final Checkbox checkedField = new Checkbox("Отметка");

    private final Button saveButton = new Button(ProtonStrings.SAVE, VaadinIcon.CHECK.create());
    private final Button revertButton = new Button(ProtonStrings.REVERT, VaadinIcon.REFRESH.create());
    private final Button closeButton = new Button(ProtonStrings.CLOSE, VaadinIcon.CLOSE.create());
    private final Button deleteButton = new Button(ProtonStrings.DELETE, VaadinIcon.TRASH.create());

    private final CustomDictRepo repository;
    private final Binder<CustomDict> binder = new Binder<>(CustomDict.class);
    private final FormLayout form = new FormLayout();
    private CustomDict item;

    private ChangeHandler changeHandler;

    private File file;
//    public static final String PERSISTENCE_UNIT_NAME = "persistenceUnit";
//    private EntityManagerFactory emf;

    @Autowired
    public CustomDictEditor(CustomDictRepo repository, EntityManagerFactory emf) {
        this.repository = repository;
        setupLayout();
        setupFields();
        // bind using naming convention
        // binder.bindInstanceFields(this);
        addKeyPressListener(Key.ENTER, e -> save());

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);

        Div output = new Div(new Text("(no image file uploaded yet)"));
        add(upload, output);

        // Configure upload component
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload.addSucceededListener(event -> {
            try {
                byte[] bytes = IOUtils.toByteArray(buffer.getInputStream());
//			emf = javax.persistence.Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();

                // Upload image to database to user id = 1
                Query query = em.createQuery("UPDATE CustomDict u SET u.image = :data WHERE u.id = :id");
                query.setParameter("data", bytes);
                query.setParameter("id", item.getId());
                query.executeUpdate();
                em.getTransaction().commit();

//			// Retrieve image from database from user id = 1
//			q = em.createQuery("SELECT u.image FROM CustomDict u WHERE u.id = '83'");
//			bytes = (byte[]) q.getSingleResult();
//			// Set the image from database
//			output.getElement().setAttribute("src", new StreamResource("", this::loadFile));
                em.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        upload.addFailedListener(event -> {
            output.removeAll();
            output.add(new Text("Upload failed: " + event.getReason()));
        });
    }

    /**
     * Load a file from local filesystem.
     */
    public InputStream loadFile() {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            log.warn("Failed to create InputStream for: {}", this.file.getAbsolutePath(), e);
        }
        return null;
    }

    /**
     * Receive a uploaded file to a file.
     */
    public OutputStream receiveUpload(String originalFileName, String MIMEType) {
//	this.originalFileName = originalFileName;
//	this.mimeType = MIMEType;
        try {
            // Create a temporary file for example, you can provide your file here.
            this.file = File.createTempFile("prefix-", "-suffix");
            file.deleteOnExit();
            return new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            log.warn("Failed to create InputStream for: {}", this.file.getAbsolutePath(), e);
        } catch (IOException e) {
            log.warn("Failed to create InputStream for: {}", this.file.getAbsolutePath(), e);
        }
        return null;
    }

    public void setupFields() {
        binder.forField(nameField)
                .asRequired(ProtonStrings.REQUIRED)
                .withValidator(new StringLengthValidator(ProtonStrings.NOT_IN_RANGE, 5, 20)) // MSSQL VARCHAR(20)
                .bind(CustomDict::getName, CustomDict::setName);
        nameField.addFocusShortcut(Key.KEY_N, KeyModifier.ALT);
        nameField.setValueChangeMode(ValueChangeMode.EAGER);

        binder.forField(numberField)
                .withValidator(new IntegerRangeValidator(ProtonStrings.NOT_IN_RANGE, 1, Integer.MAX_VALUE))
                .asRequired(ProtonStrings.REQUIRED)
                .bind(CustomDict::getNumber, CustomDict::setNumber);
        numberField.addFocusShortcut(Key.KEY_K, KeyModifier.ALT);
        numberField.setValueChangeMode(ValueChangeMode.EAGER);

        binder.forField(priceField) //MSSQL DECIMAL(18,2)
                .asRequired(ProtonStrings.REQUIRED)
                .withValidator(new BigDecimalRangeValidator(ProtonStrings.NOT_IN_RANGE, BigDecimal.valueOf(0.01), BigDecimal.valueOf(9999999999999999L)))
                .bind(CustomDict::getPrice, CustomDict::setPrice);
        priceField.setValueChangeMode(ValueChangeMode.EAGER);

        binder.forField(emailField)
//		.withValidator(new EmailValidator(ProtonStrings.BAD_EMAIL))
                .bind(CustomDict::getEmail, CustomDict::setEmail);

        binder.forField(dateField).bind(CustomDict::getDate, CustomDict::setDate);
        dateField.setMin(LocalDate.now().plusDays(1));
        dateField.getElement().addEventListener("click", e -> {
            Notification.show("Element Event Listener clock");
        });

        binder.forField(descriptionField)
                .withValidator(new StringLengthValidator(ProtonStrings.NOT_IN_RANGE, 0, 50)) // MSSQL VARCHAR(50)
                .bind(CustomDict::getDescription, CustomDict::setDescription);
        descriptionField.setValueChangeMode(ValueChangeMode.EAGER);
        descriptionField.getElement().setProperty("placeholder", "Пример");

        binder.bind(checkedField, "checked");  // Binding with property name w/o getter & setter

    }

    public void setupLayout() {
        setModal(true);
        setCloseOnOutsideClick(false);
        setCloseOnEsc(true);

        form.add(nameField, numberField, priceField, dateField, emailField, descriptionField, checkedField);

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
            dialog.showConfirmation(__ -> {
                delete();
                dialog.close();
            });
        });
        return new HorizontalLayout(saveButton, revertButton, deleteButton, closeButton);
    }

    void delete() {
        log.debug("DELETE ITEM: {}", item);
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
