package it.unitn.aa1718.webprogramming.geolists.utility;


import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
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
        final String ServerEmail = ("geolistunitn");
        final String ServerPass = ("Geolist2018");
        String UserAddress = (this.userEmail);
        String from = ("geolistunitn@gmail.com");
        String subject = "This is confirmation number for your GeoList account";
        String messageText = ("Verification Link :: http://localhost:8080/geolist/servlets/ActivateAccount?key1="+ this.userEmail+"&key2="+ this.token );
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);    
        props.put("mail.smtp.port", "587");//465

        //props.put("mail.smtp.starttls.required", "true");
        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        

//            Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentification(){
//                        return new PasswordAuthentication(ServerEmail,ServerPass);
//                    }
//            });
            
        try{
            
            
            InternetAddress[] address = {new InternetAddress(UserAddress)};
            
            Message messaggio = new MimeMessage(mailSession);
            messaggio.setFrom(new InternetAddress(from));
            messaggio.setRecipients(Message.RecipientType.TO, address);
            messaggio.setSubject(subject); 
            messaggio.setSentDate(new Date());
            messaggio.setText(messageText);

            Transport.send(messaggio);
           
           
        }catch(Exception ex)
        {
            System.out.println("SendingEmail..x "+ex);
        }

    }
        
}
