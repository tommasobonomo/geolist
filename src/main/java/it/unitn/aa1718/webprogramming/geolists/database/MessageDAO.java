/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

import it.unitn.aa1718.webprogramming.geolists.database.models.Message;
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
 * @author root
 */
public class MessageDAO implements CrudDao<Message>{
    
    private Message createMessage(ResultSet rs) throws SQLException {
        return new Message(rs.getLong("id"), rs.getLong("idList"),
                rs.getLong("idUser"), rs.getTimestamp("sendTime"),rs.getString("text"));
    }
    
    @Override
    public Optional<Message> get(long id) {
        String query = "SELECT * FROM Message AS M WHERE M.id = " + id;
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            if (rs.next()) {
                return Optional.of(createMessage(rs));
            } else {
                return Optional.empty();
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return Optional.empty();
    }

    @Override
    public List<Message> getAll() {
        String query = "SELECT * FROM Message";
        List list = new ArrayList<>();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next()) {
                list.add(createMessage(rs));
            }
            
            rs.close();
            s.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return list;
    }
    
    public List<Message> getMessageFromList(long idList) {
        String query = "SELECT * FROM Message WHERE idList="+idList;
        List listOfMessage = new ArrayList<>();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next()) {
                listOfMessage.add(createMessage(rs));
            }
            
            rs.close();
            s.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return listOfMessage;
    }

    @Override
    public void create(Message obj) {
        String query= "INSERT INTO GEODB.MESSAGE(IDUSER,IDList,TEXT,SENDTIME)\n" +
                        "VALUES (?,?,?,?)";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setLong(1, obj.getIdUser());
            ps.setLong(2, obj.getIdList());
            ps.setString(3, obj.getText());
            ps.setTimestamp(4, obj.getSendTime());
            
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    

    @Override
    public void update(long id, Message obj) {
        String query="UPDATE Message "
                + "SET idUser=?, idList=?, text=?, sendTime=?"
                + "WHERE id=?";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            
            ps.setLong(1, obj.getIdUser());
            ps.setLong(2, obj.getIdList());
            ps.setString(3, obj.getText());
            ps.setTimestamp(4, obj.getSendTime());
            
            ps.setLong(5, id);
            
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }  
    }

    @Override
    public void delete(long id) {
        String query ="DELETE FROM Message WHERE id=?";
        
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
