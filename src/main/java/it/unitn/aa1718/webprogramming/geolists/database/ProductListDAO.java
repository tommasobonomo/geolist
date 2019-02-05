/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

import it.unitn.aa1718.webprogramming.geolists.database.models.ProductList;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.sql.Types.INTEGER;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO pattern for the List relation
 * @author tommaso
 */
public class ProductListDAO implements CrudDao<ProductList> {

    private ProductList createProductList(ResultSet rs) throws SQLException {
        Blob blob = rs.getBlob("image");
        InputStream image = null;
        if (blob != null) {
            image = blob.getBinaryStream();
        }
        return new ProductList(rs.getLong("id"),rs.getLong("userOwner"), rs.getLong("userAnonOwner"),
                rs.getLong("idCat"), rs.getString("name"),rs.getString("description"), image);
    }
    
    @Override
    public Optional<ProductList> get(long id) {
        String query = "SELECT * FROM List AS L WHERE L.id=" + id; 
        Optional<ProductList> res = Optional.empty();
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            if (rs.next()) {
                res = Optional.of(createProductList(rs));
            } else {
                res = Optional.empty();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return res;
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
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return list;
    }

    public Optional<byte[]> getBlobImageFromList(long id) {
        String query = "SELECT * FROM LIST AS L WHERE L.id = ?";
        Optional<byte[]> byteArrayOpt = Optional.empty();
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps =c.prepareStatement(query);
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Blob blob = rs.getBlob("logo");
                byteArrayOpt = Optional.of(blob.getBytes(1, (int) blob.length()));
            } else {
                System.out.println("no image to be found");
            }

        } catch (Exception e) {
             System.out.println(e);
        }
        
        return byteArrayOpt;
    }
        
    @Override
    public void create(ProductList obj) {
        String query= "INSERT INTO GEODB.LIST(USEROWNER,USERANONOWNER,IDCAT,\"NAME\",DESCRIPTION,IMAGE)\n" +
                        "VALUES (?,?,?,?,?,?)";
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            
            if (obj.getUserOwner() == 0) {
                ps.setNull(1, INTEGER);
            } else {
                ps.setLong(1, obj.getUserOwner());
            }
            if (obj.getUserAnonOwner() == 0) {
                ps.setNull(2, INTEGER);
            } else {
                ps.setLong(2, obj.getUserAnonOwner());
            }
            ps.setLong(3, obj.getIdCat());
            ps.setString(4, obj.getName());
            ps.setString(5, obj.getDescription());
            ps.setBlob(6, obj.getImage());
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public Optional<Long> createAndReturnID(ProductList obj) {
        String query= "INSERT INTO GEODB.LIST(USEROWNER,USERANONOWNER,IDCAT,\"NAME\",DESCRIPTION,IMAGE)\n" +
                        "VALUES (?,?,?,?,?,?)";
        Optional<Long> listID = Optional.empty();
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            
            if (obj.getUserOwner() == 0) {
                ps.setNull(1, INTEGER);
            } else {
                ps.setLong(1, obj.getUserOwner());
            }
            if (obj.getUserAnonOwner() == 0) {
                ps.setNull(2, INTEGER);
            } else {
                ps.setLong(2, obj.getUserAnonOwner());
            }
            ps.setLong(3, obj.getIdCat());
            ps.setString(4, obj.getName());
            ps.setString(5, obj.getDescription());
            ps.setBlob(6, obj.getImage());
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                listID = Optional.of(rs.getLong(1));
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listID;
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
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
        
    }
    
    @Override
    public void update(long id, ProductList obj) {
        String query="UPDATE List "
                + "SET userOwner=?, userAnonOwner=?, idCat=?, name=?, description=?, image=?"
                + "WHERE id=?";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            if (obj.getUserOwner() == 0) {
                ps.setNull(1, INTEGER);
            } else {
                ps.setLong(1, obj.getUserOwner());
            }
            if (obj.getUserAnonOwner() == 0) {
                ps.setNull(2, INTEGER);
            } else {
                ps.setLong(2, obj.getUserAnonOwner());
            }
            ps.setLong(3, obj.getIdCat());
            ps.setString(4, obj.getName());
            ps.setString(5, obj.getDescription());
            ps.setBlob(6, obj.getImage());
            ps.setLong(7,obj.getId());
            
            ps.executeUpdate();
            
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
     * @author giorgio
     * 
     * restituisce la lista di un utente anonimo, non una lista di liste
     * dato che un utente puo averne al massimo una
     * @param userAnonID
     * @return optional of a product list, Otpional.empty() se non ce ne sono
     */
    public Optional<ProductList> getListAnon(long userAnonID) {
        String query = "SELECT * FROM List AS L WHERE L.useranonowner = " + userAnonID;
        Optional<ProductList> res = Optional.empty();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            if (rs.next()) {
                res=Optional.of(createProductList(rs));
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return res;
    }
       
}
