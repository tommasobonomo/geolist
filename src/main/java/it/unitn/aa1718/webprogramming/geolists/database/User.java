/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

/**
 *
 * @author giorgiosgl
 */
public class User {
    private String username;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private boolean isAdmin;
    
    public User(String username,String name,String lastname,String email,String Password,boolean isAdmin){
    }
    
    public void setUsername(String username){
        this.username=username;
    }
    
    public void setName(String name){
        this.name=name;
    }
    
    public void setLastname(String lastname){
        this.lastname=lastname;
    }
    
    public void setPassword(String password){
        this.password=password;
    }
    
    public void setIsAdmin(boolean admin){
        this.isAdmin=admin;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getLastname(){
        return this.lastname;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public boolean isAdmin(){
        return this.isAdmin;
    }
    
}
