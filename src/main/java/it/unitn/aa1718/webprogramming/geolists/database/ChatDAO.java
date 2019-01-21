/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

import it.unitn.aa1718.webprogramming.geolists.database.models.CatItem;
import it.unitn.aa1718.webprogramming.geolists.database.models.Chat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Giorgio
 */
public class ChatDAO implements CrudDao<Chat>{
    
    private Chat createChat(ResultSet rs) throws SQLException {
        return new Chat(rs.getLong("id"), rs.getString("name"),
                rs.getString("description"), rs.getString("picture") );
    }
    
    @Override
    public Optional<Chat> get(long id) {
        String query = "SELECT * FROM Chat AS C WHERE C.id = " + id;
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            if (rs.next()) {
                return Optional.of(createChat(rs));
            } else {
                return Optional.empty();
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return Optional.empty();
    }

    @Override
    public List<Chat> getAll() {
        String query = "SELECT * FROM Chat";
        List list = new ArrayList<>();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next()) {
                list.add(createChat(rs));
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
    public void create(Chat obj) {
        String query= "INSERT INTO GEODB.CHAT(\"NAME\",PICTURE,DESCRIPTION)\n" +
                        "VALUES (?,?,?)";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setString(1, obj.getName());
            ps.setString(2, obj.getPicture());
            ps.setString(3, obj.getDescription());
            
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public Optional<Long> createAndReturnId(Chat obj) {
        String query= "INSERT INTO GEODB.CHAT(\"NAME\",PICTURE,DESCRIPTION)\n" +
                        "VALUES (?,?,?)";
        
        Optional<Long> result = Optional.empty();
        try {
            
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            

            ps.setString(1, obj.getName());
            ps.setString(2, obj.getPicture());
            ps.setString(3, obj.getDescription());
            
            ps.executeUpdate();
            
            ResultSet rs=ps.getGeneratedKeys();
            
            if(rs.next()){
		result=Optional.of(rs.getLong(1));
            }
            
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return result;
    }

    @Override
    public void update(long id, Chat obj) {
        String query="UPDATE Chat "
                + "SET name=?, picture=?, description=?"
                + "WHERE id=?";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getPicture());
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
        String query ="DELETE FROM Chat WHERE id=?";
        
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
