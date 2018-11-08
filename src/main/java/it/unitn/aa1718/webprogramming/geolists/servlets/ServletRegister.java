package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.utility.EmailSender;
import java.util.Random;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ServletRegister extends HttpServlet {
    
    Random rand = new Random();
    String username, email, name, lastname, password,
            cookie= Integer.toString(rand.nextInt(5000000)+1), 
            image = "IMAGEN", token;
    boolean admin=false, active=false;

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void  doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        //prendo valori variabili dalla richiesta
        this.username = request.getParameter("UserName");
        this.name = request.getParameter("FirstName");
        this.lastname = request.getParameter("LastName");
        this.email = request.getParameter("Email");
        this.password = request.getParameter("Password");

        //controllo la password
        boolean passCheck = passwordCtrl(this.password);
        boolean emailCheck = emailCtrl(this.email);
        if(!passCheck){
            request.getSession().setAttribute("errMsgPass", "Your password needs to contain at least a digit, a letter and a special character");    
        }else if (!emailCheck){
            request.getSession().setAttribute("errMsgEmail", "Your email is incorrect");
        }else{
            //creo il token (PER ORA A RANDOM)
            this.token = DigestUtils.md5Hex(""+this.rand.nextInt(999999999));

            //creo user che andrò a ficcare nel database e lo inserisco
            User u = new User(this.cookie, this.username, this.name, this.lastname, 
                              this.email, this.password, this.image, this.token, false, false);
            UserDAO UD = new UserDAO();
            UD.create(u);

            //invio l'email attraverso l'email sender
            EmailSender es = new EmailSender(email,token);
            es.sendEmail();
        }
        
        // faccio tornare il tutto alla root per ora
        response.sendRedirect("/");
        
    }
    
    
    /**
     * funzione che controlla che la password abbia le caratteristiche richieste
     * @param password la password da controllare
     * @return true nel caso la password abbia almeno una lettera, un numero e un carattere speciale
     * false altrimenti
     */
    private boolean passwordCtrl(String password){
        
        // creo i pattern necessari per il controllo
        Pattern letters = Pattern.compile("[a-zA-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("[^A-Za-z0-9]");
       
        // creo i matcher che controllano i pattern
        Matcher hasLetters = letters.matcher(password);
        Matcher hasNumber = digit.matcher(password);
        Matcher hasSpecial = special.matcher(password);
        
        return hasLetters.find() && hasNumber.find() && hasSpecial.find();
    }
    
    /**
     * function that check if the email is written correctly
     * @param email email to check
     * @return true if the email is written correctly false otherwise
     */
    private boolean emailCtrl(String email){
        
        if(!email.contains("@")){
            return false;
        }else{
            email = email.substring(email.indexOf("@"));
            if(!email.contains(".")){
                return false;
            }
        }
        return true;
    }

}
