package it.unitn.aa1718.webprogramming.geolists.database;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of DAO pattern for Item relation
 *
 * @author tommaso
 */
public class ItemDAO implements CrudDao<Item> {

    private Item createItem(ResultSet rs) throws SQLException {
        Blob blob = rs.getBlob("logo");
        InputStream is = null;
        if (blob != null) {
            is = blob.getBinaryStream();
        }
        return new Item(rs.getLong("id"), rs.getLong("idCat"), rs.getString("name"), is, rs.getString("note"), rs.getLong("idOwner"), rs.getBoolean("isTemplate"));
    }

    @Override
    public Optional<Item> get(long id) {
        String query = "SELECT * FROM Item AS I WHERE I.id = " + id;

        Optional<Item> res = Optional.empty();

        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);

            if (rs.next()) {
                res = Optional.of(createItem(rs));
            } else {
                res = Optional.empty();
            }

            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return res;
    }

    public Optional<Item> get(String name) {
        String query = "SELECT * FROM Item as I WHERE I.name=?";
        Optional<Item> u = Optional.empty();

        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u = Optional.of(createItem(rs));
            }
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return u;
    }

    public List<Item> getFromPattern(String pattern, int start, int total, Optional<User> userOpt) {
        String query;
        if (userOpt.isPresent()) {
            query = "SELECT * FROM Item AS I WHERE "
                    + "(I.isTemplate = TRUE OR I.idOwner = " + userOpt.get().getId()
                    + ") AND LOWER(I.name) LIKE '%" + pattern + "%' "
                    + "OFFSET " + start + " ROWS FETCH NEXT " + total + " ROWS ONLY";
        } else {
            query = "SELECT * FROM Item AS I WHERE "
                    + "I.isTemplate = TRUE "
                    + "AND LOWER(I.name) LIKE '%" + pattern + "%' "
                    + "OFFSET " + start + " ROWS FETCH NEXT " + total + " ROWS ONLY";
        }
        List list = new ArrayList<>();

        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
                list.add(createItem(rs));
            }

            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public int getNResultsFromPattern(String pattern, Optional<User> userOpt) {
        String query;
        if (userOpt.isPresent()) {
            query = "SELECT count(I.name) FROM Item AS I WHERE "
                    + "(I.isTemplate = TRUE OR I.idOwner = " + userOpt.get().getId()
                    + ") AND LOWER(I.name) LIKE '%" + pattern + "%'";
        } else {
            query = "SELECT count(I.name) FROM Item AS I WHERE "
                    + "I.isTemplate = TRUE "
                    + "AND LOWER(I.name) LIKE '%" + pattern + "%'";
        }
        int n = 0;

        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
                n = rs.getInt(1);
            }

            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return n;
    }

    public List<Item> getFromPatternOrderedByAlfabetico(String pattern, int start, int total, Optional<User> userOpt) {
        String query;
        if (userOpt.isPresent()) {
            query = "SELECT * FROM Item AS I WHERE LOWER(I.name) LIKE '%"
                    + pattern + "%' AND (I.isTemplate = TRUE OR I.idOwner = " + userOpt.get().getId()
                    + ") ORDER BY I.name "
                    + "OFFSET " + start + " ROWS FETCH NEXT " + total + " ROWS ONLY";
        } else {
            query = "SELECT * FROM Item AS I WHERE LOWER(I.name) LIKE '%"
                    + pattern + "%' AND I.isTemplate = TRUE "
                    + "ORDER BY I.name "
                    + "OFFSET " + start + " ROWS FETCH NEXT " + total + " ROWS ONLY";
        }

        List list = new ArrayList<>();

        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
                list.add(createItem(rs));
            }

            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public List<Item> getFromPatternOrderedByCategory(String pattern, int start, int total, Optional<User> userOpt) {
        String query;
        if (userOpt.isPresent()) {
            query = "SELECT * FROM Item AS I WHERE LOWER(I.name) LIKE '%"
                    + pattern + "%' AND (I.isTemplate = TRUE OR I.idOwner = " + userOpt.get().getId()
                    + ") ORDER BY I.idCat"
                    + " OFFSET " + start + " ROWS FETCH NEXT " + total + " ROWS ONLY";
        } else {
            query = "SELECT * FROM Item AS I WHERE LOWER(I.name) LIKE '%"
                    + pattern + "%' AND I.isTemplate = TRUE "
                    + "ORDER BY I.idCat "
                    + "OFFSET " + start + " ROWS FETCH NEXT " + total + " ROWS ONLY";
        }
        List list = new ArrayList<>();

        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
                list.add(createItem(rs));
            }

            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public List<Item> getFromPatternAndCategory(String pattern, Integer category, int start, int total, Optional<User> userOpt) {
        String query;
        if (userOpt.isPresent()) {
            query = "SELECT * FROM Item AS I WHERE "
                    + "(I.isTemplate = TRUE OR I.idOwner = " + userOpt.get().getId()
                    + ") AND LOWER(I.name) LIKE '%" + pattern + "%' AND I.idCat = " + category + " "
                    + "OFFSET " + start + " ROWS FETCH NEXT " + total + " ROWS ONLY";
        } else {
            query = "SELECT * FROM Item AS I WHERE "
                    + "I.isTemplate = TRUE "
                    + "AND LOWER(I.name) LIKE '%" + pattern + "%' AND I.idCat = " + category + " "
                    + "OFFSET " + start + " ROWS FETCH NEXT " + total + " ROWS ONLY";
        }
        List list = new ArrayList<>();

        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            //ps.setInt(1, category);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(createItem(rs));
            }
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public int getNResultsFromPatternAndCategory(String pattern, Integer category, Optional<User> userOpt) {
        String query;
        if (userOpt.isPresent()) {
            query = "SELECT count(I.name) FROM Item AS I WHERE "
                    + "(I.isTemplate = TRUE OR I.idOwner = " + userOpt.get().getId()
                    + ") AND I.idcat = ? AND LOWER(I.name) LIKE '%" + pattern + "%' ";
        } else {
            query = "SELECT count(I.name) FROM Item AS I WHERE "
                    + "I.isTemplate = TRUE "
                    + "AND I.idcat = ? AND LOWER(I.name) LIKE '%" + pattern + "%' ";
        }
        int n = 0;

        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, category);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                n = rs.getInt(1);
            }
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return n;
    }

    public List<Item> getFromPatternAndCategoryOrderedByAlfabetico(String pattern, Integer category, int start, int total, Optional<User> userOpt) {
        String query;
        if (userOpt.isPresent()) {
            query = "SELECT * FROM Item AS I WHERE "
                    + "(I.isTemplate = TRUE OR I.idOwner = " + userOpt.get().getId()
                    + ") AND LOWER(I.name) LIKE '%" + pattern + "%' AND I.idCat = " + category + " "
                    + "Order by I.name "
                    + "OFFSET " + start + " ROWS FETCH NEXT " + total + " ROWS ONLY";
        } else {
            query = "SELECT * FROM Item AS I WHERE "
                    + "I.isTemplate = TRUE "
                    + "AND LOWER(I.name) LIKE '%" + pattern + "%' AND I.idCat = " + category + " "
                    + "Order by I.name "
                    + "OFFSET " + start + " ROWS FETCH NEXT " + total + " ROWS ONLY";
        }
        List list = new ArrayList<>();

        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            //ps.setInt(1, category);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(createItem(rs));
            }
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
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
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public List<Item> getAllByIdCat(Long idCat, Optional<User> userOpt) {
        String query;
        if (userOpt.isPresent()) {
            query = "SELECT * FROM Item AS I "
                    + "WHERE I.idcat=" + idCat + " AND "
                    + "(I.isTemplate = TRUE OR I.idOwner = " + userOpt.get().getId() + ")";
        } else {
            query = "SELECT * FROM Item AS I "
                    + "WHERE I.idcat=" + idCat + " AND "
                    + "I.isTemplate = TRUE";
        }
        List list = new ArrayList<>();

        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
                list.add(createItem(rs));
            }
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public List<Item> getAllOrderedByName() {
        String query = "SELECT * FROM Item Order by name";
        List list = new ArrayList<>();

        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
                list.add(createItem(rs));
            }
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public List<Item> getAllForAdminOrderedByName() {
        String query = "SELECT * FROM Item Order by name";
        List list = new ArrayList<>();

        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
                list.add(createItem(rs));
            }
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public Optional<byte[]> getBlobImage(long id) {

        String query = "SELECT * FROM Item AS I WHERE I.id = ?";
        Optional<byte[]> byteArrayOpt = Optional.empty();
        Connection c = null;

        try {

            c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Blob blob = rs.getBlob("logo");
                byteArrayOpt = Optional.of(blob.getBytes(1, (int) blob.length()));
            } else {
                System.out.println("no image to be found");
            }
            c.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(ItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return byteArrayOpt;
    }

    @Override
    public void create(Item obj) {
        String query = "INSERT INTO GEODB.ITEM(IDCAT,\"NAME\",LOGO,NOTE,ISTEMPLATE,IDOWNER)\n"
                + "VALUES (?,?,?,?,?,?)";
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
            ps.setBoolean(5, obj.isIsTemplate());
            if (obj.getIdOwner() != 0) {
                ps.setLong(6, obj.getIdOwner());
            } else {
                ps.setNull(6, INTEGER);
            }

            int row = ps.executeUpdate();
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(long id, Item obj) {
        String query = "UPDATE Item "
                + "SET idcat=?, name=?, logo=?, note=?, istemplate=?, idowner=?"
                + "WHERE id=?";

        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);

            ps.setLong(1, obj.getIdCat());
            ps.setString(2, obj.getName());
            ps.setBlob(3, obj.getLogo());
            ps.setString(4, obj.getNote());
            ps.setBoolean(5, obj.isIsTemplate());
            if (obj.getIdOwner() != 0) {
                ps.setLong(6, obj.getIdOwner());
            } else {
                ps.setNull(6, INTEGER);
            }
            ps.setLong(7, id);

            ps.executeUpdate();

            c.commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateWithoutImage(long id, Item obj) {
        String query = "UPDATE Item "
                + "SET idcat=?, name=?, note=?, istemplate=?, idowner=?"
                + "WHERE id=?";

        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);

            ps.setLong(1, obj.getIdCat());
            ps.setString(2, obj.getName());
            ps.setString(3, obj.getNote());
            ps.setBoolean(4, obj.isIsTemplate());
            if (obj.getIdOwner() != 0) {
                ps.setLong(5, obj.getIdOwner());
            } else {
                ps.setNull(5, INTEGER);
            }
            ps.setLong(6, id);

            ps.executeUpdate();

            c.commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String query = "DELETE FROM Item WHERE id=?";

        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);

            ps.setLong(1, id);

            ps.executeUpdate();
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
