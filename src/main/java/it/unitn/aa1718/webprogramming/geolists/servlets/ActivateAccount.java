 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import java.io.IOException;
import java.io.PrintWriter;
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

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ActivateAccount</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ActivateAccount at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    
    public boolean toolate(long dbTIME, long acTIME){
        boolean res=false;
        if(acTIME-dbTIME < 86400000)
            res= true;
        return res;
    }
    
    @Override
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
       // processRequest(request, response);
                
        String email = request.getParameter("email");
        String token = request.getParameter("token");     
        String timedb = request.getParameter("time");     
        
        
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long a=timestamp.getTime();
        //String timeac = timestamp.toString();
        System.out.println("\n\n\nTIME PRIMA" +timedb);
        System.out.println("\n\n\nTIME DOPO" +a);
        
        long b =Long.parseLong(timedb);
        
        UserDAO userdb = new UserDAO();
        if(userdb.getToken(email, token).isPresent() && toolate(b,a)){//controllo se presente
            User u= userdb.getToken(email, token).get();
            u.setIsActive(true); //setto attivo
            userdb.update(u.getId(), u);
            //apre pagina dove si fa il login
            request.getRequestDispatcher("/ROOT/email/login.jsp").forward(request, response);
        }
        else{
            //apre pagina di registrazione se non presente il token nel database
            System.out.print("ERRORE");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            
        }
        
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    

}
