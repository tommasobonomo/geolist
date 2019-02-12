/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.utility;

import it.unitn.aa1718.webprogramming.geolists.database.ItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mattia
 */
public class ParametersController {
    
    /**
     * costruttore vuoto
     */
    public void ParametersController(){}
    
    
    /**
     * funzione che controlla che la password abbia le caratteristiche richieste
     * @param password la password da controllare
     * @return true nel caso la password abbia almeno una lettera, un numero e un carattere speciale
     * false altrimenti
     */
    public boolean passwordCtrl(String password){
        
        // creo i pattern necessari per il controllo
        Pattern letters = Pattern.compile("[a-zA-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("[^A-Za-z0-9]");
       
        // creo i matcher che controllano i pattern
        Matcher hasLetters = letters.matcher(password);
        Matcher hasNumber = digit.matcher(password);
        Matcher hasSpecial = special.matcher(password);
        
        return hasLetters.find() && hasNumber.find() && hasSpecial.find();
    }
    
    
    /**
     * function that check if the email is written correctly
     * @param email email to check
     * @return true if the email is written correctly false otherwise
     */
    public boolean emailCtrl(String email){
        
        String emailNoTest = email;
        
        // controllo esistenza della "@"
        if(!email.contains("@")){
            return false;
        }else{
            if(email.indexOf("@") == 0){
                return false;
            }else{
                email = email.substring(email.indexOf("@"));
                // controllo esistenza "."
                if(!email.contains(".") || email.indexOf(".") == 0){
                    return false;
                }else{
                    // controllo esistenza di una stringa dopo il punto
                    if(email.endsWith(".")){
                        return false;
                    }
                }
            }
        }
        return true && isEmailNew(emailNoTest) && email.length()>0;
    }
    
    /**
     * function that check if the name is written correctly
     * @param name name to check
     * @return true if the name is written correctly false otherwise
     */
    public boolean nameCtrl(String name){
        return name.length()>0;
    }
    
    /**
     * function that check if the surname is written correctly
     * @param surname surname to check
     * @return true if the surname is written correctly false otherwise
     */
    public boolean surnameCtrl(String surname){
        return surname.length()>0;
    }
    
    /**
     * function that check if the username is written correctly
     * @param username username to check
     * @return true if the username is written correctly false otherwise
     */
    public boolean usernameCtrl(String username){
        return username.length()>0 && isUnameNew(username) && !username.contains(" ");
    }
    
    /**
     * function that check if the username does already exist in the database
     * @param username username to check
     * @return false if the username is already in the DB true otherwise
     */
    public boolean isUnameNew(String username){
        
        UserDAO db = new UserDAO();
        Optional<User> u = db.get(username);
        if(u.isPresent() || username.contains(" "))
            return false;
        
        return true;
    }
    
    public boolean isInameNew(String name){
        
        ItemDAO db = new ItemDAO();
        Optional<Item> u = db.get(name);
        if(u.isPresent() || name.contains(" "))
            return false;
        
        return true;
    }
      
    
    
    /**
     * function that check if the email does already exist in the database
     * @param email username to check
     * @return false if the email is already in the DB true otherwise
     */
    public boolean isEmailNew(String email){
        
        email = email.toLowerCase();
        
        UserDAO db = new UserDAO();
        Optional<User> u = db.getFromEmail(email);
        if(u.isPresent())
            return false;
        
        return true;
    }
    
}
