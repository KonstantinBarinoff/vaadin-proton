package proton.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import proton.entities.BaseDict;
import proton.repositories.BaseRepo;
import proton.repositories.BaseService;
import util.ProtonConfirmationDialog;
import util.ProtonNotification;
import util.ProtonStrings;

import java.util.Collection;
import java.util.Collections;
import java.util.WeakHashMap;

@Slf4j
public abstract class BaseDictView<E extends BaseDict, S extends BaseService<E>> extends VerticalLayout {

    abstract E getNewItem();

    protected BaseRepo<E> repo;
    private S service = null;

    protected final Grid<E> grid = new Grid<>();
    protected Binder<E> binder = null;
    protected final Editor<E> editor = grid.getEditor();

    protected final Grid.Column<E> idColumn = grid.addColumn(E::getId)
            .setHeader("Код").setFlexGrow(1);
    protected final Grid.Column<E> nameColumn = grid.addColumn(E::getName)
            .setHeader("Наименование").setFlexGrow(15);
    protected final Grid.Column<E> descriptionColumn = grid.addColumn(E::getDescription)
            .setHeader("Примечание").setFlexGrow(5);


    protected final TextField nameField = new TextField();
    protected final TextField descriptionField = new TextField();

    protected final Button insertButton = new Button(ProtonStrings.INSERT, VaadinIcon.PLUS.create());
    protected final Button deleteButton = new Button(ProtonStrings.DELETE, VaadinIcon.MINUS.create());
    protected final Button refreshButton = new Button(ProtonStrings.REFRESH, VaadinIcon.REFRESH.create());
    protected final Button saveEditorButton = new Button(ProtonStrings.SAVE, VaadinIcon.CHECK.create());
    protected final Button cancelEditorButton = new Button(ProtonStrings.REVERT, VaadinIcon.CLOSE.create());
    protected final Collection<Button> editButtons = Collections.newSetFromMap(new WeakHashMap<>());

//    public BaseDictView(SimpleDict1Repo repo) {
//        this.repo = repo;
//        setDefaultHorizontalComponentAlignment(Alignment.BASELINE);
//        setSizeFull();
//        setupGrid();
//        grid.setItems(repo.findAll());
//        setupFields();
//        setupEditor();
//        add(setupButtons());
//        add(grid);
//    }

    @Autowired
    public BaseDictView(S Service) {
        this.service = service;
    }

    public HorizontalLayout setupButtons() {
        deleteButton.setEnabled(false);
        deleteButton.addClickListener(event -> {
            if (grid.getSelectedItems().isEmpty()) {
                ProtonNotification.showWarning(ProtonStrings.RECORD_NOT_SELECTED);
                return;
            }
            ProtonConfirmationDialog dialog = new ProtonConfirmationDialog(ProtonStrings.DELETE_RECORD_Q);
            dialog.showConfirmation(e -> {
                grid.getSelectedItems().stream().forEach(repo::delete);
                grid.setItems(repo.findAll());
                dialog.close();
            });
        });
        insertButton.addClickListener(e -> {
            E item = getNewItem();
            repo.saveAndFlush(item);
            grid.setItems(repo.findAll());
            grid.select(item);
            grid.getEditor().editItem(item);
        });
        refreshButton.addClickListener(e -> grid.setItems(repo.findAll()));
        return new HorizontalLayout(insertButton, deleteButton, refreshButton);
    }

    public Div setupEditorButtons() {
        saveEditorButton.addClickListener(e -> editor.save());
        saveEditorButton.addClassName("save");

        cancelEditorButton.addClickListener(e -> editor.cancel());
        cancelEditorButton.addClassName("cancel");

        return new Div(saveEditorButton, cancelEditorButton);
    }

    public void setupEditor() {
        editor.setBinder(binder);
        editor.setBuffered(true);
        editor.addOpenListener(e -> editButtons.stream().forEach(button -> button.setEnabled(!editor.isOpen())));
        editor.addCloseListener(e -> editButtons.stream().forEach(button -> button.setEnabled(!editor.isOpen())));
        editor.addSaveListener(e -> repo.saveAndFlush(e.getItem()));

    }

    public void setupFields() {
        nameField.setWidthFull();
        binder.forField(nameField).withValidator(string -> !string.isEmpty(), "Заполните поле")
                .bind(E::getName, E::setName);
        nameColumn.setEditorComponent(nameField);

        descriptionField.setWidthFull();
        binder.forField(descriptionField).bind(E::getDescription, E::setDescription);
        descriptionColumn.setEditorComponent(descriptionField);
    }

    public void setupGrid() {
        grid.addSelectionListener(e -> deleteButton.setEnabled(e.getFirstSelectedItem().isPresent()));
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT, GridVariant.MATERIAL_COLUMN_DIVIDERS, GridVariant.LUMO_COMPACT);
        grid.getElement().addEventListener("keyup", event -> editor.cancel())
                .setFilter("event.key === 'Escape' || event.key === 'Esc'");

        Grid.Column<E> editorColumn = grid.addComponentColumn(item -> {
            Button editButton = new Button(ProtonStrings.EDIT);
            editButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
            editButton.addClassName("edit");
            editButton.addClickListener(e -> {
                editor.editItem(item);
                nameField.focus();
            });
            editButton.setEnabled(!editor.isOpen());
            editButtons.add(editButton);
            return editButton;
        });
        editorColumn.setEditorComponent(setupEditorButtons());
    }

    protected void setupView() {
        setDefaultHorizontalComponentAlignment(Alignment.BASELINE);
        setSizeFull();
        setupGrid();
        grid.setItems(repo.findAll());
        setupFields();

        setupEditor();
        add(setupButtons());
        add(grid);
    }

}
