package sumo.views;

import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import sumo.util.Paths;

@Slf4j
@Route(Paths.ACCEESS_DENIED) 
public class AccessDeniedView extends VerticalLayout {

    public AccessDeniedView() {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	
	String str = String.format("<big>Пользователь:    %s <p>" +
		"Запрещен доступ на страницу: %s <p>" +
		"Доступные роли приложения: <p> %s", 
		auth.getName(),
		UI.getCurrent().getInternals().getLastHandledLocation().getPath(),
				
		auth.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.filter(s -> s.toLowerCase().contains("bel_sql_sumo"))
			.collect(Collectors.joining("<p>")));
	
	log.debug(str.replaceAll("\\<[^>]*>",""));
	add(new Html(str));
	
	setSizeFull();
    }
    
}
