/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

import it.unitn.aa1718.webprogramming.geolists.database.models.ProductList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO pattern for the List relation
 * @author tommaso
 */
public class ProductListDAO implements CrudDao<ProductList> {

    private ProductList createProductList(ResultSet rs) throws SQLException {
        return new ProductList(rs.getLong("id"),rs.getLong("userowner"),rs.getLong("useranonowner"), rs.getLong("idCat"),
            rs.getString("name"),rs.getString("description"), rs.getString("image"));        
    }
    
    @Override
    public Optional<ProductList> get(long id) {
        String query = "SELECT * FROM List AS L WHERE L.id=" + id; 
                
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            if (rs.next()) {
                return Optional.of(createProductList(rs));
            } else {
                return Optional.empty();
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return Optional.empty();
    }

    @Override
    public List<ProductList> getAll() {
        String query = "SELECT * FROM List";
        List list = new ArrayList<>();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next()) {
                list.add(createProductList(rs));
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
    public void create(ProductList obj) {
        String query= "INSERT INTO GEODB.LIST(USEROWNER,USERANONOWNER,IDCAT,\"NAME\",DESCRIPTION,IMAGE)\n" +
                        "VALUES (?,?,?,?,?)";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            
            ps.setLong(1, obj.getUserOwner());
            ps.setLong(2, obj.getUserAnonOwner());
            ps.setLong(3, obj.getIdCat());
            ps.setString(4, obj.getName());
            ps.setString(5, obj.getDescription());
            ps.setString(6, obj.getImage());
            
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * set the user owner anonymous of the list to null
     * @param id of the list
     */
    public void updateUserAnonOwnerToNull(long id) {
        String query="UPDATE List "
                + "SET userAnonOwner=?"
                + "WHERE id=?";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            
            ps.setString(1, null);
            ps.setLong(2,id);
            
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
        
    }
    
    @Override
    public void update(long id, ProductList obj) {
        String query="UPDATE List "
                + "SET userOwner=?,userAnonOwner=?, idCat=?, name=?, description=?, image=?"
                + "WHERE id=?";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setLong(1, obj.getUserOwner());
            ps.setLong(2, obj.getUserAnonOwner());
            ps.setLong(3, obj.getIdCat());
            ps.setString(4, obj.getName());
            ps.setString(5, obj.getDescription());
            ps.setString(6, obj.getImage());
            ps.setLong(7,obj.getId());
            
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
        
    }

    @Override
    public void delete(long id) {
        String query ="DELETE FROM List WHERE id=?";
        
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
    
    /**
     * restituisce tutte le liste di un utente loggato
     * @param userID
     */
    public List<ProductList> getListUser(long userID) {
        String query = "SELECT * FROM List AS L WHERE L.userowner = " + userID;
        List<ProductList> list = new ArrayList<>();
        ProductListDAO a = new ProductListDAO();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next()) {
                list.add(a.get(rs.getLong("id")).get());
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return list;
    }
    
    /**
     * restituisce la lista di un utente anonimo
     * @param userAnonID
     * @return 
     */
    public List<ProductList> getListAnon(long userAnonID) {
        String query = "SELECT * FROM List AS L WHERE L.useranonowner = " + userAnonID;
        List<ProductList> list = new ArrayList<>();
        ProductListDAO a = new ProductListDAO();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next()) {
                list.add(a.get(rs.getLong("id")).get());
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return list;
    }
    
    
}
