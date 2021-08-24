package proton.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import proton.base.CustomDictionary;
import proton.repositories.CustomDictRepo;
import util.ProtonConfirmationDialog;
import util.ProtonNotification;
import util.ProtonProperties;
import util.ProtonStrings;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Route(value = "dictionary1-dialog", layout = MainView.class)
@PageTitle("CustomDict Dialog Editor")
public class CustomDictView extends VerticalLayout {

    private final CustomDictRepo repository;
    private final ProtonProperties properties;
    private final EntityManagerFactory emf;

    private final CustomDictViewEditor editor;
    // private final ImageForm imageForm;

    private final Grid<CustomDictionary> grid = new Grid<>();
//    private final Binder<Dictionary1> binder = new Binder<>(Dictionary1.class);

    private final Grid.Column<CustomDictionary> idColumn = grid.addColumn(CustomDictionary::getId).setHeader("Код");
    private final Grid.Column<CustomDictionary> nameColumn = grid.addColumn(CustomDictionary::getName)
            .setHeader("Наименование (не пуст.)");
    private final Grid.Column<CustomDictionary> numberColumn = grid.addColumn(CustomDictionary::getNumber)
            .setHeader("Количество (целое > 0)");
    private final Grid.Column<CustomDictionary> priceColumn = grid.addColumn(CustomDictionary::getPrice)
            .setHeader("Цена");
    private final Grid.Column<CustomDictionary> dateColumn = grid.addColumn(CustomDictionary::getDate)
            .setHeader("Дата");
    private final Grid.Column<CustomDictionary> descriptionColumn = grid.addColumn(CustomDictionary::getDescription)
            .setHeader("Примечание");
    private final Grid.Column<CustomDictionary> checkedColumn = grid.addColumn(i -> i.getChecked() ? "Да" : "")
            .setHeader("Отметка");
    private final Grid.Column<CustomDictionary> emailColumn = grid.addColumn(CustomDictionary::getEmail)
            .setHeader("E-mail");

    private final Button insertButton = new Button(ProtonStrings.INSERT, VaadinIcon.PLUS.create());
    private final Button deleteButton = new Button(ProtonStrings.DELETE, VaadinIcon.MINUS.create());
    private final Button refreshButton = new Button(ProtonStrings.REFRESH, VaadinIcon.REFRESH.create());
    private final Button editButton = new Button(ProtonStrings.EDIT, VaadinIcon.EDIT.create());
    private final Button imageButton = new Button(ProtonStrings.IMAGE, VaadinIcon.PICTURE.create());
    private final Button printButton = new Button("Печать страницы", VaadinIcon.PRINT.create());

    @Autowired
    // public Dictionary1DialogEditView(Dictionary1Repository repository,
    // Dictionary1Editor editor) {
    public CustomDictView(CustomDictRepo repository, ProtonProperties properties,
                          EntityManagerFactory emf) {
        this.repository = repository;
        this.properties = properties;
        this.emf = emf;
//	this.editor = editor;

        setupBrowserWindowResizeListener();

        setupLayout();
        setupGrid();

        editor = new CustomDictViewEditor(repository, emf);
        setupEditor();

        grid.setItems(repository.findAll());

        add(setupButtons());
        add(grid);

    }

    public void setupLayout() {
        setDefaultHorizontalComponentAlignment(Alignment.BASELINE);
        setSizeFull();

    }

    public void setupBrowserWindowResizeListener() {
        Page page = UI.getCurrent().getPage();
        page.addBrowserWindowResizeListener(
                e -> Notification.show("Window width=" + e.getWidth() + ", height=" + e.getHeight()));
    }

    public HorizontalLayout setupButtons() {

        deleteButton.setEnabled(false);
        editButton.setEnabled(false);
        imageButton.setEnabled(false);

        insertButton.addClickListener(e -> {
            editor.newItem(new CustomDictionary());
            editor.open();
        });

        editButton.addClickListener(e -> {
            editor.editItem(grid.getSelectedItems().stream().findFirst().get());
            editor.open();
        });

        deleteButton.addClickListener(event -> {
            if (grid.getSelectedItems().isEmpty()) {
                ProtonNotification.showWarning(ProtonStrings.RECORD_NOT_SELECTED);
                return;
            }
            ProtonConfirmationDialog dialog = new ProtonConfirmationDialog(ProtonStrings.DELETE_RECORD_Q);
            dialog.showConfirmation(e -> {
                for (CustomDictionary item : grid.getSelectedItems()) {
                    log.debug("DELETE ITEM: {}", item);
                    repository.delete(item);
                }

                grid.setItems(repository.findAll());
                dialog.close();
            });
        });

        refreshButton.addClickListener(e -> {
            grid.setItems(repository.findAll());
        });

        printButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        printButton.getStyle().set("color", "green");
        printButton.addClickListener(e -> {
            Page page = UI.getCurrent().getPage();
            page.executeJs("print();");
        });

        imageButton.addClickListener(e -> {
            Long id = grid.getSelectedItems().stream().findFirst().get().getId();
            ImageForm imageForm = new ImageForm(id, properties.getImagePath(), emf);
            imageForm.setChangeHandler(() -> {
                imageForm.close();
            });
            imageForm.open();
        });

        return new HorizontalLayout(insertButton, deleteButton, refreshButton, editButton, printButton, imageButton);
    }

    public void setupGrid() {
        grid.setSelectionMode(SelectionMode.SINGLE);
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT, GridVariant.MATERIAL_COLUMN_DIVIDERS, GridVariant.LUMO_COMPACT);
        grid.addSelectionListener(e -> {
            deleteButton.setEnabled(e.getFirstSelectedItem().isPresent());
            editButton.setEnabled(e.getFirstSelectedItem().isPresent());
            imageButton.setEnabled(e.getFirstSelectedItem().isPresent());
        });
    }

    public void setupEditor() {
        editor.setChangeHandler(() -> {
            log.debug("CHANGE HANDLER");
            editor.close();
            grid.setItems(repository.findAll());
            // listCustomers(filter.getValue());
        });
    }
}
