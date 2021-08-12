package proton.views;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import proton.entities.SimpleDict1;
import proton.repositories.SimpleDict1Repository;
import proton.repositories.SimpleDict1Service;
import util.ProtonConfirmationDialog;
import util.ProtonNotification;
import util.ProtonStrings;

@Slf4j
@Route(value = "simple-dict-1", layout = MainView.class)
@PageTitle("SimpleDict1 Buffered Editor")
public class SimpleDict1View extends BaseDictView<SimpleDict1, SimpleDict1Service> {

    private final SimpleDict1Repository repo;

//    private final Grid<SimpleDict1> grid = new Grid<>();

//    private final Binder<SimpleDict1> binder = new Binder<>(SimpleDict1.class);
//    private final Binder<SimpleDict1> binder = new Binder<>(SimpleDict1.class);

//    private final Editor<SimpleDict1> editor = grid.getEditor();
//
//    private final Grid.Column<SimpleDict1> idColumn = grid.addColumn(SimpleDict1::getId)
//            .setHeader("Код").setFlexGrow(1);
//    private final Grid.Column<SimpleDict1> nameColumn = grid.addColumn(SimpleDict1::getName)
//            .setHeader("Наименование").setFlexGrow(15);
//    private final Grid.Column<SimpleDict1> descriptionColumn = grid.addColumn(SimpleDict1::getDescription)
//            .setHeader("Примечание").setFlexGrow(5);

//    private final TextField nameField = new TextField();
//    private final TextField descriptionField = new TextField();
//
//    private final Button insertButton = new Button(ProtonStrings.INSERT, VaadinIcon.PLUS.create());
//    private final Button deleteButton = new Button(ProtonStrings.DELETE, VaadinIcon.MINUS.create());
//    private final Button refreshButton = new Button(ProtonStrings.REFRESH, VaadinIcon.REFRESH.create());
//    private final Button saveEditorButton = new Button(ProtonStrings.SAVE, VaadinIcon.CHECK.create());
//    private final Button cancelEditorButton = new Button(ProtonStrings.REVERT, VaadinIcon.CLOSE.create());
//    private final Collection<Button> editButtons = Collections.newSetFromMap(new WeakHashMap<>());

    @Autowired
    public SimpleDict1View(SimpleDict1Repository repo) {
        this.repo = repo;
        binder = new Binder<>(SimpleDict1.class);

        setDefaultHorizontalComponentAlignment(Alignment.BASELINE);
        setSizeFull();
        setupGrid();
        grid.setItems(repo.findAll());
        setupFields();

        setupEditor();
        add(setupButtons());
        add(grid);
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
            SimpleDict1 item = new SimpleDict1();
            repo.saveAndFlush(item);
            grid.setItems(repo.findAll());
            grid.select(item);
            grid.getEditor().editItem(item);
        });
        refreshButton.addClickListener(e -> grid.setItems(repo.findAll()));
        return new HorizontalLayout(insertButton, deleteButton, refreshButton);
    }

//    public Div setupEditorButtons() {
//        saveEditorButton.addClickListener(e -> editor.save());
//        saveEditorButton.addClassName("save");
//
//        cancelEditorButton.addClickListener(e -> editor.cancel());
//        cancelEditorButton.addClassName("cancel");
//
//        return new Div(saveEditorButton, cancelEditorButton);
//    }

    @Override
    public void setupEditor() {
        super.setupEditor();
        editor.addSaveListener(e -> repo.saveAndFlush(e.getItem()));
    }

//    public void setupFields() {
//        nameField.setWidthFull();
//        binder.forField(nameField).withValidator(string -> !string.isEmpty(), "Заполните поле")
//                .bind(SimpleDict1::getName, SimpleDict1::setName);
//        nameColumn.setEditorComponent(nameField);
//
//        descriptionField.setWidthFull();
//        binder.forField(descriptionField).bind(SimpleDict1::getDescription, SimpleDict1::setDescription);
//        descriptionColumn.setEditorComponent(descriptionField);
//    }

//    public void setupGrid() {
//        grid.addSelectionListener(e -> deleteButton.setEnabled(e.getFirstSelectedItem().isPresent()));
//        grid.setSelectionMode(SelectionMode.SINGLE);
//        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT, GridVariant.MATERIAL_COLUMN_DIVIDERS, GridVariant.LUMO_COMPACT);
//        grid.getElement().addEventListener("keyup", event -> getEditor().cancel())
//                .setFilter("event.key === 'Escape' || event.key === 'Esc'");
//
//        Grid.Column<SimpleDict1> editorColumn = grid.addComponentColumn(item -> {
//            Button editButton = new Button(ProtonStrings.EDIT);
//            editButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
//            editButton.addClassName("edit");
//            editButton.addClickListener(e -> {
//                getEditor().editItem(item);
//                nameField.focus();
//            });
//            editButton.setEnabled(!getEditor().isOpen());
//            editButtons.add(editButton);
//            return editButton;
//        });
//        editorColumn.setEditorComponent(setupEditorButtons());
//    }

//    @Override
//    protected Editor<E> getEditor() {
//
//        return grid.getEditor();
//    }

//    @Override
//    protected JpaRepository<? extends BaseDict, Long> getRepo()
//    {
//        return repo;
//    }

//    @Override
//    protected Binder<SimpleDict1> getBinder() {
//
//        return (Binder<SimpleDict1>) binder;
//    }
}
