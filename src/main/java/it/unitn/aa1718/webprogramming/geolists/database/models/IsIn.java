/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database.models;

/**
 * fa capire a che chat appartiene un utente
 * @author root
 */
public class IsIn {
    private long idUser;
    private long idChat;
    
    public IsIn(long idUser,long idChat){
        this.idChat=idChat;
        this.idUser=idUser;
    }
    
    public void setIdUser(long idUser){
        this.idUser=idUser;
    }
    
    public long getIdUser(){
        return this.idUser;
    }
    
    public void setIdChat(long idChat){
        this.idChat=idChat;
    }
    
    public long getIdChat(){
        return this.idChat;
    }
}
