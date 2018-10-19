/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data access object for the table user in the database
 * @author giorgiosgl
 */
public class UserDAO implements CrudDao<User> {
    
    private User createUser(ResultSet rs) throws SQLException {
        return new User(rs.getLong("id"),rs.getString("cookie"),rs.getString("username"), rs.getString("name"), rs.getString("lastname")
                , rs.getString("email"), rs.getString("password"),rs.getString("image"), rs.getBoolean("admin"));
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
    
    @Override
    public void create(User obj) {
        String query= "INSERT INTO GEODB.USERS(ID,COOKIE, USERNAME,\"NAME\",LASTNAME,EMAIL,IMAGE, PASSWORD, \"ADMIN\")\n" +
                        "VALUES (?,?,?,?,?,?,?,?,?)";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            
            ps.setLong(1, obj.getId());
            ps.setString(2, "\'" + obj.getCookie()   + "\'");
            ps.setString(3, "\'" + obj.getUsername() + "\'");
            ps.setString(4, "\'" + obj.getName()     + "\'");
            ps.setString(5, "\'" + obj.getLastname() + "\'");
            ps.setString(6, "\'" + obj.getEmail()    + "\'");
            ps.setString(7, "\'" + obj.getImage()    + "\'");
            ps.setString(8, "\'" + obj.getPassword() + "\'");
            ps.setBoolean(9, obj.isAdmin());
            
            ps.executeUpdate(query);
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
    
    }
    
    @Override
    public void update(long id, User obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(User obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
