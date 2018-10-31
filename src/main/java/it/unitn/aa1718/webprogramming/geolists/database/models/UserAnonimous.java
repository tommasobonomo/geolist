/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database.models;

/**
 * Model of the UserAnonimous relation
 * @author tommaso
 */
public class UserAnonimous {
    
    private long id;
    private String cookie;

    public UserAnonimous(long id, String cookie) {
        this.id = id;
        this.cookie = cookie;
    }

    public long getId() {
        return id;
    }

    public void setId(long idAnon) {
        this.id = idAnon;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final UserAnonimous other = (UserAnonimous) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserAnonimous{" + "idAnon=" + id + ", cookie=" + cookie + '}';
    }
    
}
