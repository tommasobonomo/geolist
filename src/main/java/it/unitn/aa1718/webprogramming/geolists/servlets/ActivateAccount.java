package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Lorenzo
 */
@WebServlet(
        name = "ActivateAccount",
        urlPatterns = "/activateAccount"
)
public class ActivateAccount extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
       // processRequest(request, response);
        
       
       //acquisisco email, token e time dal link dell'email
        String email = request.getParameter("email");
        String token = request.getParameter("token");     
        String timedb = request.getParameter("time");     
        
        
        //mi salvo il tempo timestamp attuale, che confrontero' con quello del link
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        //trasformo i 2 tempi in long
        long a = timestamp.getTime();       
        long b = Long.parseLong(timedb);
        
        UserDAO userdb = new UserDAO();
        if(userdb.getFromEmailAndToken(email, token).isPresent() && notTooLate(b,a,email)==true){//controllo se presente e se il token non e' scaduto
            User u= userdb.getFromEmailAndToken(email, token).get();
            u.setIsActive(true); //setto attivo
            userdb.updateWithoutImage(u.getId(), u);
            //apre pagina dove si fa il login
            request.getRequestDispatcher("/ROOT/register/activate.jsp").forward(request, response);
        }
        else{
            //apre pagina di registrazione se non presente il token nel database
            System.out.print("ERRORE DURANTE L'ATTIVAZIONE DELL'ACCOUNT");
            request.getRequestDispatcher("/ROOT/email/register.jsp").forward(request, response);   
        }  
    }
    
    
    /**
     * funzione che confrontoa il tempo registrato nel token con il tempo registrato quando il link viene effettuato, 
     * se la loro differenza e' superiore a 30 minuti, allora viene rifiutata la registrazione
     * @param dbTIME tempo di registrazione da parte dell'utente
     * @param acTIME tempo attuale
     * @param email email dello user da registrare
     * @return ritorna true se sono passati meno di 30 minuti false altrimenti
     */
    public boolean notTooLate(long dbTIME, long acTIME, String email){
        boolean res = false;
        if(acTIME-dbTIME <= 1800000){ 
            res = true; 
        }else{
            //cancello l'utente nel caso abbia cliccato il link con ritardo
            UserDAO db = new UserDAO();
            User u = db.getFromEmail(email).get();
            
            //cancello solamente se l'utente non è già attivo
            if (!u.isActive())
                db.delete(u.getId());
        }
        return res;
    }
}
