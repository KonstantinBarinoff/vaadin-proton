//package proton.views;
//
//import java.util.stream.Collectors;
//
//import javax.annotation.PostConstruct;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.GetMapping;
//
////import com.vaadin.flow.component.Component;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.grid.GridVariant;
//import com.vaadin.flow.component.html.Span;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.router.Route;
//
//import lombok.extern.slf4j.Slf4j;
//import proton.entities.UserEquip;
//import proton.repositories.UserEquipDAO;
//
//
////@SpringComponent	//Can't move a node from one state tree to another. If this is intentional, first remove the node from its current state tree by calling removeFromTree
//			//c.v.flow.router.InternalServerError      : There was an exception while trying to navigate to 'ue'
//			//I had the same problem and I found out that I had an extra SpringComponent annotation on my view class. When I removed it the problem was solved.
//			//https://vaadin.com/forum/thread/17411679/can-t-move-a-node-from-one-state-tree-to-another-error-in-vaadin-10 
//
//@Slf4j
//@Route(value = "user-equipments", layout = MainView.class)
//@PageTitle("User Equipments")
//@SuppressWarnings("serial")
//public class UserEquipView extends VerticalLayout {
//
//    private UserEquipDAO userEquipDAO;
//    final Grid<UserEquip> grid;
//    final TextField filter;
//
//    @Autowired
//    public UserEquipView(UserEquipDAO userEquipDAO) {
//	this.userEquipDAO = userEquipDAO;
//
//	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	String loginName = auth.getName().replace("ENMASHBEL\\", "");
//
//	// build layout
//	this.filter = new TextField();
//
//	setDefaultHorizontalComponentAlignment(Alignment.BASELINE);
//	
////	HorizontalLayout header1 = new HorizontalLayout(
////		new TextField("TextField1"), 
////		new TextField("TextField2"),
////		new TextField(auth.getName()),		
////		filter);   
////	header1.setAlignItems(Alignment.BASELINE);
////
////	HorizontalLayout header2 = new HorizontalLayout(
////		new TextField("TextField1"), 
////		new TextField("TextField2"), 
////		filter);   // new TextField(authentication.getName())
////	header2.setAlignItems(Alignment.BASELINE);
//
//	this.grid = new Grid<>(UserEquip.class);
//
//	add(new Span(auth.getName())); 
//	
//	setSizeFull();
//	// setMargin(false);
//	// setSpacing(false);
//	// setPadding(false);
////	add(header1, header2, grid);
//	add(grid);
//
//	grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
//
//	grid.setColumns("registrationNB", "serialNB", "groupName", "modelName", "locationName", "userName");
//	grid.getColumnByKey("registrationNB").setFlexGrow(5).setHeader("Регистр. №");
//	grid.getColumnByKey("serialNB").setFlexGrow(10).setHeader("Серийный №");
//	grid.getColumnByKey("groupName").setFlexGrow(20).setHeader("Тип оборудования");
//	grid.getColumnByKey("modelName").setFlexGrow(30).setHeader("Модель");
//	grid.getColumnByKey("locationName").setFlexGrow(50).setHeader("Помещение");
//	grid.getColumnByKey("userName").setFlexGrow(50).setHeader("Пользователь");
//
////	grid.setItems(Arrays.asList(
////		new UserEquipEntity(100, "11111", "Группа", "Модель", "Помещение"),
////		new UserEquipEntity(200, "2342332321", "Группа", "Модель", "Помещение")));		
//
////	System.out.printf("%s : userEquipDAO = %s\n", this.getClass().getName(), (userEquipDAO == null) ? "null" : "not null");
//	grid.setItems(userEquipDAO.getAllForUser(loginName));
////	log.warn("Constructor", this.getClass().getName());
//    }
//    
////    public void refreshView()
////    {
////	grid.setItems(userEquipDAO.getAllForUser());
////    }
//
//    @PostConstruct
//    public void	postConstruct() {
//	log.debug("{} : POSTCONSTRUCT", this.getClass().getName());
//
//    }
//    @GetMapping("auth")
//    public String demo(Authentication auth) {
////        final String currentUsername = WindowsAccountImpl.getCurrentUsername();
////        return currentUsername;
//        return String.format("Hello, %s. <p>You have authorities: %s", //auth.getPrincipal(),
//        	auth.getName(),
//                auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", ")));
//    }
//    
//    
//    
//}
