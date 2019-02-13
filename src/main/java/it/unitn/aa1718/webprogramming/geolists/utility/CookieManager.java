package it.unitn.aa1718.webprogramming.geolists.utility;

import it.unitn.aa1718.webprogramming.geolists.database.UserAnonimousDAO;
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.database.models.UserAnonimous;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


public class CookieManager{
    private Cookie cookie = null;
    private final int MAXAGE = 120;
    
    
    /**
     * costruttore che prende come parametro una serie di cookie e setta come this.cookie il cookie con nome "Cookie"
     * @param cookies lista di cookies da request.getCookies
     */
    public CookieManager(Cookie[] cookies) {
        if(cookies != null){
            for(Cookie c : cookies){
                if ("Cookie".equals(c.getName())){
                    this.cookie = c;
                }
            }
        }
    }
    
    /**
     * costruttore vuoto, usato SOLAMENTE PER FUNZIONI COME "updateUser"
     */
    public CookieManager() {
    }
    
    
    /**
     * controlla che il cookie con il nome specificato sia associato ad un utente in User
     * @return Optional di User con quel Cookie se trovato altrimenti Null
     */
    public Optional<User> checkExistenceUser(){
        UserDAO db = new UserDAO();
        List<User> lu = db.getAll();
        
        //controllo tra i miei cookies che ci sia quello che cerco e cioè "Cookie"
        if(this.cookie != null){
            //appena trovato controllo che ci sia almeno uno User con quel cookie
            for (User u : lu){
                if(u.getCookie()!= null && u.getCookie().equals(this.cookie.getValue()))
                    return Optional.of(u);
            }
        }
        
        return Optional.empty();
    }
    
    
    /**  
     * preso uno user controllo se esiste un cookie associato a lui e lo ritorno oppure ne creo uno.
     * DA USARE SOLAMENTE CON UN UTENTE PRESENTE NEL DATABASE!
     * @param u     user a cui controllare il cookie
     * @return      il cookie inerente all'utente o null
     */
    public Cookie setCookieOldUser(User u){
        
        Cookie cookieNew = null;
        String cookieVal = u.getCookie();
        String cookie ="";   
        
        if (cookieVal == null){
            Random rand = new Random();
            int n = rand.nextInt(5000000)+1;
            cookieVal = Integer.toString(n);
            
        try {
             cookie = HashGenerator.Hash(cookieVal);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CookieManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    
            
            UserDAO db = new UserDAO();
            u.setCookie(cookie);
            db.updateWithoutImage(u.getId(), u);
        }
   

      
        
        
        
        
        cookieNew = new Cookie("Cookie", cookie);
        cookieNew.setMaxAge(MAXAGE);
        cookieNew.setPath("/");
        
        return cookieNew;
    }
    
    
    
    /**
     * controlla che il cookie con il nome specificato sia associato ad un utente in AnonimousUser
     * @return User con quel Cookie se trovato altrimenti Null
     */
    public Optional<UserAnonimous> checkExistenceAnonimous(){
        UserAnonimousDAO db = new UserAnonimousDAO();
        List<UserAnonimous> lu = db.getAll();
        
        //controllo che il mio cookie sia stato settato
        if(this.cookie != null){
            //appena trovato controllo che ci sia almeno uno User con quel cookie
            for (UserAnonimous u : lu){
                if(u.getCookie().equals(this.cookie.getValue())){
                    System.out.println("TROVATO ANONIMO NEL DATABASE");
                    return Optional.of(u);
                }
            }
        }
        
        return Optional.empty();
    }

    /**
     * nel caso sia la prima volta che un utente accede creo un nuovo cookie anonimo
     * @param response 
     */
    public Optional<UserAnonimous> createAnonimous(HttpServletResponse response) {
        UserAnonimousDAO db = new UserAnonimousDAO();
        Cookie c = null;
        
        // genero il cookieVal
        Random rand = new Random();
        int n = rand.nextInt(5000000)+1;
        String cookieVal = Integer.toString(n);
        
        String cookie ="";

        try {
             cookie = HashGenerator.Hash(cookieVal);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CookieManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
        // inserisco nel database
        UserAnonimous ua = new UserAnonimous(n, cookie);
        db.create(ua);
        
        c = new Cookie("Cookie", cookie);
        c.setPath("/");
        c.setMaxAge(60*60*24*365);
        
        response.addCookie(c);
        
        return Optional.of(ua);
    }
}
