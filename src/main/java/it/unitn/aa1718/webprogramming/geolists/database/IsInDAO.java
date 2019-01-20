/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

import it.unitn.aa1718.webprogramming.geolists.database.models.Chat;
import it.unitn.aa1718.webprogramming.geolists.database.models.IsIn;
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
 * @author root
 */
public class IsInDAO {
    private IsIn createIsIn(ResultSet rs) throws SQLException {
        return new IsIn(rs.getLong("iduser"), rs.getLong("idchat"));
    }
    
    /**
     * get user who have access to that list
     * @param chatID
     * @return list of user
     */
    public List<User> getUsers(long chatID) {
        String query = "SELECT * FROM IsIn AS I WHERE I.idchat = " + chatID;
        List<User> list = new ArrayList<>();
        UserDAO a = new UserDAO();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next()) {
                list.add(a.get(rs.getLong("idUser")).get());
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return list;
    }

    /**
     * get chat who have been access from that user
     * @param userID
     * @return list of chat list
     */
    public List<Chat> getChats(long userID) {
        String query = "SELECT * FROM IsIn AS A WHERE A.iduser = " + userID;
        List<Chat> list = new ArrayList<>();
        ChatDAO a = new ChatDAO();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next()) {
                list.add(a.get(rs.getLong("idchat")).get());
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return list;
    }

    /**
     * add access on db
     * @param obj
     */
    public void create(IsIn obj) {
        String query= "INSERT INTO GEODB.ISIN(IDCHAT,IDUSER)\n" +
                        "VALUES (?,?)";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setLong(1, obj.getIdChat());
            ps.setLong(2, obj.getIdUser());
            
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
    }

    /**
     * delete the object a
     * @param a
     */
    public void delete(IsIn obj) {
        String query ="DELETE FROM IsIn WHERE idChat=? and idUser=?";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setLong(1, obj.getIdChat());
            ps.setLong(2, obj.getIdUser());
            
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
    }
}