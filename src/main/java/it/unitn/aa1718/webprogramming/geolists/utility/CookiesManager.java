package it.unitn.aa1718.webprogramming.geolists.utility;

import it.unitn.aa1718.webprogramming.geolists.database.UserAnonimousDAO;
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.database.models.UserAnonimous;
import java.util.List;
import java.util.Random;
import javax.servlet.http.Cookie;


public class CookiesManager{
    private Cookie cookie = null;
    private final int MAXAGE = 120;
    
    
    /**
     * costruttore che prende come parametro una serie di cookie e setta come this.cookie il cookie con nome "Cookie"
     * @param cookies lista di cookies da request.getCookies
     */
    public CookiesManager(Cookie[] cookies) {
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
    public CookiesManager() {
    }
    
    
    /**
     * controlla che il cookie con il nome specificato sia associato ad un utente in User
     * @return User con quel Cookie se trovato altrimenti Null
     */
    public User checkExistenceUser(){
        UserDAO db = new UserDAO();
        List<User> lu = db.getAll();
        
        //controllo tra i miei cookies che ci sia quello che cerco e cioè "Cookie"
        if(this.cookie != null){
            //appena trovato controllo che ci sia almeno uno User con quel cookie
            for (User u : lu){
                if(u.getCookie().equals(this.cookie.getValue()))
                    return u;
            }
        }
        
        return null;
    }
    
    
    /**  
     * preso uno user controllo se esiste un cookie associato a lui e lo ritorno oppure ne creo uno.
     * Da usare solamente con un utente valido!
     * @param u     user a cui controllare il cookie
     * @return      il cookie inerente all'utente o null
     */
    public Cookie setCookieOldUser(User u){
        
        Cookie cookieNew = null;
        String cookieVal = u.getCookie();
                
        if (cookieVal == null){
            Random rand = new Random();
            int n = rand.nextInt(5000000)+1;
            cookieVal = Integer.toString(n);
            
            UserDAO db = new UserDAO();
            u.setCookie(cookieVal);
            db.update(u.getId(), u);
        }
      
        cookieNew = new Cookie("Cookie", cookieVal);
        cookieNew.setMaxAge(MAXAGE);
        
        return cookieNew;
    }
    
    
    
    /**
     * controlla che il cookie con il nome specificato sia associato ad un utente in AnonimousUser
     * @return User con quel Cookie se trovato altrimenti Null
     */
    public UserAnonimous checkExistenceAnonimous(){
        UserAnonimousDAO db = new UserAnonimousDAO();
        List<UserAnonimous> lu = db.getAll();
        
        //controllo tra i miei cookies che ci sia quello che cerco e cioè "Cookie"
        if(this.cookie != null){
            //appena trovato controllo che ci sia almeno uno User con quel cookie
            for (UserAnonimous u : lu){
                if(u.getCookie().equals(this.cookie.getValue()))
                    return u;
            }
        }
        
        return null;
    }
}
