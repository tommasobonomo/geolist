/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

import it.unitn.aa1718.webprogramming.geolists.database.models.ProductList;
import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import it.unitn.aa1718.webprogramming.geolists.database.models.UserAnonimous;
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
 * @author giorgiosgl
 */
public class UserAnonimousDAO implements CrudDao<UserAnonimous>{
    
    private UserAnonimous createUserAnonimous(ResultSet rs) throws SQLException {
        return new UserAnonimous(rs.getLong(1),rs.getString("cookie"));
    }
    
    /**
     * transform a anonymous user in a user with his list
     * @param ua utente anonimo da trasformare
     * @param u utente con dati da creare nel database
     * @return false means there is an error, true means there is no error but not that
     * work
     */
    public boolean becomeUserRegister(UserAnonimous ua,User u){
        
        try{
            // creo il nuovo utente nel database e gli setto il cookie dell'utente anonimo
            UserDAO userDb = new UserDAO();
            u.setCookie(ua.getCookie());
            userDb.create(u);
        
            //prendo il nuovo utente dal database (per avere l'id)
            User u_new= null;
            u_new = userDb.getUser(u.getCookie()).get();
        
            //collego le liste dell'utente anonimo a quello delle liste del nuovo utente
            ProductListDAO listDb = new ProductListDAO();
            ProductList p = listDb.getListAnon(ua.getId()).get(0);  // prendo la prima e unica lista
                                                                //dell'utente anonimo
            p.setUserOwner(u_new.getId());
            System.out.println(p);
            listDb.update(p.getId(), p); // aggiorno la lista
            listDb.updateUserAnonOwnerToNull(p.getId()); // setto a null l'anonimoowner nella tabella lista
            
            //rimuovo l'anonimo dalla tabella useranonymous
            UserAnonimousDAO anoDb = new UserAnonimousDAO();
            anoDb.delete(ua.getId());
            
            return true;
        }catch(Exception e){
            e.printStackTrace();
            
            return false;
        }
    }
    
    @Override
    public Optional<UserAnonimous> get(long id) {
        String query= "SELECT * FROM UsersAnonimous as U WHERE U.id="+id;
        UserAnonimous u=null;
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs=s.executeQuery(query);
        
            while(rs.next()){
                u=createUserAnonimous(rs);
            }
        
            rs.close();
            s.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }        
        return Optional.of(u);
    }
    
    @Override
    public List<UserAnonimous> getAll() {
        String query= "SELECT * FROM UsersAnonimous";
        List<UserAnonimous> resList = new ArrayList<>();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs=s.executeQuery(query);
        
            while(rs.next()){
                resList.add(createUserAnonimous(rs));
            }
        
            rs.close();
            s.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }        
        return resList; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(UserAnonimous obj) {
        String query= "INSERT INTO GEODB.USERSANONIMOUS(COOKIE)\n" +
                        "VALUES (?)";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setString(1, obj.getCookie());
            
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(long id, UserAnonimous obj) {
        String query="UPDATE UsersAnonimous "
                + "SET cookie=?"
                + "WHERE id=?";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setString(1, obj.getCookie());
            ps.setLong(2, id);
            
            ps.executeUpdate();
            ps.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
    }

    @Override
    public void delete(long id) {
        String query ="DELETE FROM UsersAnonimous WHERE id=?";
        
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
