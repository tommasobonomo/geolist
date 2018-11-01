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
    
    
    /**
     * costruttore che prende come parametro una serie di cookie e setta come this.cookie il cookie con nome "Cookie"
     * @param cookies lista di cookies da request.getCookies
     */
    public CookiesManager(Cookie[] cookies) {
        for(Cookie c : cookies){
            if ("Cookie".equals(c.getName())){
                this.cookie = c;
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
     * @param uOld  nome dello user a cui cambiare il cookie
     * @return      ritorna il cookie con il nome specificato aggiornato e aggionra il database oppure null in caso di fallimento. 
     *              Da usare solamente se si è certi dell'esistenza del cookie nel database!! (dopo avere usato checkExistance!) 
     */
    public Cookie updateUser(User uOld){
        UserDAO db = new UserDAO();
        
        // genero un numero random per creare il Cookie
        Random rand = new Random();
        int n = rand.nextInt(5000000)+1;

        // genero il nuovo cookie della sessione
        String cookieVal = Integer.toString(n);
        Cookie cookieNew = new Cookie(this.cookie.getName(), cookieVal);
        cookieNew.setMaxAge(120);

        // aggiorno il database
        User uNew = db.get(uOld.getUsername()).get();
        uNew.setCookie(cookieVal);
        db.update(uOld.getId(), uNew);

        System.out.println("COOKIE AGGIORNATO");

      
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
