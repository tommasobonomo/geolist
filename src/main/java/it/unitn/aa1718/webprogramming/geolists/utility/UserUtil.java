/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.utility;

import it.unitn.aa1718.webprogramming.geolists.database.UserAnonimousDAO;
import it.unitn.aa1718.webprogramming.geolists.database.UserDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.database.models.UserAnonimous;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author root
 */
public class UserUtil {
    
    public UserUtil(){}
    
    /**
     * get user from cookie of session
     * @param request
     * @return 0 if no exist or id if exist
     * @deprecated 
     */
    public long getUserID(HttpServletRequest request){
        
        
        Cookie[] cookies = request.getCookies();
        String thisCookie = "noCookie";
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Cookie")) {
                    thisCookie=cookie.getValue();
                }
            }
        }
        
        UserDAO u = new UserDAO();
        Optional<User> res = u.getUser(thisCookie);
        
        if(res.isPresent())
            return res.get().getId();
        else
            return 0;
            
    }
    
    /**
     * get userID from cookie of session
     * @param request
     * @return
     */
    public Optional<Long> getUserOptionalID(HttpServletRequest request){
        
        
        Cookie[] cookies = request.getCookies();
        String thisCookie = "noCookie";
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Cookie")) {
                    thisCookie=cookie.getValue();
                }
            }
        }
        
        UserDAO u = new UserDAO();
        Optional<User> res = u.getUser(thisCookie);
        
        if(res.isPresent())
            return Optional.of(res.get().getId());
        else
            return Optional.empty();
            
    }
    
    /**
     * get user from cookie
     * @param request
     * @return
     */
    public Optional<User> getUserOptional(HttpServletRequest request){
        
        
        Cookie[] cookies = request.getCookies();
        String thisCookie = "noCookie";
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Cookie")) {
                    thisCookie=cookie.getValue();
                }
            }
        }
        
        UserDAO u = new UserDAO();
        return u.getUser(thisCookie);
    }
    
    /**
     * get userID from cookie of session
     * @param request
     * @return
     */
    public Optional<Long> getUserAnonymousOptionalID(HttpServletRequest request){
        
        
        Cookie[] cookies = request.getCookies();
        String thisCookie = "noCookie";
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Cookie")) {
                    thisCookie=cookie.getValue();
                }
            }
        }
        
        UserAnonimousDAO u = new UserAnonimousDAO();
        u.getFromCookie(thisCookie);
        Optional<UserAnonimous> res = u.getFromCookie(thisCookie);
        
        if(res.isPresent())
            return Optional.of(res.get().getId());
        else
            return Optional.empty();
            
    }
    
    /**
     * get user from cookie
     * @param request
     * @return
     */
    public Optional<UserAnonimous> getUserAnonymousOptional(HttpServletRequest request){
        
        
        Cookie[] cookies = request.getCookies();
        String thisCookie = "noCookie";
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Cookie")) {
                    thisCookie=cookie.getValue();
                }
            }
        }
        
        UserAnonimousDAO u = new UserAnonimousDAO();
        return u.getFromCookie(thisCookie);
    }
    
    /**
     * get user from cookie
     * @param request
     * @return
     */
    public Optional<Cookie> getCookie(HttpServletRequest request){
        
        
        Cookie[] cookies = request.getCookies();
        String thisCookie = "noCookie";
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Cookie")) {
                    return Optional.of(cookie);
                }
            }
        }
        
        return Optional.empty();
    }
    
}
