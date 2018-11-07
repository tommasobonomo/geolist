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


public class ServletRegister extends HttpServlet {
    
    private static final String DB_URL = "jdbc:derby://localhost:1527/GEODB";  // link al database in localhost

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

        //creo il token (PER ORA A RANDOM)
        this.token = DigestUtils.md5Hex(""+this.rand.nextInt(999999999));

        //creo user che andrò a ficcare nel database e lo inserisco
        User u = new User(this.cookie, this.username, this.name, this.lastname, this.email, this.password, this.image, this.token, false, false);
        UserDAO UD = new UserDAO();
        UD.create(u);

        //invio l'email attraverso l'email sender
        EmailSender es = new EmailSender(email,token);
        es.sendEmail();
        
    }

}
