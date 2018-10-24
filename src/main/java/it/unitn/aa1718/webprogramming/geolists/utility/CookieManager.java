package it.unitn.aa1718.webprogramming.geolists.utility;

import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.servlet.http.Cookie;


public class CookieManager{
    private Cookie[] cookies = null;
    
    public CookieManager(Cookie[] cookies) {
        this.cookies = cookies;
    }
    
    /**
     * controlla che il cookie con il nome specificato sia associato ad un utente in User
     * @return true -> trovato nel database.     false -> non trovato nel database
     */
    public boolean checkExistenceUser(String name){
        UserDAO db = new UserDAO();
        boolean res = false;
        
        for(Cookie c : this.cookies){
            if(c.getName().equals(name)){
                User u = db.get(c.getValue()).get();
                if(u != null)
                    res = true;
            }
        }
        
        return res;
    }
    
    /**
     * 
     * @param name
     * @return ritorna il cookie con il nome specificato aggiornato e aggionra il database oppure null in caso di fallimento. 
     *          Da usare solamente se si è certi dell'esistenza del cookie nel database!! (dopo avere usato checkExistance!)
     * @throws NoSuchAlgorithmException 
     */
    public Cookie updateUser(String name) throws NoSuchAlgorithmException{
        
        Random rand = new Random();
        int n = rand.nextInt(50000)+1;

        // genero e assegno il nuovo cookie alla sessione
        String cookieVal = HashGenerator.Hash(Integer.toString(n));
        Cookie cookie = new Cookie(name, cookieVal);
        cookie.setMaxAge(20);

        // aggiorno database
        UserDAO db = new UserDAO();
        User u = db.get("rappasta").get();
        u.setCookie(cookieVal);
        db.update(u.getId(), u);

        System.out.println("COOKIE AGGIORNATO");

      
        return cookie;
    }
    
    
    
    
    
}
