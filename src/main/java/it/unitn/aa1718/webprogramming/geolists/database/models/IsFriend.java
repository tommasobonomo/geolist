/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database.models;

/**
 * Model of the IsFriend relation
 * @author tommaso
 */
public class IsFriend {
 
    private long userID1;
    private long userID2;

    public IsFriend(long userID1, long userID2) {
        this.userID1 = userID1;
        this.userID2 = userID2;
    }

    public long getUserID1() {
        return userID1;
    }

    public void setUserID1(long userID1) {
        this.userID1 = userID1;
    }

    public long getUserID2() {
        return userID2;
    }

    public void setUserID2(long userID2) {
        this.userID2 = userID2;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (int) (this.userID1 ^ (this.userID1 >>> 32));
        hash = 67 * hash + (int) (this.userID2 ^ (this.userID2 >>> 32));
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
        final IsFriend other = (IsFriend) obj;
        if (this.userID1 != other.userID1) {
            return false;
        }
        if (this.userID2 != other.userID2) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IsFriend{" + "userID1=" + userID1 + ", userID2=" + userID2 + '}';
    }
    
}
