/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database;

import java.util.List;
import java.util.Optional;

/**
 * Interface that specifies all the functions that the various DAO have to
 * 
 * @author tommaso
 */
public interface CrudDao<T> {
    
    Optional<T> get(long id);
    
    List<T> getAll();
    
    void create(T obj);
    
    void update(long id, T obj);
    
    void delete(T obj);
}
