/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

import it.unitn.aa1718.webprogramming.geolists.database.models.User;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author giorgiosgl
 */
public class main {
    private User createUser(ResultSet rs) throws SQLException {
        return new User(rs.getLong("id"),rs.getString("cookie"),rs.getString("username"), rs.getString("name"), rs.getString("lastname")
                , rs.getString("email"), rs.getString("password"),rs.getString("image"), rs.getBoolean("admin"));
    }
    
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        User u = new User(12123,"","giorgiosgasdassdal","giorgio","segalla","giorgisdsdosgl@gmail.com","pasta","null",true);
        dao.create(u);
    }
    
}
