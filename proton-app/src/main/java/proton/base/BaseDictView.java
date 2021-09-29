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
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import proton.reports.ExcelCreator;
import proton.reports.PdfCreator;
import proton.reports.ReportFactory;
import util.ProtonConfirmationDialog;
import util.ProtonNotification;
import util.ProtonStrings;
import util.ProtonWarningDialog;

import javax.persistence.OptimisticLockException;
import java.util.*;

@Slf4j
public abstract class BaseDictView<E extends BaseDict, S extends BaseService<E>>
        extends VerticalLayout {

    protected E item;

    protected abstract E getNewItem();

    protected BaseService<E> service;

    protected BaseDictViewEditor editor;

    protected final Grid<E> grid = new Grid<>();


    private final Grid.Column<E> idColumn = grid.addColumn(E::getId).setKey("id").setHeader("Код").setFlexGrow(1);
    private final Grid.Column<E> nameColumn = grid.addColumn(E::getName)
            .setKey("name").setHeader("Наименование").setFlexGrow(100);
    private final Grid.Column<E> descriptionColumn = grid.addColumn(E::getDescription)
            .setKey("description").setHeader("Примечание").setFlexGrow(50);

    protected final Button insertButton = new Button(ProtonStrings.INSERT, VaadinIcon.PLUS.create());
    protected final Button deleteButton = new Button(ProtonStrings.DELETE, VaadinIcon.MINUS.create());
    protected final Button refreshButton = new Button(ProtonStrings.REFRESH, VaadinIcon.REFRESH.create());
    protected final Button editButton = new Button(ProtonStrings.EDIT, VaadinIcon.EDIT.create());
    protected final Button exportPDFButton = new Button(ProtonStrings.EXPORT_PDF, VaadinIcon.FILE_TEXT.create());
    protected final Button exportExcelButton = new Button(ProtonStrings.EXPORT_EXCEL, VaadinIcon.FILE_TABLE.create());

    public void setupView() {
        setupBrowserWindowResizeListener();
        setupLayout();
        setupGrid();
        setupEditor();
        refreshGrid();
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

    protected void refreshGrid() {
        grid.setItems(service.findAll());
    }

    public HorizontalLayout setupButtons() {
        insertButton.addClickListener(this::onInsertButtonClick);
        deleteButton.addClickListener(this::onDeleteButtonClick);
        refreshButton.addClickListener(this::onRefreshButtonClick);
        editButton.addClickListener(this::onEditButtonClick);
        exportPDFButton.addClickListener(this::exportReport);
        exportExcelButton.addClickListener(this::exportReport);
        deleteButton.setEnabled(false);
        editButton.setEnabled(false);
        return new HorizontalLayout(insertButton, deleteButton, refreshButton, editButton, exportPDFButton, exportExcelButton);
    }

    public void setupGrid() {
        grid.addSelectionListener(this::onGridSelectionEvent);
        grid.setSelectionMode(SelectionMode.SINGLE);
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT, GridVariant.MATERIAL_COLUMN_DIVIDERS, GridVariant.LUMO_COMPACT);
    }

    public void setupEditor() {
        editor.setOnChange(this::onEditorChange);
    }

    protected void onGridSelectionEvent(SelectionEvent<Grid<E>, E> e) {
        deleteButton.setEnabled(e.getFirstSelectedItem().isPresent());
        editButton.setEnabled(e.getFirstSelectedItem().isPresent());
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
                new ProtonWarningDialog(ProtonStrings.DATA_INTEGRITY_VIOLATION,
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

    protected void onEditButtonClick(ClickEvent<Button> event) {
        if (grid.getSelectedItems().stream().findFirst().isEmpty()) {
            return;
        }
        try {
            editor.editItem(grid.getSelectedItems().stream().findFirst().get());
            editor.open();
        } catch (NoSuchElementException e) {
//            new ProtonWarningDialog(e.getMessage(), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
            refreshGrid();
        }
    }

    protected void onRefreshButtonClick(ClickEvent<Button> event) {
        refreshGrid();
    }

    private void onEditorChange() {
        log.debug("CHANGE HANDLER");
        editor.close();
        refreshGrid();
        // listCustomers(filter.getValue());
    }

    protected void onInsertButtonClick(ClickEvent<Button> event) {
        editor.newItem(getNewItem());
        editor.open();
    }

    private void exportReport(ClickEvent<Button> event) {
        ReportFactory factory;
        if ("Excel".equals(event.getSource().getText())) {
            factory = new ExcelCreator();
        } else {
            factory = new PdfCreator();
        }
        List<E> content = service.findAll();
        factory.setReportForm("classpath:TestReport.jrxml");
        factory.setReportContent(content);
        StreamResource resource = factory.getReportResource();
        StreamRegistration registration = VaadinSession.getCurrent().getResourceRegistry().registerResource(resource);
        UI.getCurrent().getPage().open(registration.getResourceUri().toString(), "_blank");
    }
}
