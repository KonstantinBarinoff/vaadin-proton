package proton.base;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.value.ValueChangeMode;
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

// TODO: Сделать генераторы для заполнения таблиц (проверить отклик интерфейса на больших таблицах)
// TODO: Test Lazy Loading
// TODO: Selenium UI Testing
// TODO: Multitab Edit Form
// TODO: Сделать Custom-компонент редактирования, например для @Embedded Dimensions (длина, ширина, высота)

// ALEXZ: Мой персональный комментарий :)

/**
 * Базовый класс формы просмотра таблицы в виде Grid <p>
 * Работает совместно с BaseDictViewEditor, реализующего модальную форму редактирования.
 *
 * @param <E> Редактируемая сущность
 * @param <S> Сервис отвечающий за чтение/запись в БД
 */
@Slf4j
public abstract class BaseDictView<E extends BaseDict, S extends BaseService<E>>
        extends VerticalLayout {

    protected E item;

    /**
     * Фабричный метод для создания экземпляра сущности
     * с которой работает наследник BaseDictView
     */
    protected abstract E getNewItem();

    protected BaseService<E> service;

    protected BaseDictViewEditor<E, S> editor;

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
    protected final TextField filterField = new TextField();

    public BaseDictView() {
        super();
    }

    public void setupView() {
        setupBrowserWindowResizeListener();
        setDefaultHorizontalComponentAlignment(Alignment.BASELINE);
        setSizeFull();
        setupGrid();
        setupEditor();
        refreshGrid();
        add(setupTopLayout());
        add(grid);
    }

    public void setupBrowserWindowResizeListener() {
        Page page = UI.getCurrent().getPage();
//        page.addBrowserWindowResizeListener(
//                e -> Notification.show("Window width=" + e.getWidth() + ", height=" + e.getHeight()));
    }

    protected void refreshGrid() {
        grid.setItems(service.findAll(filterField.getValue()));
    }

    public HorizontalLayout setupTopLayout() {
        insertButton.addClickListener(this::onInsertButtonClick);
        deleteButton.addClickListener(this::onDeleteButtonClick);
        refreshButton.addClickListener(this::onRefreshButtonClick);
        editButton.addClickListener(this::onEditButtonClick);
        deleteButton.setEnabled(false);
        editButton.setEnabled(false);
        filterField.setPlaceholder("Поиск (server-side)");
        filterField.setClearButtonVisible(true);
        filterField.addValueChangeListener(e -> refreshGrid());
        filterField.setValueChangeMode(ValueChangeMode.ON_CHANGE);


        return new HorizontalLayout(insertButton, deleteButton, refreshButton, editButton, filterField);
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
            new ProtonWarningDialog(e.getMessage(), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
            refreshGrid();
        }
    }

    protected void onRefreshButtonClick(ClickEvent<Button> e) {
        refreshGrid();
    }

    private void onEditorChange() {
        log.debug("CHANGE HANDLER");
        editor.close();
        refreshGrid();
        // listCustomers(filter.getValue());
    }

    protected void onInsertButtonClick(ClickEvent<Button> e) {
        editor.newItem(getNewItem());
        editor.open();
    }




}
