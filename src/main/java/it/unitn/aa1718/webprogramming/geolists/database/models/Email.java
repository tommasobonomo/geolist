/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database.models;

/**
 * Model of the Email relation
 * @author tommaso
 */
public class Email {
    
    private long    idMail;
    private String  info;
    private String  text;
    private long    sender;
    private long    receiver;

    public Email(long idMail, String info, String text, long sender, long receiver) {
        this.idMail = idMail;
        this.info = info;
        this.text = text;
        this.sender = sender;
        this.receiver = receiver;
    }

    public long getIdMail() {
        return idMail;
    }

    public void setIdMail(long idMail) {
        this.idMail = idMail;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getSender() {
        return sender;
    }

    public void setSender(long sender) {
        this.sender = sender;
    }

    public long getReceiver() {
        return receiver;
    }

    public void setReceiver(long receiver) {
        this.receiver = receiver;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (int) (this.idMail ^ (this.idMail >>> 32));
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
        final Email other = (Email) obj;
        if (this.idMail != other.idMail) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Email{" + "idMail=" + idMail + ", info=" + info + ", text=" + text + ", sender=" + sender + ", receiver=" + receiver + '}';
    }
    
}
