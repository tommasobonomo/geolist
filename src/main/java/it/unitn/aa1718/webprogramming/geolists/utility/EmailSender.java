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
    private String time;

    /**
     * classe che gestisce costruisce e invia le email agli user che vogliono registrarsi
     * @param userEmail email dello user (a cui inviare la mail)
     * @param token token che lo user userà per identificare il proprio account
     */
    public EmailSender(String userEmail, String token, String time) {
        this.userEmail = userEmail;
        this.token = token;
        this.time = time;
    }

    
    public void sendEmail(){
            
        // creo l'email
        String host ="smtp.gmail.com" ;
        String UserAddress = (this.userEmail);
        String from = ("geolistunitn@gmail.com");
        String subject = "This is the confirmation number for your GeoList account";
        String messageText = (" Hello, Thanks for subscribing to our Website\n"
                + "Click the link to activate your account! \n watch out, it only lasts for 24 hours\n "
                + "Verification Link :: http://localhost:8080/activateAccount?email="+ this.userEmail+"&token="+ this.token+"&time="+this.time);
        Properties props = new Properties();

        // ottengo permessi di autenticazione
        Authenticator auth = new SMTPAuthenticator();
        
        // setto cose
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);    
        props.put("mail.smtp.port", "587");

        Session mailSession = Session.getDefaultInstance(props, auth);
        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        
        try{
            InternetAddress[] address = {new InternetAddress(UserAddress)};

            // creo il messaggio da inviare
            Message messaggio = new MimeMessage(mailSession);           
            messaggio.setFrom(new InternetAddress(from));
            messaggio.setRecipients(Message.RecipientType.TO, address);
            messaggio.setSubject(subject); 
            messaggio.setSentDate(new Date());
            messaggio.setText(messageText);
            
            // provo ad inviare
            Transport.send(messaggio);
            
        }catch(MessagingException ex)
        {
            System.out.println("SendingEmail....x "+ex);
        }

    }
        
    
    // classe per autenticazione
    private class SMTPAuthenticator extends javax.mail.Authenticator {
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
           String username = "geolistunitn";
           String password = "Geolist2018";
           return new PasswordAuthentication(username, password);
        }
    }
}
