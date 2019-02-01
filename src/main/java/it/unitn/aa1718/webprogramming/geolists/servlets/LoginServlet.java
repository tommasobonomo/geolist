package it.unitn.aa1718.webprogramming.geolists.servlets;

import it.unitn.aa1718.webprogramming.geolists.database.UserAnonimousDAO;
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.database.models.UserAnonimous;
import it.unitn.aa1718.webprogramming.geolists.utility.CookieManager;
import it.unitn.aa1718.webprogramming.geolists.utility.HashGenerator;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
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
    // Funzione di gestione del metodo GET della pagina principale dell'applicazione
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        boolean remember=true;
        //per leggere il valore della checkbox e trasformarlo in boolean
        remember = "on".equals(request.getParameter("remember"));
        //System.out.println(remember+"\n\n");
        
        boolean logged = false;
        String username = "";
        
        Cookie[] cookies = request.getCookies();
        String thisCookie= "noCookie";
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Cookie")) {
                    thisCookie=cookie.getValue();
                }
            }
        }
        
        try {    
            // Genero l'hash per controllare la password
            String hash = HashGenerator.Hash(request.getParameter("password"));
            
            // Uso i DAO per controllare che l'utente ci sia nel database
            UserDAO db = new UserDAO();

            Optional<User> userOpt = db.get(request.getParameter("username"));
            
            if(userOpt.isPresent()){
                User user = userOpt.get();
                if (user.isActive()) {
                    String password = user.getPassword();
                    if(password.equals(hash)){
                        
                        logged = true;
                        username = user.getUsername();

                        
                        UserAnonimousDAO uaDAO = new UserAnonimousDAO();
                    
                        Optional <UserAnonimous> ua = uaDAO.getFromCookie(thisCookie);
                        
                        if(ua.isPresent())
                            uaDAO.becomeUserRegister(ua.get(), user);
                    
                        CookieManager cm = new CookieManager();
                        
                        if(remember==true){//se l'utente vuole essere ricordato
                            Cookie c = cm.setCookieOldUser(user);
                            c.setMaxAge(60*60*24*7);//ricordo l'utente per una settimana
                            response.addCookie(c);
                        }
                        else{//se l'utente non vuole essere ricordato
                            Cookie c = cm.setCookieOldUser(user);
                            c.setMaxAge(60*30);//ricordo l'utente per mezz'ora
                            response.addCookie(c);
                        }
                    }
                }
            }
            
            
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.toString());
        } 
        
        // Aggiungi attributi a richiesta in modo da passarla al JSP
        request.getSession().setAttribute("logged", logged);
        request.getSession().setAttribute("username", username);
        
        // Indirizza a servlet di Landing
        //request.getRequestDispatcher("/ROOT/LandingPage.jsp").forward(request, response);
        //getServletContext().getNamedDispatcher("LandingServlet").forward(request, response);
        response.sendRedirect("/");
    }
    
    
    // Nel caso serva questa è la funzione che restituisce le info sulla servlet
    @Override
    public String getServletInfo() {
        return "This is the servlet that handles requests in the main root of the server";
    }

}
