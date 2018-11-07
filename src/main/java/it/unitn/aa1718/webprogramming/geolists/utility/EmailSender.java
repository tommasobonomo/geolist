package it.unitn.aa1718.webprogramming.geolists.utility;


import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;


/**
 * @author Lorenzo
 */
public class EmailSender {
    private String userEmail;
    private String token;

    /**
     * classe che gestisce costruisce e invia le email agli user che vogliono registrarsi
     * @param userEmail email dello user (a cui inviare la mail)
     * @param token token che lo user userà per identificare il proprio account
     */
    public EmailSender(String userEmail, String token) {
        this.userEmail = userEmail;
        this.token = token;
    }

    
    public void sendEmail(){
        
        
        String host ="smtp.gmail.com" ;
        String UserAddress = (this.userEmail);
        String from = ("geolistunitn@gmail.com");
        String subject = "This is confirmation number for your GeoList account";
        String messageText = (" Hello, Thanks for Subscribing to Our Website"
                + "Click the link to activate your account "
                + "Verification Link :: http://localhost:8080/geolist/servlets/ActivateAccount?key1="+ this.userEmail+"&key2="+ this.token );
        Properties props = new Properties();

        Authenticator auth = new SMTPAuthenticator();
        
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);    
        props.put("mail.smtp.port", "587");

        Session mailSession = Session.getDefaultInstance(props, auth);
        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        
        try{
            InternetAddress[] address = {new InternetAddress(UserAddress)};
            
            Message messaggio = new MimeMessage(mailSession);
            
            messaggio.setFrom(new InternetAddress("geolistunitn@gmail.com"));
            messaggio.setRecipients(Message.RecipientType.TO, address);
            messaggio.setSubject(subject); 
            messaggio.setSentDate(new Date());
            messaggio.setText(messageText);
            Transport.send(messaggio);
        }catch(MessagingException ex)
        {
            System.out.println("SendingEmail....x "+ex);
        }

    }
        
    
       private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
           String username = "geolistunitn";
           String password = "Geolist2018";
           return new PasswordAuthentication(username, password);
        }
    }
}
