package sumo.views;

import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

//import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import sumo.entities.UserEquip;
import sumo.repositories.SupportEquipDAO;
import sumo.security.SecurityUtils;
import sumo.util.Paths;

@Slf4j
@Route( Paths.SUPPORT_EQUIP ) // User Equipments
@Secured({ SecurityUtils.ROLE_SUPPORT })
public class SupportEquipView extends VerticalLayout {

    private SupportEquipDAO supportEquipDAO;
    private final Grid<UserEquip> grid;
    
    @Autowired
    public SupportEquipView(SupportEquipDAO supportEquipDAO) {  
	this.supportEquipDAO = supportEquipDAO;
	grid = new Grid<>(UserEquip.class);

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
	filter.addValueChangeListener(e -> listFiltered(e.getValue()));
	HorizontalLayout header = new HorizontalLayout(filter);
	
	grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT, GridVariant.LUMO_COMPACT);

	setDefaultHorizontalComponentAlignment(Alignment.BASELINE);
	setSizeFull();

	grid.setColumns("registrationNB", "serialNB", "groupName", "modelName", "locationName", "userName", "phone");
	grid.getColumnByKey("registrationNB").setFlexGrow(5).setHeader("Регистр. №");
	grid.getColumnByKey("serialNB").setFlexGrow(10).setHeader("Серийный №");
	grid.getColumnByKey("groupName").setFlexGrow(20).setHeader("Тип оборудования");
	grid.getColumnByKey("modelName").setFlexGrow(30).setHeader("Модель");
	grid.getColumnByKey("locationName").setFlexGrow(50).setHeader("Помещение");
	grid.getColumnByKey("userName").setFlexGrow(50).setHeader("Пользователь");
	grid.getColumnByKey("phone").setFlexGrow(5).setHeader("Телефон");
	grid.setItems(supportEquipDAO.getAll());

	add(header, grid);
    }

    @PostConstruct
    public void	postConstruct() {
	log.debug("{} : POSTCONSTRUCT", this.getClass().getName());
    }

    @GetMapping("auth")
    public String demo(Authentication auth) {
//        final String currentUsername = WindowsAccountImpl.getCurrentUsername();
//        return currentUsername;
        return String.format("Hello, %s. <p>You have authorities: %s", //auth.getPrincipal(),
        	auth.getName(),
                auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", ")));
    }
    
    void listFiltered(String filterText) {
	if (StringUtils.isEmpty(filterText)) {
	    grid.setItems(supportEquipDAO.getAll());
	} else {
	    grid.setItems(supportEquipDAO.getByFilter(filterText));
	}
    }
    
    
}
