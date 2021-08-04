package sumo.views;

import org.springframework.beans.factory.annotation.Autowired;

//import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import lombok.extern.slf4j.Slf4j;
import sumo.entities.BzemDepartment;
import sumo.repositories.BzemDepartmentsDAO;
import sumo.util.Paths;

@Slf4j
@Route( Paths.BZEM_DEPARTMENTS) // User Equipments
public class BzemDepartmentsView extends VerticalLayout {

    private BzemDepartmentsDAO bzemDepartmentsDAO;
    private final Grid<BzemDepartment> grid;
    
    @Autowired
    public BzemDepartmentsView(BzemDepartmentsDAO bzemDepartmentsDAO) {  
	this.bzemDepartmentsDAO = bzemDepartmentsDAO;
	grid = new Grid<>(BzemDepartment.class);

        log.debug("CONSTRUCTOR");
//	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	if (auth.getAuthorities().stream()
//		.map(GrantedAuthority::getAuthority)
//		.filter(s -> s.toLowerCase().contains("bel_sql_sumo")).count() == 0 ) {
//	    add(new Span(String.format("Вы определены как пользователь:  %s. У вас нет прав для просмотра этой страницы!",
//		    auth.getName().replace("ENMASHBEL\\", ""))));
//	    return;
//	}

	// build layout
	TextField filter = new TextField();
	filter.setPlaceholder("Поиск");
	filter.setWidth("600px");
	filter.setValueChangeMode(ValueChangeMode.EAGER);
//	filter.addValueChangeListener(e -> listFiltered(e.getValue()));
	HorizontalLayout header = new HorizontalLayout(filter);
	
	grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

	setDefaultHorizontalComponentAlignment(Alignment.BASELINE);
	setSizeFull();
	
	grid.setColumns("departmentNumber", "departmentParentNumber", "departmentName");
	grid.getColumnByKey("departmentNumber").setFlexGrow(5).setHeader("Код подразделения");
	grid.getColumnByKey("departmentParentNumber").setFlexGrow(10).setHeader("Код родительского подразделения");
	grid.getColumnByKey("departmentName").setFlexGrow(20).setHeader("Наименование подразделения");
	grid.setItems(bzemDepartmentsDAO.getAllDistincted());

	add(header, grid);
    }
    
//    void listFiltered(String filterText) {
//	if (StringUtils.isEmpty(filterText)) {
//	    grid.setItems(bzemDepartmentsDAO.getAll());
//	} else {
//	    grid.setItems(bzemDepartmentsDAO.getByFilter(filterText));
//	}
//	}
    
    
}
