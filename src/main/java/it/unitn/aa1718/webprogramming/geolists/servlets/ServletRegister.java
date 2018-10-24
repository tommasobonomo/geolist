package it.unitn.aa1718.webprogramming.geolists.servlets;

import java.util.Random;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ComposeDAO;
import it.unitn.aa1718.webprogramming.geolists.database.Database;
import it.unitn.aa1718.webprogramming.geolists.database.ItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ProductListDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;

@WebServlet(name = "ServletRegister", urlPatterns = {"/register"})
public class ServletRegister extends HttpServlet {
    
    private static final String DB_URL = "jdbc:derby://localhost:1527/GEODB";  // link al database in localhost

    Random rand = new Random();
    String USERNAME, EMAIL, NAME, LASTNAME, PASSWORD, COOKIE,IMAGE,TOKEN;
    int ID;
    boolean ADMIN=false, ACTIVE=false;

    /**
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void  doPost(HttpServletRequest request, HttpServletResponse response) //String
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
            
            USERNAME=request.getParameter("UserName9");
            EMAIL=request.getParameter("Email");
            NAME=request.getParameter("FirstName");
            LASTNAME=request.getParameter("LastName");
            PASSWORD=request.getParameter("Password");
            
            
            Random random = new Random();
            random.nextInt(999999);
            TOKEN = DigestUtils.md5Hex(""+random);
            
            
            RegisterUser ru = new RegisterUser();
            ru.setFirstName(NAME);
            ru.setLastName(LASTNAME);
            ru.setPassword(PASSWORD);
            ru.setUserName(USERNAME);
            ru.setEmail(EMAIL);
            ru.setToken(NAME);
            
            UserDAO UD = new UserDAO();
            User PERSONA = new User(123213, "", USERNAME, NAME, LASTNAME, EMAIL, PASSWORD, "", false);
            UD.create(PERSONA);
           
            
            
            
            String query=" insert into GEODB.USERS(USERNAME,\"NAME\",LASTNAME,EMAIL,IMAGE, PASSWORD,TOKEN, ACTIVE, \"ADMIN\")"
                    + " values('"+ID+"','"+COOKIE+"','"+USERNAME+"','"+NAME+"','"+LASTNAME+"','"+EMAIL+"','"+IMAGE+"','"+PASSWORD+"','"+TOKEN+"','"+ACTIVE+"','"+ADMIN+"')";
            stmt.execute(query);
            int i=stmt.executeUpdate(query);
            
            if(i!=0){
            
                ConfirmationEmail se = new ConfirmationEmail(EMAIL,TOKEN);
                se.sendEmail();
                conn.close();
                writer.close();
                stmt.close();
                response.sendRedirect("verify.jsp");
              //  return "SUCCESS";
            }
            
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
