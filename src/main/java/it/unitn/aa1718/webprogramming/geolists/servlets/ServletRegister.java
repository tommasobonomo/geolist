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
import java.io.InputStream;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.Part;



@WebServlet(
        name = "ServletRegister",
        urlPatterns = "/form-actions/register"
)
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class ServletRegister extends HttpServlet {
     
    Random rand = new Random();
    String username="", email="", name="", lastname="", password="",
            cookie= Integer.toString(rand.nextInt(5000000)+1), 
            token="", time="", timeToken="";
    InputStream image = null;
    boolean admin=false, active=false;
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = null;
        action =(String) request.getParameter("action");
        switch (action){
            case "view":
                request.getRequestDispatcher("/ROOT/register/register.jsp").forward(request, response);
                break;
            case "send":
                sendRegister(request, response);
                break;
            default:
                break;
        }        
        
    }
    
    void sendRegister(HttpServletRequest request, HttpServletResponse response)
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
        
        // carico immagine uploadata
        try {
            Part filePart = request.getPart("File");
            if (filePart != null) {
                // ottengo InputStream da File caricato
                this.image = filePart.getInputStream();
            }
        } catch (IOException | ServletException ex) {
            Logger.getLogger(ItemRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //variabili varie
        ParametersController pc = new ParametersController();
        Boolean error = false;
        request.setAttribute("passwordError", false);
        request.setAttribute("emailError", false);
        request.setAttribute("usernameError", false);
        request.setAttribute("nameError", false);
        request.setAttribute("surnameError", false);
        request.setAttribute("termsError", false);
        
        //controlli
        if(!pc.passwordCtrl(this.password)){  //controllo password
            error = true;
            request.setAttribute("passwordError", true);
        }
        if(!pc.surnameCtrl(this.lastname)){  //controllo password
            error = true;
            request.setAttribute("surnameError", true);
        }
        if(!pc.nameCtrl(this.name)){  //controllo password
            error = true;
            request.setAttribute("nameError", true);
        }
        if (!pc.emailCtrl(this.email)){  //controllo la mail
            error = true;
            request.setAttribute("emailError", true);
        }
        if (!pc.usernameCtrl(this.username)){
            error = true;
            request.setAttribute("usernameError", true);
        }
        if (!"on".equals(request.getParameter("terms"))){
            error = true;
            request.setAttribute("termsError", true);
        }
        
        
        //controllo se c'è stato un errore
        if(!error){
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
        else{
            request.getRequestDispatcher("/ROOT/register/register.jsp").forward(request, response);
        }
        
    }


// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}