package it.unitn.aa1718.webprogramming.geolists.database;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import it.unitn.aa1718.webprogramming.geolists.database.models.CatItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of DAO pattern for CatItem relation
 * @author tommaso
 */
public class CatItemDAO implements CrudDao<CatItem>{

    private CatItem createCategoryItem(ResultSet rs) throws SQLException {
        return new CatItem(rs.getLong("id"), rs.getString("name"),
                rs.getString("image"), rs.getString("description") );
    }

    @Override
    public Optional<CatItem> get(long id) {
        String query = "SELECT * FROM CItem AS C WHERE C.id = " + id;
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            if (rs.next()) {
                return Optional.of(createCategoryItem(rs));
            } else {
                return Optional.empty();
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return Optional.empty();
    }

    @Override
    public List<CatItem> getAll() {
        String query = "SELECT * FROM CItem";
        List list = new ArrayList<>();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next()) {
                list.add(createCategoryItem(rs));
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
    public void create(CatItem obj) {
        String query= "INSERT INTO GEODB.CITEM(\"NAME\",IMAGE,DESCRIPTION)\n" +
                        "VALUES (?,?,?)";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setString(1, obj.getName());
            ps.setString(2, obj.getDescription());
            ps.setString(3, obj.getImage());
            
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(long id, CatItem obj) {
        String query="UPDATE CItem "
                + "SET name=?, image=?, description=?"
                + "WHERE id=?";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getImage());
            ps.setString(3, obj.getDescription());
            
            ps.setLong(4, id);
            
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
    }

    @Override
    public void delete(long id) {
        String query ="DELETE FROM CItem WHERE id=?";
        
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
