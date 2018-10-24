package it.unitn.aa1718.webprogramming.geolists.servlets;

import java.util.Random;
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

@WebServlet(name = "ServletRegister", urlPatterns = {"/register"})
public class ServletRegister extends HttpServlet {
    
    private static final String DB_URL = "jdbc:derby://localhost:1527/GEODB";  // link al database in localhost

    Random rand = new Random();
    String USERNAME, EMAIL, NAME, LASTNAME, PASSWORD, COOKIE,IMAGE;
    int ID;
    boolean ADMIN=false;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
            ID = rand.nextInt(1000000)+1;
            IMAGE = COOKIE = " ";
           
            USERNAME=request.getParameter("username");
            EMAIL=request.getParameter("email");
            NAME=request.getParameter("name");
            LASTNAME=request.getParameter("lastname");
            PASSWORD=request.getParameter("password");
            
 
            String query=" insert into GEODB.USERS(USERNAME,\"NAME\",LASTNAME,EMAIL,IMAGE, PASSWORD, \"ADMIN\")"
                    + " values('"+ID+"','"+COOKIE+"','"+USERNAME+"','"+NAME+"','"+LASTNAME+"','"+EMAIL+"','"+IMAGE+"','"+PASSWORD+"','"+ADMIN+"')";
            stmt.execute(query);
          //  System.out.println("<h1> USERNAME:"+USERNAME+", EMAIL "+EMAIL+"</h1>");
            writer.write("USERNAME:"+USERNAME+", EMAIL "+EMAIL);
            
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
