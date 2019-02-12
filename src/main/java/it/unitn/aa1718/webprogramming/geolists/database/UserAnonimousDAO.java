/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

import it.unitn.aa1718.webprogramming.geolists.database.models.Access;
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
import static java.sql.Types.INTEGER;

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
            
        }else{//si deve registrare
            u.setCookie(ua.getCookie());
            userDb.create(u);
            
            //prendo il nuovo utente dal database (per avere l'id)
            u_new = userDb.getUser(u.getCookie()).get();
        }
        
        
        
        ProductListDAO listDb = new ProductListDAO();
        Optional<ProductList> p = listDb.getListAnon(ua.getId());  // prendo la lista dell'utente anonimo
                                                                
        AccessDAO aDAO = new AccessDAO();
        
        if(p.isPresent()){   // se possiede una lista l'utente anonimo
            //aggiungo l'accesso
            Access a = new Access(u_new.getId(), p.get().getId(), true);
            aDAO.create(a);
            
            //setto il possessore all'utente esistente
            p.get().setUserOwner(u_new.getId());
            listDb.update(p.get().getId(), p.get()); // aggiorno la lista
            listDb.updateUserAnonOwnerToNull(p.get().getId()); // setto a null l'anonimoowner nella tabella lista
        }
        
        //rimuovo l'anonimo dalla tabella useranonymous
        UserAnonimousDAO anoDb = new UserAnonimousDAO();
        anoDb.delete(ua.getId());
    }
    
    public void fromAnonToRegister(UserAnonimous userAnon, User user) {
        UserDAO userDAO = new UserDAO();
        User userWithID;
        if (userDAO.get(user.getUsername()).isPresent()) {
            // Gia' registrato, prendo l'ID
            userWithID = userDAO.get(user.getUsername()).get();
        } else {
            // Utente da registrare
            user.setCookie(userAnon.getCookie());
            userDAO.create(user);
            
            // Prendo l'id appena creato
            userWithID = userDAO.getUser(user.getCookie()).get();
        }
        
        ///////////////////////////////
        // Modifiche da fare in LIST //
        ///////////////////////////////
        String queryID = "SELECT ID FROM LIST WHERE USERANONOWNER = ?";
        long listID = 0;
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(queryID);
            
            ps.setLong(1, userAnon.getId());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                listID = rs.getLong("ID");
            }
            c.commit();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        String queryList = "UPDATE LIST "
                + "SET USEROWNER = ?, USERANONOWNER = ?"
                + "WHERE ID = ?";

        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(queryList);
            
            ps.setLong(1, userWithID.getId());
            ps.setNull(2, INTEGER);
            ps.setLong(3, listID);
            ps.executeUpdate();
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
        
        ////////////////////////////////
        // Modifiche da fare a ACCESS //
        ////////////////////////////////
        if (listID != 0) {
            AccessDAO accessDAO = new AccessDAO();
            Access access = new Access(userWithID.getId(),listID,true);
            accessDAO.create(access);
        }
        
    }
    
    /**
     * get user from id
     * @param id
     * @return
     */
    @Override
    public Optional<UserAnonimous> get(long id) {
        String query= "SELECT * FROM UsersAnonimous as U WHERE U.id="+id;
        UserAnonimous u=null;
        
        try {
            Connection c = Database.openConnection();
            Statement s = c.createStatement();
            ResultSet rs=s.executeQuery(query);
        
            if(rs.next()){
                u=createUserAnonimous(rs);
            }
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }        
        return Optional.of(u);
    }
    
    /**
     * Obtain a UserAnonimous from his cookie, in a optional object to understand exist really
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
        
            if(rs.next()){
                u=Optional.of(createUserAnonimous(rs));
            }
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }        
        return u;
    }
    
    /**
     * get all useranonimous, in a optional object to understand exist really
     * @return
     */
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
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }        
        return resList; //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * create user anonimous
     * @param obj
     */
    @Override
    public void create(UserAnonimous obj) {
        String query= "INSERT INTO GEODB.USERSANONIMOUS(COOKIE)\n" +
                        "VALUES (?)";
        
        try {
            Connection c = Database.openConnection();
            PreparedStatement ps = c.prepareStatement(query);
            

            ps.setString(1, obj.getCookie());
            
            ps.executeUpdate();
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * update useranonymous
     * @param id
     * @param obj
     */
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
            c.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
    }

    /**
     * delete user anonymous
     * @param id
     */
    @Override
    public void delete(long id) {
        String query ="DELETE FROM UsersAnonimous WHERE id=?";
        
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
