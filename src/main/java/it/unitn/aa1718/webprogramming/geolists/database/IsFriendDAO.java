/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

import it.unitn.aa1718.webprogramming.geolists.database.models.IsFriend;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author giorgiosgl
 */
public class IsFriendDAO{

    
    
    /**
     * get user who have isFriend to that list
     * ricordo due utenti sono amici se esistono due righe con (id1,id2) e (id2,id1)
     * @param userID
     * @return list of user
     */
    public List<User> getFriends(long userID) {
        String query1 = " (SELECT usr2 as id FROM IsFriend AS F WHERE F.usr1 =" + userID +")"
                + " INTERSECT "
                + " (SELECT usr1 as id FROM IsFriend AS F WHERE F.usr2 =" + userID +")";
        
        List<User> list = new ArrayList<>();
        UserDAO a = new UserDAO();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query1); //usr2
            
            while (rs.next()) {
                list.add(a.get(rs.getLong("id")).get());
            }
            
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return list;
    }
    
    /**
     * 
     * @param userId
     * @param possibleFriendId
     * @return true if they are friend, false else
     */
    public boolean isFriend(long userId, long possibleFriendId) {
        IsFriendDAO isFriendDAO = new IsFriendDAO();
        List<User> friends = isFriendDAO.getFriends(userId);
        
        //controllo siano realmente amici
        boolean isReallyFriends=false;
        for(User f : friends){
            if(f.getId()==possibleFriendId)
                isReallyFriends=true;
        }
        
        return isReallyFriends;
    }
    
    /**
     * get friend request pending
     * @param listID
     * @return list of user
     */
    public List<User> getRequestFriends(long userID) {
        String query1 = "(SELECT usr1 as id FROM IsFriend AS F WHERE F.usr2 = "+userID  +")"
                + " EXCEPT "
                + " (SELECT usr2 as id FROM IsFriend AS F WHERE F.usr1 = "+userID
                + " INTERSECT "
                + " SELECT usr1 as id FROM IsFriend AS F WHERE F.usr2 = "+userID +")";
        
        List<User> list = new ArrayList<>();
        UserDAO a = new UserDAO();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query1); //usr2
            
            while (rs.next()) {
                list.add(a.get(rs.getLong("id")).get());
            }
            
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return list;
    }

    /**
     * add isfreind on db
     * @param obj
     */
    public void create(IsFriend obj) {
        String query= "INSERT INTO GEODB.ISFRIEND(USR1,USR2)\n" +
                        "VALUES (?,?)";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setLong(1, obj.getUserID1());
            ps.setLong(2, obj.getUserID2());
            
            ps.executeUpdate();
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
    }

    /**
     * delete the object a
     * @param a
     */
    public void delete(IsFriend obj) {
        String query ="DELETE FROM IsFriend WHERE usr1=? and usr2=?";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setLong(1, obj.getUserID1());
            ps.setLong(2, obj.getUserID2());
            
            ps.executeUpdate();
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
    }
}
