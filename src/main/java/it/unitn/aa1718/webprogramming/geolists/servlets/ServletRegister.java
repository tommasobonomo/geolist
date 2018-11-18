package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.UserAnonimousDAO;
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
import it.unitn.aa1718.webprogramming.geolists.utility.ParametersController;
import java.sql.Timestamp;
import javax.servlet.annotation.WebServlet;
import it.unitn.aa1718.webprogramming.geolists.database.models.UserAnonimous;
import java.util.Optional;
import javax.servlet.http.Cookie;



@WebServlet(
        name = "ServletRegister",
        urlPatterns = "/form-actions/register"
)
public class ServletRegister extends HttpServlet {
     
    Random rand = new Random();
    String username, email, name, lastname, password,
            cookie= Integer.toString(rand.nextInt(5000000)+1), 
            image = "IMAGEN", token, time, timeToken;
    boolean admin=false, active=false;
    
    @Override
    protected void  doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/ROOT/email/register.jsp").forward(request, response);
     
    }
     
    
    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void  doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        //con questo mi prendo l'utente anonimo con il cookie della richiesta
        Cookie[] cookies = request.getCookies();
        String thisCookie= "noCookie";
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Cookie")) {
                    thisCookie=cookie.getValue();
                }
            }
        }
        
        Optional<UserAnonimous> utenteAnonimo = (new UserAnonimousDAO()).getFromCookie(thisCookie);
        
        
        
        //prendo valori variabili dalla richiesta
        this.username = request.getParameter("UserName");
        this.name = request.getParameter("FirstName");
        this.lastname = request.getParameter("LastName");
        this.email = request.getParameter("Email");
        this.password = request.getParameter("Password");
        
        
        ParametersController pc = new ParametersController();
        //controllo esistenza user
        boolean unameNew = pc.isUnameNew(this.username);
        //controllo esistenza email
        boolean emailNew = pc.isEmailNew(this.email);
        //controllo la email
        boolean emailCheck = pc.emailCtrl(this.email);
        //controllo la password
        boolean passCheck = pc.passwordCtrl(this.password);
        
        
        if(!passCheck){  //controllo password
            System.out.println("PASSWORD NON CORRETTA, DEVE CONTENERE UN NUMERO, UNA LETTERA E UN CARATTERE SPECIALE");    
        }else if (!emailCheck){  //controllo la mail
            System.out.println("EMAIL NON CORRETTA, DEVE CONTENERE UNA @,UN CARATTARE DAVANTI ALLA \"@\", UN \".\" DOPO LA @ E CON UN DOMINIO DOPO IL \".\"");
        }else if (!unameNew){
            System.out.println("USER GIA PRESENTE NEL DATABASE");
        }else if (!emailNew){
            System.out.println("EMAIL GIA PRESENTE NEL DATABASE");
        }else{
            //creo il token (PER ORA A RANDOM)
            this.token = DigestUtils.md5Hex(""+this.rand.nextInt(999999999));
            //creo user che andrò a ficcare nel database e lo inserisco
            User u = new User(this.cookie, this.username, this.name, this.lastname, 
                              this.email, this.password, this.image, this.token, false, false);
           
            
            UserAnonimousDAO uaDAO = new UserAnonimousDAO();
            
            if(utenteAnonimo.isPresent()) // per evitare null pointer
                uaDAO.becomeUserRegister(utenteAnonimo.get(), u);
            else //in caso di errore nei cookie comunque viene aggiunto al db
                (new UserDAO()).create(u);
            
            
            //mi salvo il tempo attuale, che inviero' dopo nell'email
            Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // salva in millisecondi da quando e' stato schiacciato il tasto per registrare
            long time=timestamp.getTime();
            this.timeToken=Long.toString(time);

            //invio l'email attraverso l'email sender
            EmailSender es = new EmailSender(this.email, this.token, this.timeToken);
            es.sendEmail();
            
            //mando l'utente nella pagina di corretto invio della mail
            request.getRequestDispatcher("/ROOT/email/verify.jsp").forward(request, response);
        }
        
        //mando l'utente nella pagina di errore
        request.getRequestDispatcher("/ROOT/email/error.jsp").forward(request, response);
        
    }
}
