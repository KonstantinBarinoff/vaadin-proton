package sumo.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

import lombok.extern.slf4j.Slf4j;
import sumo.util.Paths;

@Slf4j
@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {
	event.getSource().addUIInitListener(uiEvent -> {
	    final UI ui = uiEvent.getUI();
	    ui.addBeforeEnterListener(this::beforeEnter);
	});
    }

    /**
     * Reroutes the user if (s)he is not authorized to access the view.
     *
     * @param event before navigation event with event details
     */
    private void beforeEnter(BeforeEnterEvent event) {
	if (!SecurityUtils.isAccessGranted(event.getNavigationTarget())) {
	    if (SecurityUtils.isUserLoggedIn()) {
		log.debug("USER LOGGED IN BUT NOT GRANTED - REROUTE TO NOT FOUND");
		event.forwardTo(Paths.ACCEESS_DENIED);
//		event.rerouteToError(NotFoundException.class);
	    } else {
		log.debug("USER {} IS NOT GRANTED TO {}",
			SecurityContextHolder.getContext().getAuthentication().getName(), event.getNavigationTarget());

		event.forwardTo(Paths.ACCEESS_DENIED);
	    }
	}
    }
}