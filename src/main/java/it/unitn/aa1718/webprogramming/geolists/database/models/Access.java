/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database.models;

/**
 * Model of Access relation
 * @author tommaso
 */
public class Access { 
    private long idUser;
    private long idList;
    
    public Access(long idUser,long idList){
        this.idList=idList;
        this.idUser=idUser;
    }
    
    public void setIdUser(long idUser){
        this.idUser=idUser;
    }
    
    public long getIdUser(){
        return this.idUser;
    }
    
    public void setIdList(long idList){
        this.idList=idList;
    }
    
    public long getIdList(){
        return this.idList;
    }
    
}
