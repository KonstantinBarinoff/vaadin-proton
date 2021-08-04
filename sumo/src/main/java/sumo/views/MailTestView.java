package sumo.views;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import sumo.security.SecurityUtils;
import sumo.services.EmailService;
import sumo.util.Paths;
import util.ProtonDatePicker;


@Slf4j
@Route(Paths.MAIL_TEST)
@Secured({SecurityUtils.ROLE_ADMIN})
public class MailTestView extends VerticalLayout {

	private final TextField emailField = new TextField("E-mail");
	private final Button sendButton = new Button("Send e-mail", VaadinIcon.MAILBOX.create());
	private final EmailService emailServiceImpl;
	private final ProtonDatePicker date = new ProtonDatePicker();


//    private final TextLabel 

	private void sendEmail(String email) {
		new Notification(email, 1000, Position.MIDDLE).open();
		emailServiceImpl.send("alex_zhig@energomash.ru", email,
				"Test Email Title", "test email body");
		;
	}

	@Autowired
	public MailTestView(EmailService emailServiceImpl) {
		this.emailServiceImpl = emailServiceImpl;

		log.debug("CONSTRUCTOR");

		emailField.setValue("alex_zhig@energomash.ru");
		emailField.setWidth("300px");

		//FunctionalInterface lambda = () -> sendEmail(emailField.getValue());

		sendButton.addClickListener(e -> sendEmail(emailField.getValue()));

		String str = String.format("<big>Администратор");
		add(new Html("<big>Отправка"));
		add(emailField);
		add(sendButton);
		add(date);

		setSizeFull();


	}

}
