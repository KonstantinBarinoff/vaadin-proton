package proton.base;

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
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import util.ProtonConfirmationDialog;
import util.ProtonNotification;
import util.ProtonStrings;
import util.ProtonWarningDialog;

import javax.persistence.OptimisticLockException;
import java.util.Arrays;
import java.util.NoSuchElementException;

@Slf4j
public abstract class BaseDictView<E extends BaseDict, S extends BaseService<E>>
        extends VerticalLayout {

    protected E item;

    protected abstract E getNewItem();

//    protected BaseRepository<E> repo;
    protected BaseService<E> service;


    protected BaseDictViewEditor editor;

    protected final Grid<E> grid = new Grid<>();

    private final Grid.Column<E> idColumn = grid.addColumn(E::getId).setKey("id").setHeader("Код").setFlexGrow(1);
    private final Grid.Column<E> nameColumn = grid.addColumn(E::getName)
            .setKey("name").setHeader("Наименование").setFlexGrow(100);
    private final Grid.Column<E> descriptionColumn = grid.addColumn(E::getDescription)
            .setKey("description").setHeader("Примечание").setFlexGrow(50);

    private final Button insertButton = new Button(ProtonStrings.INSERT, VaadinIcon.PLUS.create());
    private final Button deleteButton = new Button(ProtonStrings.DELETE, VaadinIcon.MINUS.create());
    private final Button refreshButton = new Button(ProtonStrings.REFRESH, VaadinIcon.REFRESH.create());
    private final Button editButton = new Button(ProtonStrings.EDIT, VaadinIcon.EDIT.create());

    public void setupView() {
        setupBrowserWindowResizeListener();
        setupLayout();
        setupGrid();
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
        insertButton.addClickListener(this::onInsertButtonClick);
        deleteButton.addClickListener(this::onDeleteButtonClick);
        deleteButton.setEnabled(false);
        refreshButton.addClickListener(this::onRefreshButtonClick);
        editButton.addClickListener(this::onEditButtonClick);
        editButton.setEnabled(false);
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
        editor.setOnChange(this::onEditorChange);
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
                    if (!service.existsById(item.getId())) {
                        throw new OptimisticLockException(ProtonStrings.RECORD_NOT_FOUND + ": " + item);
                    }
                    service.delete(item);
                }
            } catch (DataIntegrityViolationException ex) {
                new ProtonWarningDialog(ProtonStrings.DATA_INTEGRITY_VIOLETION,
                        NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
                log.error(Arrays.toString(ex.getStackTrace()));
            } catch (Exception ex) {
                new ProtonWarningDialog(ProtonStrings.RECORD_NOT_FOUND + ": " + item,
                        NestedExceptionUtils.getMostSpecificCause(ex).getMessage());
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
            new ProtonWarningDialog(e.getMessage(), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
            refreshGrid();
        }
    }

    private void onRefreshButtonClick(ClickEvent<Button> e) {
        grid.setItems(service.findAll());
    }

    private void onEditorChange() {
        log.debug("CHANGE HANDLER");
        editor.close();
        refreshGrid();
        // listCustomers(filter.getValue());
    }

    private void onInsertButtonClick(ClickEvent<Button> e) {
        editor.newItem(getNewItem());
        editor.open();
    }
}
