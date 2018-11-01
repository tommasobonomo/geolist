/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

import it.unitn.aa1718.webprogramming.geolists.database.models.Access;
import it.unitn.aa1718.webprogramming.geolists.database.models.ProductList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author giorgiosgl
 */
public class AccessDAO implements CrudDao<Access>{

    private Access createAccess(ResultSet rs) throws SQLException {
        return new Access(rs.getLong("iduser"), rs.getLong("idlist"));
    }
    
    @Override
    public Optional<Access> get(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<ProductList> getList(long userID) {
        String query = "SELECT * FROM Access AS A WHERE A.iduser = " + userID;
        List<ProductList> list = new ArrayList<>();
        ProductListDAO a = new ProductListDAO();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next()) {
                list.add(a.get(rs.getLong("idlist")).get());
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return list;
    }

    @Override
    public void create(Access obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(long id, Access obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Access> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
