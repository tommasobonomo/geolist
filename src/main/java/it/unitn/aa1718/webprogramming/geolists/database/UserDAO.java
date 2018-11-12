/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.utility.HashGenerator;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data access object for the table user in the database
 * @author giorgiosgl
 */
public class UserDAO implements CrudDao<User> {
    
    private User createUser(ResultSet rs) throws SQLException {
        return new User(rs.getLong("id"),rs.getString("cookie"),rs.getString("username"), rs.getString("name"), rs.getString("lastname")
                , rs.getString("email"), rs.getString("password"),rs.getString("image"), rs.getString("token"), rs.getBoolean("active"), rs.getBoolean("admin"));
    }

    /**
     * Get a user from id
     * @param id
     * @return
     */
    @Override
    public Optional<User> get(long id) {
        String query= "SELECT * FROM Users as U WHERE U.id="+id;
        User u=null;
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs=s.executeQuery(query);
        
            while(rs.next()){
                u=createUser(rs);
            }
        
            rs.close();
            s.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }        
        return Optional.of(u);
    }
    
  
     /**
     * Get a user from username
     * @param username
     * @return
     */
    
    public Optional<User> get(String username) {
        String query= "SELECT * FROM Users as U WHERE U.username=?";
        Optional<User> u=Optional.empty();
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            
            ps.setString(1, username);
            
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                u=Optional.of(createUser(rs));
            }
            
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return u;
    }
    
    /**
     * Get a user from email
     * @param email
     * @return
     */
    public Optional<User> getFromEmail(String email) {
        String query= "SELECT * FROM Users as U WHERE U.email=?";
        Optional<User> u=Optional.empty();
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            
            ps.setString(1, email);
            
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                u=Optional.of(createUser(rs));
            }
            
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return u;
    }
    
    
   

    public Optional<User> getFromEmailAndToken(String email, String token) {
        String query= " SELECT * FROM GEODB.USERS WHERE email=? AND token=? and active=false ";
        Optional<User> u=Optional.empty();
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            
            ps.setString(1,email);
            ps.setString(2,token);
            
            
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                u=Optional.of(createUser(rs));
            }
            
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return u;
    }
    
    /** 
     * Return a list of all user in the database
     * @return List of User
     * @throws SQLException
     */
    @Override
    public List<User> getAll(){
        String query= "SELECT * FROM Users";
        List<User> resList = new ArrayList<>();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs=s.executeQuery(query);
        
            while(rs.next()){
                resList.add(createUser(rs));
            }
        
            rs.close();
            s.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }        
        return resList;
    }
    
    private String hash(String pass){
        try {
            return HashGenerator.Hash(pass); //this means pasta
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * insert in the db the User obj
     * @param obj that are User
     */
    @Override
    public void create(User obj) {
        String query= "INSERT INTO GEODB.USERS(COOKIE, USERNAME,\"NAME\",LASTNAME,EMAIL,IMAGE, PASSWORD, TOKEN, ACTIVE, \"ADMIN\")\n" +
                        "VALUES (?,?,?,?,?,?,?,?,?,?)";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setString(1, obj.getCookie());
            ps.setString(2, obj.getUsername());
            ps.setString(3, obj.getName());
            ps.setString(4, obj.getLastname());
            ps.setString(5, obj.getEmail());
            ps.setString(6, obj.getImage());
            ps.setString(7, hash(obj.getPassword()));
            ps.setString(8, obj.getToken());
            ps.setBoolean(9, obj.isActive());
            ps.setBoolean(10, obj.isAdmin());
            
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
    
    }
    
    @Override
    public void update(long id, User obj) {
        String query="UPDATE Users "
                + "SET cookie=?, username=?, name=?, lastname=?, email=?, image=?, token=?, active=?, admin=?"
                + "WHERE id=?";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setString(1, obj.getCookie());
            ps.setString(2, obj.getUsername());
            ps.setString(3, obj.getName());
            ps.setString(4, obj.getLastname());
            ps.setString(5, obj.getEmail());
            ps.setString(6, obj.getImage());
            ps.setString(7, obj.getToken());
            ps.setBoolean(8, obj.isActive());
            ps.setBoolean(9, obj.isAdmin());
            ps.setLong(10, id);
            
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
        
    }

    /**
     * delete the user with that value
     * @param obj
     */
    @Override
    public void delete(long id) {
        String query ="DELETE FROM Users WHERE id=?";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setLong(1, id);
            
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }  
    } 
    
}
