/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.utility;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Lorenzo
 */
public class ConfirmationEmail {
        private String UserEmail="CIOA";
        private String Token="LOL";

    public ConfirmationEmail(String UserEmail, String Token) {
        this.UserEmail = UserEmail;
        this.Token = Token;
    }

    
    public void sendEmail(){
        
            String host ="smtp.gmail.com" ;
            String ServerEmail = ("geolistunitn");
            String ServerPass = ("Geolist2018");
            String UserAddress = (this.UserEmail);
            String from = ("geolistunitn@gmail.com");
            String subject = "This is confirmation number for your GeoList account";
            String messageText = ("Verification Link ::  "+"http://localhost:8080/geolist/servlets/ActivateAccount?key1="+ this.UserEmail+"&key2="+ this.Token );
            Properties props = new Properties();
            
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);    
            props.put("mail.smtp.port", "587");//465
            
            //props.put("mail.smtp.starttls.required", "true");
            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            
//            Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
//                    protected PasswordAuthentification getPasswordAuthentification(){
//                    
//                        return new PasswordAuthentification(ServerEmail,ServerPass);
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
