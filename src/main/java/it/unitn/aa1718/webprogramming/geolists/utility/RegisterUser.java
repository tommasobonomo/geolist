/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.utility;

/**
 *
 * @author Lorenzo
 */
public class RegisterUser {
    private String UserName;
    private String FirstName;
    private String LastName;
    private String Password;
    private String Email;
    private String Token;
    
    public RegisterUser(String username, String firstname, String lastname, String password, String email, String token){
        this.UserName = username;
        this.Email = email;
        this.FirstName = firstname;
        this.LastName = lastname;
        this.Password = password;
        this.Token = token;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }
    
    
}
