/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

package it.unitn.aa1718.webprogramming.geolists.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletRoot", urlPatterns = {"/login"})
public class ServletEmail extends HttpServlet {
    
    private static final String DB_URL = "jdbc:derby://localhost:1527/GEODB";  // link al database in localhost

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        response.setContentType("text/html;");
        PrintWriter writer = response.getWriter();
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");            
        } catch (ClassNotFoundException ex) {
            System.out.println("DRIVER NON TROVATO");
        }
        
        try {    
            Connection conn = DriverManager.getConnection(DB_URL, "GEODB", "GEODB");
            
            Statement stmt = conn.createStatement();
            String query =    "select * "
                            + "from users "
                            + "where username=\'" + request.getParameter("username")
                            + "\' and password=\'"+ request.getParameter("password")+"\'";
            ResultSet rs = stmt.executeQuery(query);

            
            
            
            stmt.close();
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
       
        writer.close();
        
    }
    
    
    @Override
    public String getServletInfo() {
        return "This is the servlet that handles requests in the main root of the server";
    }

    
    @Entity
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
     
    private String token;
    
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    //private User user;
     
    private Date expiryDate;
    
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
     
    // standard constructors, getters and setters
}
    
     public void sendEmail(HttpServletRequest request){
        try{

            String host ="smtp.gmail.com" ;
            String user = ("geolistunitn");
            String pass = ("Geolist2018");
            //String to = request.getParameter("email");
            String to = ("lorenzo.framba6@gmail.com");
            String from = ("geolistunitn@gmail.com");
            String subject = "This is confirmation number for your GeoList account. Please insert this number to activate your account.";
            String messageText = request.getParameter("BRAVO TI SEI ISCRITTO CON SUCCESSO");
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            //props.put("mail.smtp.host", host);
            props.setProperty("mail.smtp.host", host);    
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            
            Message messaggio = new MimeMessage(mailSession);
            messaggio.setFrom(new InternetAddress(from));
            
            InternetAddress[] address = {new InternetAddress(to)};
            
            messaggio.setRecipients(Message.RecipientType.TO, address);
            messaggio.setSubject(subject); 
            messaggio.setSentDate(new Date());
            messaggio.setText(messageText);

           Transport transport=mailSession.getTransport("smtp");
           transport.connect(host, user, pass);
           transport.sendMessage(messaggio, messaggio.getAllRecipients());
           transport.close();
           
           System.out.println("message send successfully");
        }catch(Exception ex)
        {
            System.out.println(" ERRORI "+ex);
        }

    }
}
 */