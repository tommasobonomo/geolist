/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database.models;

import java.sql.Timestamp;

/**
 *
 * @author root
 */
public class Message {
    private long id;
    private long idList;
    private long idUser;
    private Timestamp sendTime;
    private String text;
    
    /**
     * con id nel costrutto
     * @param id
     * @param idUser
     * @param idList
     * @param sendTime
     * @param text
     */
    public Message(long id, long idUser , long idList , Timestamp sendTime, String text){
        this.id = id;
        this.idList = idList;
        this.idUser = idUser;
        this.sendTime = sendTime;
        this.text = text;
    }
    
    /**
     * senza id nel costrutto
     * @param idUser
     * @param idList
     * @param sendTime
     * @param text
     */
    public Message(long idUser , long idList , Timestamp sendTime, String text){
        this.idList = idList;
        this.idUser = idUser;
        this.sendTime = sendTime;
        this.text = text;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public long getIdList() {
        return idList;
    }

    public void setIdList(long idList) {
        this.idList = idList;
    }
    
    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }
    
    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }   
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Message other = (Message) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Message{" + "id=" + id + ", idList=" + idList + ", idUser=" + idUser + ", sendTime=" + sendTime + ", text=" + text + '}';
    }
    
    
}
