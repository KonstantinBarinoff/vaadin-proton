package proton.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
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
import proton.entities.CustomDict;
import proton.repositories.CustomDictRepo;
import util.ProtonConfirmationDialog;
import util.ProtonNotification;
import util.ProtonStrings;

@Slf4j
@Route(value = "base-form-dict", layout = MainView.class)
@PageTitle("Base Dictionary with Dialog Editor")
public class BaseFormDictView extends VerticalLayout {

    private final CustomDictRepo repo;
    private final BaseFormDictDialog editor;
    private final Grid<CustomDict> grid = new Grid<>();

    private final Grid.Column<CustomDict> idColumn = grid.addColumn(CustomDict::getId)
            .setHeader("Код").setFlexGrow(1);
    private final Grid.Column<CustomDict> nameColumn = grid.addColumn(CustomDict::getName)
            .setHeader("Наименование").setFlexGrow(15);
    private final Grid.Column<CustomDict> descriptionColumn = grid.addColumn(CustomDict::getDescription)
            .setHeader("Примечание").setFlexGrow(5);

    private final Button insertButton = new Button(ProtonStrings.INSERT, VaadinIcon.PLUS.create());
    private final Button deleteButton = new Button(ProtonStrings.DELETE, VaadinIcon.MINUS.create());
    private final Button refreshButton = new Button(ProtonStrings.REFRESH, VaadinIcon.REFRESH.create());
    private final Button editButton = new Button(ProtonStrings.EDIT, VaadinIcon.EDIT.create());

    @Autowired
    public BaseFormDictView(CustomDictRepo repo) {
        this.repo = repo;

        setupBrowserWindowResizeListener();

        setupLayout();
        setupGrid();

        editor = new BaseFormDictDialog(repo);
        setupEditor();

        grid.setItems(repo.findAll());

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

        insertButton.addClickListener(e -> {
            editor.newItem(new CustomDict());
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
                for (CustomDict item : grid.getSelectedItems()) {
                    log.debug("DELETE ITEM: {}", item);
                    repo.delete(item);
                }

                grid.setItems(repo.findAll());
                dialog.close();
            });
        });

        refreshButton.addClickListener(e -> {
            grid.setItems(repo.findAll());
        });


        return new HorizontalLayout(insertButton, deleteButton, refreshButton, editButton);
    }

    public void setupGrid() {
        grid.setSelectionMode(SelectionMode.SINGLE);
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT, GridVariant.MATERIAL_COLUMN_DIVIDERS, GridVariant.LUMO_COMPACT);
        grid.addSelectionListener(e -> {
            deleteButton.setEnabled(e.getFirstSelectedItem().isPresent());
            editButton.setEnabled(e.getFirstSelectedItem().isPresent());
        });
    }

    public void setupEditor() {
        editor.setChangeHandler(() -> {
            log.debug("CHANGE HANDLER");
            editor.close();
            grid.setItems(repo.findAll());
            // listCustomers(filter.getValue());
        });
    }
}
