package it.unitn.aa1718.webprogramming.geolists.utility;

import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.servlet.http.Cookie;


public class CookiesManager{
    private Cookie[] cookies = null;
    
    public CookiesManager(Cookie[] cookies) {
        this.cookies = cookies;
    }
    
    public CookiesManager() {
    }
    
    
    /**
     * controlla che il cookie con il nome specificato sia associato ad un utente in User
     * @param name  nome del cookie di cui controllare l'esistenza della associazione
     * @return Optional con quel Cookie se trovato altrimenti empty
     */
    public Optional<User> checkExistenceCookie(String name){
        UserDAO db = new UserDAO();
        List<User> lu = db.getAll();
        boolean res = false;
        
        //controllo tra i miei cookies che ci sia quello che cerco e cioè "Cookie"
        if(this.cookies != null){
            for(Cookie c : this.cookies){
                if(c.getName().equals(name)){
                    //appena trovato controllo che ci sia almeno uno User con quel cookie
                    for (User u : lu){
                        if(u.getCookie().equals(c.getValue()))
                            return Optional.of(u);
                    }
                }
            }
        }
        
        return Optional.empty();
    }
    
    /**
     * 
     * @param name  nome del cookie da cambiare  
     * @param uOld  nome dello user a cui cambiare il cookie
     * @return      ritorna il cookie con il nome specificato aggiornato e aggionra il database oppure null in caso di fallimento. 
     *              Da usare solamente se si è certi dell'esistenza del cookie nel database!! (dopo avere usato checkExistance!)
     * @throws      NoSuchAlgorithmException 
     */
    public Cookie updateUser(String name, User uOld) throws NoSuchAlgorithmException{
        UserDAO db = new UserDAO();
        
        // genero un numero random per creare il Cookie
        Random rand = new Random();
        int n = rand.nextInt(50000)+1;

        // genero il nuovo cookie della sessione
        String cookieVal = HashGenerator.Hash(Integer.toString(n));
        Cookie cookieNew = new Cookie(name, cookieVal);
        cookieNew.setMaxAge(30000000);

        // aggiorno il database
        User uNew = db.get(uOld.getUsername()).get();
        uNew.setCookie(cookieVal);
        db.update(uOld.getId(), uNew);

        System.out.println("COOKIE AGGIORNATO");

      
        return cookieNew;
    }
    
    
    
    
    
}
