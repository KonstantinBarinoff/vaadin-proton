package sumo.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;

import lombok.extern.slf4j.Slf4j;
import sumo.entities.WorkFolderEntity;
import sumo.repositories.WorkFoldersRepository;
import sumo.util.Paths;

import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

@Slf4j
@Route( Paths.WFC )
public class WorkFoldersView extends VerticalLayout {

    private final WorkFoldersRepository repo;
    final Grid<WorkFolderEntity> grid;
    final TextField filter;

    public WorkFoldersView(WorkFoldersRepository repo) {
	log.debug("CONSTRUCTOR");

	this.repo = repo;

	// build layout
	this.filter = new TextField();

	filter.setId("wfc-filter");	
	filter.setPlaceholder("Поиск");
	filter.setWidth("600px");
	filter.setValueChangeMode(ValueChangeMode.EAGER);
	filter.addValueChangeListener(e -> listWorkFolders(e.getValue()));

	HorizontalLayout header = new HorizontalLayout(filter);

	this.grid = new Grid<>(WorkFolderEntity.class);

	setSizeFull();
	add(header, grid);

	grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT, GridVariant.LUMO_COMPACT);

	grid.setColumns("name", "purpose", "description", "ownerName");
	grid.getColumnByKey("name").setFlexGrow(30).setHeader("Файловые ресурсы");
	grid.getColumnByKey("purpose").setFlexGrow(30).setHeader("Назначение");
	grid.getColumnByKey("description").setFlexGrow(30).setHeader("Описание");
	grid.getColumnByKey("ownerName").setFlexGrow(30).setHeader("Владелец");

	listWorkFolders(null);
    }

    void listWorkFolders(String filterText) {
	if (StringUtils.isEmpty(filterText)) {
	    grid.setItems(repo.findAll(Sort.by("name")));
	} else {
	    grid.setItems(repo.findByFilter(filterText));
	}
    }

}
