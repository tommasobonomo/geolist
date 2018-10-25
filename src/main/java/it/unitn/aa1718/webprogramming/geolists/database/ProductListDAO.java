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
        return new ProductList(rs.getLong("idList"),rs.getLong("userCreator"), rs.getLong("idCat"),
            rs.getString("name"),rs.getString("description"), rs.getString("image"));        
    }
    
    @Override
    public Optional<ProductList> get(long id) {
        String query = "SELECT * FROM List AS L WHERE L.idList=" + id; 
                
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
         String query= "INSERT INTO GEODB.LIST(USERCREATOR,IDCAT,\"NAME\",DESCRIPTION,IMAGE)\n" +
                        "VALUES (?,?,?,?,?)";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setLong(1, obj.getUserCreator());
            ps.setLong(2, obj.getIdCat());
            ps.setString(3, obj.getName());
            ps.setString(4, obj.getDescription());
            ps.setString(5, obj.getImage());
            
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
                + "SET userCreator=?, idCat=?, name=?, description=?, image=?"
                + "WHERE idList=?";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setLong(1, obj.getUserCreator());
            ps.setLong(2, obj.getIdCat());
            ps.setString(3, obj.getName());
            ps.setString(4, obj.getDescription());
            ps.setString(5, obj.getImage());
            ps.setLong(6,obj.getIdList());
            
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
        
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
