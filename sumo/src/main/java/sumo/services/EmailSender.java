//package sumo.services;
//
//import java.util.Properties;
//
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//public class EmailSender{
//
//    public static void main(String args[]) {
//        String to = "ssss@xxx.om";            // sender email
//        String from = "dddd@xxx.com";       // receiver email
//        String host = "dkdkdd.xxx.com";                   // mail server host
//
//        String login="dkkdkd";
//        String pass="dkkdkd";
//       Properties properties = System.getProperties();
//        properties.setProperty("mail.smtp.host", host);
//        properties.setProperty("mail.smtp.user", login);
//        properties.setProperty("mail.smtp.password", pass);
//        properties.setProperty("mail.smtps.ssl.enable", "true");
//       // properties.setProperty("mail.smtp.auth", "true"); 
//
//        Session session = Session.getDefaultInstance(properties); // default session
//
//        try {
//            MimeMessage message = new MimeMessage(session);        // email message
//            message.setFrom(new InternetAddress(from));                    // setting header fields
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//            message.setSubject("Test Mail from Java Program"); // subject line
//
//            // actual mail body
//            message.setText("You can send mail from Java program by using");
//
//            // Send message
//            Transport transport = session.getTransport("smtp");
//            transport.connect(host, login, pass);
//            Transport.send(message);
//            System.out.println("Email Sent successfully....");
//        } catch (MessagingException mex) {
//            mex.printStackTrace();
//        }
//    }
//
//}