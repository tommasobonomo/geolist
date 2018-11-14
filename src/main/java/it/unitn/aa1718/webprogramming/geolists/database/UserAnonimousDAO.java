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
     * transform a anonymous user in a user with his list eventualmente se le ha
     * questo funziona sia con il register sia con il login
     * @param ua utente anonimo da trasformare
     * @param u utente con dati da creare nel database
     * 
     */
    public void becomeUserRegister(UserAnonimous ua,User u){
        
        UserDAO userDb = new UserDAO();
        User u_new;
        if(userDb.get(u.getUsername()).isPresent()){//è gia registrato
            u_new = userDb.get(u.getUsername()).get();
            //TODO inviare il cookie del db all'utente browser 
            
        }else{//si deve registrare
            u.setCookie(ua.getCookie());
            userDb.create(u);
            
            //prendo il nuovo utente dal database (per avere l'id)
            u_new = userDb.getUser(u.getCookie()).get();
        }
        
        ProductListDAO listDb = new ProductListDAO();
        Optional<ProductList> p = listDb.getListAnon(ua.getId());  // prendo la lista
                                                                //dell'utente anonimo
        if(p.isPresent()){   // se possiede una lista l'utente anonimo
            p.get().setUserOwner(u_new.getId());
            listDb.update(p.get().getId(), p.get()); // aggiorno la lista
            listDb.updateUserAnonOwnerToNull(p.get().getId()); // setto a null l'anonimoowner nella tabella lista
        }
        
        //rimuovo l'anonimo dalla tabella useranonymous
        UserAnonimousDAO anoDb = new UserAnonimousDAO();
        anoDb.delete(ua.getId());
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
    
    /**
     * Obtain a UserAnonimous from his cookie
     * @param cookie
     * @return
     */
    public Optional<UserAnonimous> getFromCookie(String cookie) {
        String query= "SELECT * FROM UsersAnonimous as U WHERE U.cookie=\'"+cookie+"\'";
        Optional<UserAnonimous> u=Optional.empty();
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs=s.executeQuery(query);
        
            while(rs.next()){
                u=Optional.of(createUserAnonimous(rs));
            }
        
            rs.close();
            s.close();
            Database.closeConnection(c);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }        
        return u;
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
