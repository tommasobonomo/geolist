/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Lorenzo
 */
class DBInsert {
    
    
    private static final String DB_URL = "jdbc:derby://localhost:1527/Supermarket";
    private String Name;
    private String Surname;
    private String Email;
    
    public String getName(){
        return Name;
    }
    
    public void setName(String name){
        this.Name=name;
    }

    public String getSurname(){
        return Surname;
    }
    
    public void setSurname(String surname){
        this.Surname=surname;
    }
    
    public String getEmail(){
        return Email;
    }
    
    
    public void setEmail(String email){
        this.Email=email;
    }
    
    public int store() throws ClassNotFoundException, SQLException{
        
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        
        Connection Connect = DriverManager.getConnection(DB_URL, "DoppioDiPig", "DoppioDiPig");
        
        PreparedStatement ps = Connect.prepareStatement("insert into doppiodipig(name,surname,email) values(?,?,?)");
        
        ps.setString(1, Name);
        ps.setString(2, Surname);
        ps.setString(3, Email);
        
        int a=ps.executeUpdate();
        
        if(a==1){
            return a;
        }else{
            return a;
        }
             
    }
    
}
