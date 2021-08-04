//package proton.workfolders;
//
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.grid.GridVariant;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.value.ValueChangeMode;
//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.router.Route;
//
//import proton.views.MainView;
//
//import org.springframework.util.StringUtils;
//
//@Route(value = "wfc", layout = MainView.class)
//@PageTitle("Work Folder Catalog")
//@SuppressWarnings("serial")
//public class WorkFoldersView extends VerticalLayout {
//
//    private final WorkFoldersRepository repo;
//    final Grid<WorkFolders> grid;
//    final TextField filter;
//
//    public WorkFoldersView(WorkFoldersRepository repo) {
//	this.repo = repo;
//
//	// build layout
//	this.filter = new TextField();
//
//	filter.setPlaceholder("Поиск");
//	filter.setWidth("600px");
//	filter.setValueChangeMode(ValueChangeMode.EAGER);
//	filter.addValueChangeListener(e -> listWorkFolders(e.getValue()));
//
//	HorizontalLayout header = new HorizontalLayout(filter);
//
//	this.grid = new Grid<>(WorkFolders.class);
//
//	setSizeFull();
//	// setMargin(false);
//	// setSpacing(false);
//	// setPadding(false);
//	add(header, grid);
//
//	grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
//
//	grid.setColumns("name", "purpose", "description", "ownerName");
//	grid.getColumnByKey("name").setFlexGrow(30).setHeader("Файловые ресурсы");
//	grid.getColumnByKey("purpose").setFlexGrow(30).setHeader("Назначение");
//	grid.getColumnByKey("description").setFlexGrow(30).setHeader("Описание");
//	grid.getColumnByKey("ownerName").setFlexGrow(30).setHeader("Владелец");
//
//	// Replace listing with filtered content when user changes filter
//	listWorkFolders(null);
//    }
//
//    void listWorkFolders(String filterText) {
//	if (StringUtils.isEmpty(filterText)) {
//	    grid.setItems(repo.findAll());
//	} else {
//	    grid.setItems(repo.findByFilter(filterText));
//	}
//    }
//
//}
