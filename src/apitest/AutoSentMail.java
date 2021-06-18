/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apitest;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Minh Tu
 */
public class AutoSentMail extends Thread {

    private String emailRecipient = null;
    private String newPassword = null;
    private MailType mailType = null;

    public AutoSentMail(String emailRecipient, String newPassword, MailType mailType) {
        this.emailRecipient = emailRecipient;
        this.newPassword = newPassword;
        this.mailType = mailType;
    }

    @Override
    public void run() {
        super.run();
        System.out.println("Prepare to send mail");
        sendMail();
        System.out.println("Sent successfully");
    }

    private static String getNewPasswordContent(String newPassword) {
        String htmlMessage = "Hello,<br/><br/>"
                + "A request to create an BMS account with this email address was submitted recently.<br/><br/>"
                + "Your auto-generated password is: " + "<b>" + newPassword + "</b>" + "<br/><br/>"
                + "If you did not request this, you can safely ignore the mail.<br/><br/>"
                + "Best regards <br/><br/>"
                + "The BMS Team";
        return htmlMessage;
    }

    private static String getForgotPasswordContent(String newPassword) {
        String htmlMessage = "Hello,<br/><br/>"
                + "A request to recover your BMS password  with this email address was submitted recently.<br/><br/>"
                + "Your auto-recovered password is: " + "<b>" + newPassword + "</b>" + "<br/><br/>"
                + "If you did not request this, you can safely ignore the mail.<br/><br/>"
                + "Best regards <br/><br/>"
                + "The BMS Team";
        return htmlMessage;
    }

    public enum MailType {

        NEW_PASSWORD("[BMS] Your new auto-generated password is ready"),
        FORGOT_PASSWORD("[BMS] Your password has been recovered");

        public final String title;

        private MailType(String title) {
            this.title = title;
        }

    }

    public void sendMail() {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String emailSender = "BakeMS.NoReply@gmail.com";
        String password = "Noreply123!@#";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailSender, password);
            }
        });

        Message message = prepareMessage(session, emailSender, emailRecipient, newPassword, mailType);
        try {
            Transport.send(message);
        } catch (MessagingException ex) {
            System.out.println("Failed to send the message");
        }
    }

    private Message prepareMessage(Session session, String emailSender, String emailRecipient, String newPassword, MailType mailType) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailSender));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailRecipient));
            message.setSubject(mailType.title);
            if (mailType == MailType.NEW_PASSWORD) {
                message.setContent(getNewPasswordContent(newPassword), "text/html");
            } else {
                message.setContent(getForgotPasswordContent(newPassword), "text/html");
            }
            return message;

        } catch (Exception ex) {
            Logger.getLogger(AutoSentMail.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
