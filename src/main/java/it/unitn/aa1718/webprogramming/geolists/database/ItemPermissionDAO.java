/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

import it.unitn.aa1718.webprogramming.geolists.database.models.CatItem;
import it.unitn.aa1718.webprogramming.geolists.database.models.CatList;
import it.unitn.aa1718.webprogramming.geolists.database.models.ItemPermission;
import it.unitn.aa1718.webprogramming.geolists.database.models.ProductList;
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
public class ItemPermissionDAO {

    private ItemPermission createItemPermission(ResultSet rs) throws SQLException {
        return new ItemPermission(rs.getLong("categoryListId"), rs.getLong("categoryItemId"));
    }

    /**
     * get item category under the list id category
     *
     * @param categoryListId
     * @return list of user
     */
    public List<CatItem> getItemCategories(long categoryListId) {
        String query = "SELECT * FROM ItemPermission AS A WHERE a.categoryListId = " + categoryListId;
        List<CatItem> list = new ArrayList<>();
        CatItemDAO categoryItemDAO = new CatItemDAO();

        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
                list.add(categoryItemDAO.get(rs.getLong("categoryItemId")).get());
            }
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    /**
     * if categoryItem and categoryList are in relation
     *
     * @param categoryItemId
     * @param categoryListId
     * @return boolean
     */
    public boolean catogoryItemIsUnderCategoryList(long categoryListId, long categoryItemId) {
        String query = "SELECT * FROM ItemPermission AS A WHERE a.categoryListId = " + categoryListId
                + "and a.categoryItemId = " + categoryItemId;
        boolean res = false;

        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);

            if (rs.next()) {
                res = true;
            } else {
                res = false;
            }
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return res;
    }

    /**
     * get categorylist from categoryitemid
     *
     * @param categoryItemId
     * @return
     */
    public List<CatList> getListCategories(long categoryItemId) {
        String query = "SELECT * FROM ItemPermission AS A WHERE A.categoryItemId = " + categoryItemId;
        List<CatList> list = new ArrayList<>();
        CatProductListDAO catListDAO = new CatProductListDAO();

        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
                list.add(catListDAO.get(rs.getLong("categoryListId")).get());
            }
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    /**
     * add ItemPermission on db
     *
     * @param obj
     */
    public void create(ItemPermission obj) {
        String query = "INSERT INTO GEODB.ItemPermission(categoryListId,categoryItemId)\n"
                + "VALUES (?,?)";

        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);

            ps.setLong(1, obj.getCategoryListId());
            ps.setLong(2, obj.getCategoryItemId());

            ps.executeUpdate();
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * delete an ItemPermission object
     *
     * @param obj
     */
    public void delete(ItemPermission obj) {
        String query = "DELETE FROM ItemPermission WHERE CategoryListId=? and categoryItemId=?";

        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);

            ps.setLong(1, obj.getCategoryListId());
            ps.setLong(2, obj.getCategoryItemId());

            ps.executeUpdate();
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * List of user in which the item can be add
     * @param userId
     * @param itemId
     * @return return the id of list
     */
    public List<Long> getPossibleMyListToAddItem(long userId, long itemId) {
        String superQuery = "SELECT listsOfUser.id\n"
                + "FROM    (SELECT l.id,l.idcat FROM GEODB.list AS l JOIN GEODB.access AS a ON l.id=a.idlist WHERE a.iduser="+ userId +") as listsOfUser\n"
                + "        JOIN (SELECT ip.categorylistid\n"
                + "              FROM (SELECT i.idcat FROM GEODB.item as i where i.id="+ itemId +") AS categoryOfItem \n"
                + "                    JOIN ItemPermission AS ip ON categoryOfItem.idcat=ip.categoryitemid) as possibleCategoryList\n"
                + "        ON listsOfUser.idcat = possibleCategoryList.categorylistid";
        
         
        List <Long> list = new ArrayList<>();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(superQuery);

            while (rs.next()) {
                list.add(rs.getLong("id"));
            }
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

}
