package sumo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;


@Component
public class EmailService {

//    private JavaMailSender javaMailSender;
    private MailSender 	mailSender;

//    @Autowired
//    public EmailServiceImpl(JavaMailSender javaMailSender) {
//	this.javaMailSender = javaMailSender;
//    }
    @Autowired
    public EmailService(MailSender mailSender) {
	this.mailSender = mailSender;
    }
       
    public void send(String from, String to, String title, String body) {
	
	// creates a simple e-mail object
	SimpleMailMessage email = new SimpleMailMessage();
	email.setTo(to);
	email.setFrom("portal@energomash.ru");
	email.setSubject(title);
	email.setText(body);
	mailSender.send(email);
	
//	Session session = ((JavaMailSenderImpl)mailSender).getSession();
	//	MimeMessage message = this.javaMailSender.createMimeMessage();
//	MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
//	try {
//	    if (from != null) {
//		mimeMessageHelper.setFrom(from);
//	    }
//	    mimeMessageHelper.setSubject(title);
//	    mimeMessageHelper.setText(body);
//	    mimeMessageHelper.setTo(to);
//	    this.javaMailSender.send(message);
//	} catch (MessagingException messageException) {
//	    // You could also 'throw' this exception. I am not a fan of checked exceptions.
//	    // If you want to go that route, then just update this method and the interface.
//	    throw new RuntimeException(messageException);
//	}
	
    }
}
