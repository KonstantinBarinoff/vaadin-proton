package proton.views;

import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouteData;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "ref", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Reference View")
public class ReferencesView extends VerticalLayout {

    public ReferencesView() {
	setId("ref-view");

	add(new Paragraph("Authenticated User: " + SecurityContextHolder.getContext().getAuthentication().getName()));	
	add(new Paragraph("Vaadin Session: " + VaadinSession.getCurrent()));
	add(new Paragraph("Vaadin UI: " + UI.getCurrent()));

	add(new Paragraph("Available Routes:"));
	for(RouteData route : RouteConfiguration.forApplicationScope().getAvailableRoutes()) {
	        add(new Anchor(route.getUrl(), route.getUrl()));
	}

	add(new Anchor("hello", "HelloController"));
        add(new Anchor("user", "UserController"));
        
	add(new Image("img/cat.jpg", "Cat")); 	// src\main\webapp\img

        
    }

}
