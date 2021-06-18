package util.mail;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtility {

    private static final String EMAIL_SENDER = "BakeMS.NoReply@gmail.com";
    private static final String PASSWORD = "Noreply123!@#";

    private static final String FORGOT_PASSWORD_CONTENT_STRING_FORMAT = "Hello,<br/><br/>"
            + "A request to recover your BMS password  with this email address"
            + " was submitted recently.<br/><br/>"
            + "Your auto-recovered password is: "
            + "<b>%s</b>"
            + "<br/><br/>"
            + "If you did not request this, you can safely ignore the mail.<br/><br/>"
            + "Best regards <br/><br/>"
            + "The BMS Team";

    private static final String NEW_PASSWORD_CONTENT_STRING_FORMAT
            = "Hello,<br/><br/>"
            + "A request to create an BMS account with this email address "
            + "was submitted recently.<br/><br/>"
            + "Your auto-generated password is: "
            + "<b>%s</b>"
            + "<br/><br/>"
            + "If you did not request this, you can safely ignore the mail.<br/><br/>"
            + "Best regards <br/><br/>"
            + "The BMS Team";

    private static final String NEW_PASSWORD_EMAIL_TITLE
            = "[BMS] Your new auto-generated password is ready";

    private static final String FORGOT_PASSWORD_EMAIL_TITLE
            = "[BMS] Your password has been recovered";

    public static void sendPasswordRecover(String emailRecipient, String recoverdPassword) {
        new Thread() {
            @Override
            public void run() {
                sendMail(emailRecipient, FORGOT_PASSWORD_EMAIL_TITLE,
                        String.format(FORGOT_PASSWORD_CONTENT_STRING_FORMAT, recoverdPassword));
            }
        }.start();
    }

    public static void sendPasswordNewEmployee(String emailRecipient, String newPassword) {
        new Thread() {
            @Override
            public void run() {
                sendMail(emailRecipient, NEW_PASSWORD_EMAIL_TITLE,
                        String.format(NEW_PASSWORD_CONTENT_STRING_FORMAT, newPassword));
            }
        }.start();
    }

    private static void sendMail(String emailRecipient, String emailTitle, String content) {
        Properties properties = System.getProperties();
        String host = "smtp.gmail.com";
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.user", EMAIL_SENDER);
        properties.put("mail.smtp.password", PASSWORD);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_SENDER, PASSWORD);
            }
        });

        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(EMAIL_SENDER));
            InternetAddress toAddress = new InternetAddress(emailRecipient);
            message.addRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(emailTitle);
            message.setContent(content, "text/html");
            Transport transport = session.getTransport("smtp");
            transport.connect(host, EMAIL_SENDER, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}
