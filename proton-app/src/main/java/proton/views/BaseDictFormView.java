package proton.views;

import com.vaadin.flow.component.ClickEvent;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import proton.entities.BaseDict;
import proton.repositories.BaseRepo;
import proton.repositories.BaseService;
import util.ProtonConfirmationDialog;
import util.ProtonNotification;
import util.ProtonStrings;
import util.ProtonWarningDialog;

import javax.persistence.OptimisticLockException;
import java.util.Arrays;
import java.util.NoSuchElementException;

@Slf4j
public abstract class BaseDictFormView<E extends BaseDict, S extends BaseService<E>>
        extends VerticalLayout {

    protected E item;

    protected abstract E getNewItem();

    protected BaseRepo<E> repo;
    private S service = null;
    protected BaseDictFormEditor editor;

    protected final Grid<E> grid = new Grid<>();

    private final Grid.Column<E> idColumn = grid.addColumn(E::getId).setHeader("Код").setFlexGrow(1);
    private final Grid.Column<E> nameColumn = grid.addColumn(E::getName)
            .setHeader("Наименование").setFlexGrow(100);
    private final Grid.Column<E> descriptionColumn = grid.addColumn(E::getDescription)
            .setHeader("Примечание").setFlexGrow(50);

    private final Button insertButton = new Button(ProtonStrings.INSERT, VaadinIcon.PLUS.create());
    private final Button deleteButton = new Button(ProtonStrings.DELETE, VaadinIcon.MINUS.create());
    private final Button refreshButton = new Button(ProtonStrings.REFRESH, VaadinIcon.REFRESH.create());
    private final Button editButton = new Button(ProtonStrings.EDIT, VaadinIcon.EDIT.create());

    @Autowired
    public BaseDictFormView(S service) {
        this.service = service;
    }

    public void setupView() {
        setupBrowserWindowResizeListener();
        setupLayout();
        setupGrid();
//        editor = getNewEditor(service);
        setupEditor();
        onRefreshButtonClick(null);
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

    public void refreshGrid() {
        onRefreshButtonClick(null);
    }

    public HorizontalLayout setupButtons() {
        deleteButton.setEnabled(false);
        editButton.setEnabled(false);
        insertButton.addClickListener(e -> {
            editor.newItem(getNewItem());
            editor.open();
        });
        editButton.addClickListener(this::onEditButtonClick);
        deleteButton.addClickListener(this::onDeleteButtonClick);
        refreshButton.addClickListener(this::onRefreshButtonClick);
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
            refreshGrid();
            // listCustomers(filter.getValue());
        });
    }

    void onDeleteButtonClick(ClickEvent<Button> event) {
        if (grid.getSelectedItems().isEmpty()) {
            ProtonNotification.showWarning(ProtonStrings.RECORD_NOT_SELECTED);
            return;
        }
        ProtonConfirmationDialog dialog = new ProtonConfirmationDialog(ProtonStrings.DELETE_RECORD_Q);
        dialog.showConfirmation(e -> {
            try {
                for (E item : grid.getSelectedItems()) {
                    if (!repo.existsById(item.getId())) {
                        throw new OptimisticLockException(ProtonStrings.RECORD_NOT_FOUND + ": " + item);
                    }
                    repo.delete(item);
                }
            } catch (Exception ex) {
                new ProtonWarningDialog(ProtonStrings.RECORD_NOT_FOUND + ": " + item);
                log.error(Arrays.toString(ex.getStackTrace()));
            } finally {
                refreshGrid();
                dialog.close();
            }
        });
    }

    private void onEditButtonClick(ClickEvent<Button> event) {
        try {
            editor.editItem(grid.getSelectedItems().stream().findFirst().get());
            editor.open();
        } catch (NoSuchElementException e) {
            new ProtonWarningDialog(e.getMessage());
            refreshGrid();
        }
    }

    private void onRefreshButtonClick(ClickEvent<Button> e) {
        grid.setItems(repo.findAll());
    }
}