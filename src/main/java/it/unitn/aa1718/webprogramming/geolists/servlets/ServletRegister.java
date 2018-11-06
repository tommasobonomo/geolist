package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.utility.ConfirmationEmail;
import it.unitn.aa1718.webprogramming.geolists.utility.RegisterUser;
import java.util.Random;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;


public class ServletRegister extends HttpServlet {
    
    private static final String DB_URL = "jdbc:derby://localhost:1527/GEODB";  // link al database in localhost

    Random rand = new Random();
    String username, email, name, lastname, password,
            cookie= Integer.toString(rand.nextInt(5000000)+1), 
            image = "IMAGEN", token;
    boolean admin=false, active=false;

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void  doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("SONO ENTRATO!");
        
        try {    
            //connessione al database per salvare nuovo utente
            Connection conn = DriverManager.getConnection(DB_URL, "GEODB", "GEODB");
            
            //prendo valori variabili dalla richiesta
            this.username = request.getParameter("UserName");
            this.email = request.getParameter("Email");
            this.name = request.getParameter("FirstName");
            this.lastname = request.getParameter("LastName");
            this.password = request.getParameter("Password");
            
            //creo il token (PER ORA A RANDOM)
            Random random = new Random();
            random.nextInt(999999999);
            token = DigestUtils.md5Hex(""+random);
            
            //creo user che andrò a ficcare nel database
            User u = new User(this.cookie, this.username, this.name, this.lastname, this.email, this.password, this.image, this.token, false, false);
            
            UserDAO UD = new UserDAO();
            UD.create(u);
           
            
            
            
//            String query=" insert into GEODB.USERS(username,\"name\",lastname,email,image, password,token, active, \"admin\")"
//                    + " values('"+id+"','"+cookie+"','"+username+"','"+name+"','"+lastname+"','"+email+"','"+image+"','"+password+"','"+token+"','"+active+"','"+admin+"')";
//            stmt.execute(query);
//            int i=stmt.executeUpdate(query);
//            
//            if(i!=0){
//            
//                ConfirmationEmail se = new ConfirmationEmail(email,token);
//                se.sendEmail();
//                conn.close();
//                stmt.close();
//                response.sendRedirect("verify.jsp");
//              //  return "SUCCESS";
//            }
//            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
       // return null;         
    }

    
    
    @Override
    public String getServletInfo() {
        return "This is the servlet that handles requests in the main root of the server";
    }

}
