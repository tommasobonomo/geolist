package it.unitn.aa1718.webprogramming.geolists.database;

import it.unitn.aa1718.webprogramming.geolists.database.models.Compose;
import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * DAO pattern for Compose relation
 * @author tommaso
 */
public class ComposeDAO {

    private Compose createCompose(ResultSet rs) throws SQLException {
        return new Compose(rs.getLong("LIST"), rs.getLong("ITEM"), rs.getInt("QUANTITY"));
    }
    
    public List<Compose> getItemsID(long listID) {
        String query = "SELECT * FROM Compose AS C WHERE C.list = " + listID;
        List<Compose> list = new ArrayList<>();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next()) {
                list.add(createCompose(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return list;
    }
    
    public List<Compose> getListID(long itemID) {
        String query = "SELECT * FROM Compose AS C WHERE C.item = " + itemID;
        List<Compose> list = new ArrayList<>();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next()) {
                list.add(createCompose(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return list;
    }

    public List<Compose> getAll() {
        String query = "SELECT * FROM Compose";
        List list = new ArrayList<>();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next()) { 
                list.add(createCompose(rs));
            }            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return list;
    }
    
    public boolean addItemToList(long itemID, long listID, int quantity) {
        String query = "INSERT INTO Compose(LIST,ITEM,QUANTITY) VALUES(?,?,?)";
        boolean success = true;
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            
            ps.setLong(1, listID);
            ps.setLong(2, itemID);
            ps.setInt(3, quantity);
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            success = false;
        }
        
        return success;
    }
    
    public Optional<Integer> getQuantityFromItemAndList(long itemID, long listID) {
        String query = "SELECT * FROM Compose WHERE LIST="+ listID +" AND ITEM="+ itemID ;
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next()) {
                return Optional.of(createCompose(rs).getQuantity());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    public Optional<Compose> getComposeObjectFromItemIdListId(long itemID, long listID) {
        String query = "SELECT * FROM Compose WHERE LIST="+ listID +" AND ITEM="+ itemID ;
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next()) {
                return Optional.of(createCompose(rs));
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    
    public void updateQuantity(Compose obj) {
        String query="UPDATE Compose "
                + "SET quantity=?"
                + "WHERE LIST="+ obj.getIdList() +" AND ITEM="+ obj.getIdItem() ;
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setInt(1, obj.getQuantity());
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
    }
    
    public boolean removeItemFromList(long itemID, long listID) {
        String query = "DELETE FROM Compose WHERE LIST=? AND ITEM=?";
        boolean success = true;
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            
            ps.setLong(1, listID);
            ps.setLong(2, itemID);
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            success = false;
        }
        
        return success;
    }
}
