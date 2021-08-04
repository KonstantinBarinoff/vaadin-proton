package sumo.views;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserController {

    @GetMapping("user-controller")
//    public String getCurrentUser(Authentication auth) {
    public String getCurrentUser() {

	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	
	log.debug("CONSTRUCTOR");
	return String.format("<big>Вы авторизованы как пользователь:    %s <p><p><p>"
		+ "Доступные роли приложения: <p> %s", 
		auth.getName(), auth.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.filter(s -> s.toLowerCase().contains("bel_sql_sumo"))
			.collect(Collectors.joining("<p>")));
    }
    
    @GetMapping("current-user")
    public String demo(Authentication auth) {
	
        return String.format("Hello, %s. You have authorities: %s", auth.getPrincipal(),
                auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", ")));
    }
    
    @GetMapping("auth")
    public String checkAuthentication(Authentication auth) {
	return (auth != null) ? auth.getName() : "Authentication is null";
    }

}
