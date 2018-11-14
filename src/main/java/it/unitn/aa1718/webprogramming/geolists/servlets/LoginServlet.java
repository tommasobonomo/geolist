package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.utility.CookieManager;
import it.unitn.aa1718.webprogramming.geolists.utility.HashGenerator;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name="LoginServlet",
        urlPatterns = "/form-actions/login"
)
public class LoginServlet extends HttpServlet {
    
    private static final String DB_URL = "jdbc:derby://localhost:1527/GEODB";  // link al database in localhost

    // Funzione di gestione del metodo GET della pagina principale dell'applicazione
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Variabili dove salvare risultato query
        boolean logged = false;
        String username = "";
        
        // Provo a caricare il driver necessario per il collegamento al database
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");            
        } catch (ClassNotFoundException ex) {
            System.out.println("DRIVER NON TROVATO");
        }
        
        
        try {    
            // Genero l'hash per controllare la password
            String hash = HashGenerator.Hash(request.getParameter("password"));
            
            // Uso i DAO per controllare che l'utente ci sia nel database
            UserDAO db = new UserDAO();
            User u = null;
            
            try{
                u = db.get(request.getParameter("username")).get();
            }catch(Exception e){}
            
            if(u != null){
                String password = u.getPassword();
                if(password.equals(hash)){
                    logged = true;
                    username = u.getUsername();

                    // qui sono sicuro che lo user esiste già
                    System.out.println("USER TROVATO NEL DATABASE");
                    CookieManager cm = new CookieManager();

                    Cookie c = cm.setCookieOldUser(u);
                    response.addCookie(c);
                }
            }
            
            
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.toString());
        } 
        
        // Aggiungi attributi a richiesta in modo da passarla al JSP
        request.getSession().setAttribute("logged", logged);
        request.getSession().setAttribute("username", username);
        
        // Indirizza a servlet di Landing
        // request.getRequestDispatcher("/ROOT/LandingPage.jsp").forward(request, response);
        // getServletContext().getNamedDispatcher("LandingServlet").forward(request, response);
        response.sendRedirect("/");
    }
    
    
    // Nel caso serva questa è la funzione che restituisce le info sulla servlet
    @Override
    public String getServletInfo() {
        return "This is the servlet that handles requests in the main root of the server";
    }

}
