package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.utility.CookiesManager;
import it.unitn.aa1718.webprogramming.geolists.utility.HashGenerator;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
            // Attivo la connessione al DB
            Connection conn = DriverManager.getConnection(DB_URL, "GEODB", "GEODB");
            
            // Genero l'hash per controllare la password
            String hash = HashGenerator.Hash(request.getParameter("password"));
            
            //creo lo statement che serve a interrogare il DB
            Statement stmt = conn.createStatement();
            String query =    "select * "
                            + "from users "
                            + "where username=\'" + request.getParameter("username")
                            + "\' and password=\'"+ hash +"\'"; //utilizzo l'hash ottenuto prima
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()) {
                logged = true;
                username = rs.getString("username");
                
                // qui sono sicuro che lo user esiste già
                System.out.println("USER TROVATO NEL DATABASE");
                CookiesManager cm = new CookiesManager();
                User u = new UserDAO().get(username).get();
                if (u != null){
                    Cookie c = cm.updateUser("Cookie", u);
                    //NON FUNZIA!!!
                    c.setPath("/");
                    response.addCookie(c);
                    System.out.println(c.getValue());
                }
            }
            
            conn.close();
            stmt.close();
            
        } catch (SQLException | NoSuchAlgorithmException ex) {
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
