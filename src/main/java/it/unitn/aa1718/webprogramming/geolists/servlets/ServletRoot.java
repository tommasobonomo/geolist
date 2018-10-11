package it.unitn.aa1718.webprogramming.geolists.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        response.setContentType("text/html;");
        PrintWriter writer = response.getWriter();
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");            
        } catch (ClassNotFoundException ex) {
            System.out.println("DRIVER NON TROVATO");
        }
        
        try {    
            Connection conn = DriverManager.getConnection(DB_URL, "GEODB", "GEODB");
            
            Statement stmt = conn.createStatement();
            String query =    "select * "
                            + "from users "
                            + "where username=\'" + request.getParameter("username")
                            + "\' and password=\'"+ request.getParameter("password")+"\'";
            ResultSet rs = stmt.executeQuery(query);
            
            
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
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
       
        writer.close();
        
        
    }
    
    
    @Override
    public String getServletInfo() {
        return "This is the servlet that handles requests in the main root of the server";
    }

}
