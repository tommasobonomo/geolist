/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

import it.unitn.aa1718.webprogramming.geolists.database.models.Access;
import it.unitn.aa1718.webprogramming.geolists.database.models.ProductList;
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
public class AccessDAO{

    private Access createAccess(ResultSet rs) throws SQLException {
        return new Access(rs.getLong("iduser"), rs.getLong("idlist"));
    }
    
    /**
     * get user who have access to that list
     * @param listID
     * @return list of user
     */
    public List<User> getUser(long listID) {
        String query = "SELECT * FROM Access AS A WHERE a.idlist = " + listID;
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
     * get user who have access to that list
     * @param listID
     * @return list of user
     */
    public boolean canHaveAccess(long userID, long listID) {
        String query = "SELECT * FROM Access AS A WHERE a.idlist = " + listID 
                + "and a.iduser = " + userID;
        boolean res = false;
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            if(rs.next())
                res = true;
            else
                res = false;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return res;
    }

    /**
     * get list who have been access from that user
     * @param userID
     * @return list of shhoppinglist
     */
    public List<ProductList> getAllLists(long userID) {
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

    /**
     * add access on db
     * @param obj
     */
    public void create(Access obj) {
        String query= "INSERT INTO GEODB.ACCESS(IDLIST,IDUSER)\n" +
                        "VALUES (?,?)";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setLong(1, obj.getIdList());
            ps.setLong(2, obj.getIdUser());
            
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
    }

    /**
     * delete the object a
     * @param a
     */
    public void delete(Access obj) {
        String query ="DELETE FROM Access WHERE idList=? and idUser=?";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setLong(1, obj.getIdList());
            ps.setLong(2, obj.getIdUser());
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
    }
}
