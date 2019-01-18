/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database.models;

/**
 *
 * @author root
 */
public class Message {
    private long id;
    private long idChat;
    private long idUser;
    private String sendTime;
    private String text;
    
    /**
     * con id nel costrutto
     * @param id
     * @param idUser
     * @param idChat
     * @param sendTime
     * @param text
     */
    public Message(long id, long idUser , long idChat , String sendTime, String text){
        this.id = id;
        this.idChat = idChat;
        this.idUser = idUser;
        this.sendTime = sendTime;
        this.text = text;
    }
    
    /**
     * senza id nel costrutto
     * @param idUser
     * @param idChat
     * @param sendTime
     * @param text
     */
    public Message(long idUser , long idChat , String sendTime, String text){
        this.idChat = idChat;
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
    
    public long getIdChat() {
        return idChat;
    }

    public void setIdChat(long idChat) {
        this.idChat = idChat;
    }
    
    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }
    
    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
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
    
}
