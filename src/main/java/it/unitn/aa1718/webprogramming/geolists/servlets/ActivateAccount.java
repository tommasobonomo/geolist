 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lorenzo
 */
public class ActivateAccount extends HttpServlet {


    //confronto il tempo registrato nel token con il tempo registrato quando il link viene effettuato, se la loro differenza e' superiore a 30 minuti, allora ritorna falso
    public boolean toolate(long dbTIME, long acTIME){
        boolean res=false;
        if(acTIME-dbTIME < 1800000) 
            res= true;
        return res;
    }
    
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
        long a=timestamp.getTime();       
        long b =Long.parseLong(timedb);
        
        UserDAO userdb = new UserDAO();
        if(userdb.getToken(email, token).isPresent() && toolate(b,a)==true){//controllo se presente e se il token non e' scaduto
            User u= userdb.getToken(email, token).get();
            u.setIsActive(true); //setto attivo
            userdb.update(u.getId(), u);
            //apre pagina dove si fa il login
            request.getRequestDispatcher("/ROOT/email/login.jsp").forward(request, response);
        }
        else{
            //apre pagina di registrazione se non presente il token nel database
            System.out.print("ERRORE");
            request.getRequestDispatcher("/ROOT/email/register.jsp").forward(request, response);   
        }
           
    }
}
