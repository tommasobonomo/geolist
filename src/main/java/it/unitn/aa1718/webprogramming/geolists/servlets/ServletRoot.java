package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.utility.HashGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletRoot", urlPatterns = {"/login"})
public class ServletRoot extends HttpServlet {
    
    private static final String DB_URL = "jdbc:derby://localhost:1527/GEODB";  // link al database in localhost

    // Funzione di gestione del metodo GET della pagina principale dell'applicazione
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        // Setto il contenxt della risposta della servlet
        response.setContentType("text/html;");
        PrintWriter writer = response.getWriter();
        
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
            
            // controllo che il risultato sia presente nel database
            boolean trovato = true;
            if (!rs.next() ) {
                trovato = false;
            }
            
            if( trovato == false ){
                writer.write("non è presente nel database");
            } else{
                writer.write("è presente nel database");
            }
            
            conn.close();
            stmt.close();
            
        } catch (SQLException | NoSuchAlgorithmException ex) {
            System.out.println(ex.toString());
        }
       
        writer.close();
        
        
    }
    
    
    // Nel caso serva questa è la funzione che restituisce le info sulla servlet
    @Override
    public String getServletInfo() {
        return "This is the servlet that handles requests in the main root of the server";
    }

}
