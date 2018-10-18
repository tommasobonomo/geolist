/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database.models;

/**
 * Class to model the User relation
 * @author giorgiosgl
 */
public class User {
    private long id;
    private String username;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private boolean isAdmin;
    
    public User(long id,String username,String name,String lastname,String email,String password,boolean admin){
        this.id=id;
        this.username=username;
        this.name=name;
        this.lastname=lastname;
        this.password=password;
        this.isAdmin=admin;
    }
    
    public void setId(long id){
        this.id=id;
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
    
    public void setEmail(String email) {
        this.email=email;
    }
    
    public void setIsAdmin(boolean admin){
        this.isAdmin=admin;
    }
    
    public long getId(){
        return this.id;
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
    
    public String getEmail(){
        return this.email;
    }
    
    public boolean isAdmin(){
        return this.isAdmin;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        return "id: "+this.id+"username: "+this.username+" name: "+this.name +
                            " lastname: "+this.lastname+" password: "+this.password+
                            " isAdmin? "+this.isAdmin;
    }
    
}
