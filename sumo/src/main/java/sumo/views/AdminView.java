package sumo.views;

import java.util.stream.Collectors;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import lombok.extern.slf4j.Slf4j;
import sumo.security.SecurityUtils;
import sumo.util.Paths;

@Slf4j
@Route(Paths.ADMIN)
@Secured({ SecurityUtils.ROLE_ADMIN })
public class AdminView extends VerticalLayout {

    public AdminView() {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	String str = String.format("<big>Администратор: %s <p>" + "Доступные роли приложения: <p> %s", auth.getName(),
		auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
			.filter(s -> s.toLowerCase().contains("bel_sql_sumo")).collect(Collectors.joining("<p>")));
	add(new Html(str));
	setSizeFull();
    }

}
