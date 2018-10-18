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
    
    private long idAnon;
    private String cookie;

    public UserAnonimous(long idAnon, String cookie) {
        this.idAnon = idAnon;
        this.cookie = cookie;
    }

    public long getIdAnon() {
        return idAnon;
    }

    public void setIdAnon(long idAnon) {
        this.idAnon = idAnon;
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
        hash = 59 * hash + (int) (this.idAnon ^ (this.idAnon >>> 32));
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
        if (this.idAnon != other.idAnon) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserAnonimous{" + "idAnon=" + idAnon + ", cookie=" + cookie + '}';
    }
    
}
