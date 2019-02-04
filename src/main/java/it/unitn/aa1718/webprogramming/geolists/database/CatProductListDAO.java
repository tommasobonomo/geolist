/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

import it.unitn.aa1718.webprogramming.geolists.database.models.CatList;
import java.io.InputStream;
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
 * DAO pattern for the CatList relation
 * @author tommaso
 */
public class CatProductListDAO implements CrudDao<CatList> {
    
    private CatList createCatList(ResultSet rs) throws SQLException {
        Blob blob = rs.getBlob("image");
        InputStream image = null;
        if (blob != null) {
            image = blob.getBinaryStream();
        }
        return new CatList(rs.getLong("id"), rs.getString("name"), 
                rs.getString("description"), image);
    }
    
    @Override
    public Optional<CatList> get(long id) {
        String query = "SELECT * FROM CList AS CL WHERE CL.id = " + id;
        Optional<CatList> res = Optional.empty();
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            if (rs.next()) {
                res = Optional.of(createCatList(rs));
            } else {
                res = Optional.empty();
            }
            
            rs.close();
            Database.closeConnection(c);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }

    @Override
    public List<CatList> getAll() {
        String query = "SELECT * FROM CList";
        List list = new ArrayList<>();
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while(rs.next()) {
                list.add(createCatList(rs));
            }
            
            rs.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public Optional<byte[]> getBlobImageFromCatList(long id) {
       
        String query = "SELECT * FROM CList AS CL WHERE I.id = ?";
        Optional<byte[]> byteArrayOpt = Optional.empty();
        try {
            
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Blob blob = rs.getBlob("image");
                byteArrayOpt = Optional.of(blob.getBytes(1, (int) blob.length()));
            } else {
                System.out.println("no image to be found");
            }
            
            rs.close();
            Database.closeConnection(c);
        
        } catch (Exception e) {
             System.out.println(e);
        }
        
        return byteArrayOpt;
    }
    
    @Override
    public void create(CatList obj) {
        String query = "INSERT INTO GEODB.CLIST(NAME,DESCRIPTION,IMAGE) "
                + "VALUES (?,?,?)";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getDescription());
            ps.setBlob(3, obj.getImage());
            
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(long id, CatList obj) {
         String query="UPDATE CList "
                + "SET name=?, description=?, image=?"
                + "WHERE id=?";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getDescription());
            ps.setBlob(3, obj.getImage());
            ps.setLong(4,obj.getIdCategory());
            
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
    }

    @Override
    public void delete(long id) {
        String query ="DELETE FROM CList WHERE id=?";
        
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
