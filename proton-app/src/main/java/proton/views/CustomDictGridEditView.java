package proton.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import proton.entities.CustomDict;
import proton.repositories.CustomDictRepository;
import util.ProtonConfirmationDialog;
import util.ProtonDatePicker;
import util.ProtonNotification;
import util.ProtonStrings;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.WeakHashMap;

@Slf4j
@Route(value = "dictionary1-grid-pro", layout = MainView.class)
@PageTitle("CustomDict Grid Buffered Editor")
public class CustomDictGridEditView extends VerticalLayout {

    private final CustomDictRepository repo;
    // final TextField filter;

    public String sql = """
	    defefe
	    efefef
	    efef
    	""";
    

    private final Grid<CustomDict> grid = new Grid<>();
    private final Binder<CustomDict> binder = new Binder<>(CustomDict.class);
    private final Editor<CustomDict> editor = grid.getEditor();

    private final Grid.Column<CustomDict> idColumn = grid.addColumn(CustomDict::getId).setHeader("Код");
    private final Grid.Column<CustomDict> nameColumn = grid.addColumn(CustomDict::getName)
	    .setHeader("Наименование (не пуст.)");
    private final Grid.Column<CustomDict> numberColumn = grid.addColumn(CustomDict::getNumber)
	    .setHeader("Количество (целое > 0)");
    private final Grid.Column<CustomDict> dateColumn = grid.addColumn(CustomDict::getDate)
	    .setHeader("Дата (ближайший месяц)");
    private final Grid.Column<CustomDict> descriptionColumn = grid.addColumn(CustomDict::getDescription)
	    .setHeader("Примечание");
    private final Grid.Column<CustomDict> checkedColumn = grid.addColumn(i -> i.getChecked() ? "Да" : "")
	    .setHeader("Отметка");

    private final TextField nameField = new TextField();
    private final IntegerField numberField = new IntegerField();
    private final ProtonDatePicker dateField = new ProtonDatePicker();
    private final TextField descriptionField = new TextField();
    private final Checkbox checkedField = new Checkbox();

    private final Button insertButton = new Button(ProtonStrings.INSERT, VaadinIcon.PLUS.create());
    private final Button deleteButton = new Button(ProtonStrings.DELETE, VaadinIcon.MINUS.create());
    private final Button refreshButton = new Button(ProtonStrings.REFRESH, VaadinIcon.REFRESH.create());
    private final Button saveEditorButton = new Button(ProtonStrings.SAVE, VaadinIcon.CHECK.create());
    private final Button cancelEditorButton = new Button(ProtonStrings.REVERT, VaadinIcon.CLOSE.create());
    private final Collection<Button> editButtons = Collections.newSetFromMap(new WeakHashMap<>());

    @Autowired
    public CustomDictGridEditView(CustomDictRepository repo) {
	this.repo = repo;

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
		for (CustomDict item : grid.getSelectedItems())
		    repo.delete(item);
		grid.setItems(repo.findAll());
		dialog.close();
	    });
	});

	insertButton.addClickListener(e -> {
	    CustomDict item = new CustomDict();
	    repo.saveAndFlush(item);
	    grid.setItems(repo.findAll());
	    grid.select(item);
	    grid.getEditor().editItem(item);
	});

	refreshButton.addClickListener(e -> {
	    grid.setItems(repo.findAll());
	});

	return new HorizontalLayout(insertButton, deleteButton, refreshButton);	
    }

//    public void refreshView()
//    {
//	grid.setItems(userEquipDAO.getAllForUser());
//    }

    public Div setupEditorButtons() {
        saveEditorButton.addClickListener(e -> {
            log.debug("Save Button clicked : editor.save()");
            editor.save();
        });
        saveEditorButton.addClassName("save");
    
        cancelEditorButton.addClickListener(e -> editor.cancel());
        cancelEditorButton.addClassName("cancel");
        
        return new Div(saveEditorButton, cancelEditorButton);
    }

    public void setupEditor() {
	editor.setBinder(binder);
	editor.setBuffered(true);
	editor.addOpenListener(e -> editButtons.stream().forEach(button -> {
	    button.setEnabled(!editor.isOpen());
	}));
	editor.addCloseListener(e -> {
	    log.debug("Editor:CloseListener start");
	    editButtons.stream().forEach(button -> button.setEnabled(!editor.isOpen()));
//		    saveButton.setEnabled(false);
//		    cancelButton.setEnabled(false);
	    log.debug("Editor:CloseListener end");
	});
	editor.addSaveListener(e -> {
	    log.debug("Editor:SaveListener start");
	    repo.saveAndFlush(e.getItem());
	    log.debug("editor - SaveListener end");
	});
    }

    public void setupFields() {
	binder.forField(nameField).withValidator(string -> !string.isEmpty(), "Не пустая строка")
		.bind(CustomDict::getName, CustomDict::setName);
	nameColumn.setEditorComponent(nameField);

	binder.forField(numberField).withValidator(v -> v > 0, "Значение больше 0").bind(CustomDict::getNumber,
		CustomDict::setNumber);
	numberColumn.setEditorComponent(numberField);

	dateField.setMin(LocalDate.now());
	dateField.setMax(LocalDate.now().plusMonths(1));
	binder.forField(dateField).bind(CustomDict::getDate, CustomDict::setDate);
	dateColumn.setEditorComponent(dateField);

	binder.forField(descriptionField).bind(CustomDict::getDescription, CustomDict::setDescription);
	descriptionColumn.setEditorComponent(descriptionField);

	binder.forField(checkedField).bind(CustomDict::getChecked, CustomDict::setChecked);
	checkedColumn.setEditorComponent(checkedField);
    }
    
    public void setupGrid() {
	grid.addSelectionListener( e -> { 
	    deleteButton.setEnabled(e.getFirstSelectedItem().isPresent());
	});
	grid.setSelectionMode(SelectionMode.SINGLE);
	grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT, GridVariant.MATERIAL_COLUMN_DIVIDERS);
	grid.getElement().addEventListener("keyup", event -> editor.cancel())
		.setFilter("event.key === 'Escape' || event.key === 'Esc'");
	
	Grid.Column<CustomDict> editorColumn = grid.addComponentColumn(item -> {
	    Button edit = new Button(ProtonStrings.EDIT);
	    edit.addClassName("edit");
	    edit.addClickListener(e -> {
		editor.editItem(item);
		nameField.focus();
	    });
	    edit.setEnabled(!editor.isOpen());
	    editButtons.add(edit);
	    return edit;
	});
	editorColumn.setEditorComponent(setupEditorButtons());
    }

}
