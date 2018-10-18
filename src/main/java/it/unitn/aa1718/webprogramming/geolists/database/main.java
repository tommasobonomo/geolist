/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

/**
 *
 * @author giorgiosgl
 */
public class main {
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        System.out.println(dao.get(1423879));
    }
    
}
