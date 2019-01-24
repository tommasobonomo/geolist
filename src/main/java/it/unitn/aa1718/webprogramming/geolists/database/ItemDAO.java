package it.unitn.aa1718.webprogramming.geolists.database;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of DAO pattern for Item relation
 * @author tommaso
 */
public class ItemDAO implements CrudDao<Item>{

    private Item createItem(ResultSet rs) throws SQLException {
        return new Item(rs.getLong("id"), rs.getLong("idCat"), rs.getString("name"), (InputStream) rs.getBlob("logo"), rs.getString("note"));
    }
    
    @Override
    public Optional<Item> get(long id) {
        
        String query = "SELECT * FROM Item AS I WHERE I.id = " + id;
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            if (rs.next()) {
                return Optional.of(createItem(rs));
            } else {
                return Optional.empty();
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return Optional.empty();
    }

    
    
    @Override
    public List<Item> getAll() {
        String query = "SELECT * FROM Item";
        List list = new ArrayList<>();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next()) {
                list.add(createItem(rs));
            }
            
            rs.close();
            s.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return list;
    }

    @Override
    public void create(Item obj) {
        String query= "INSERT INTO GEODB.ITEM(IDCAT,\"NAME\",LOGO,NOTE)\n" +
                        "VALUES (?,?,?,?)";
        String message = null;
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            
            if (obj.getLogo() != null) {
                 ps.setBlob(3, obj.getLogo());
            }
            
            ps.setLong(1, obj.getIdCat());
            ps.setString(2, obj.getName());
            ps.setString(4, obj.getNote());
            
            int row = ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(long id, Item obj) {
        String query="UPDATE Item "
                + "SET idcat=?, name=?, logo=?, note=?"
                + "WHERE id=?";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setLong(1, obj.getIdCat());
            ps.setString(2, obj.getName());
            ps.setBlob(3, obj.getLogo());
            ps.setString(4, obj.getNote());
            ps.setLong(6, id);
            
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
    }

    @Override
    public void delete(long id) {
        String query ="DELETE FROM Item WHERE id=?";
        
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
