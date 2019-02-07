/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database.models;

/**
 * Model of the Compose
 * @author tommaso
 */
public class Compose {
    
    private long idList;
    private long idItem;
    private int quantity;
    private boolean take;

    public Compose(long idList, long idItem, int quantity,boolean take) {
        this.idList = idList;
        this.idItem = idItem;
        this.quantity = quantity;
        this.take=take;
    }

    public long getIdList() {
        return idList;
    }

    public void setIdList(long idList) {
        this.idList = idList;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getIdItem() {
        return idItem;
    }

    public void setIdItem(long idItem) {
        this.idItem = idItem;
    }
    
    public boolean isTake() {
        return take;
    }

    public void setIsTake(boolean isTake) {
        this.take = isTake;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (int) (this.idList ^ (this.idList >>> 32));
        hash = 67 * hash + (int) (this.idItem ^ (this.idItem >>> 32));
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
        final Compose other = (Compose) obj;
        if (this.idList != other.idList) {
            return false;
        }
        if (this.idItem != other.idItem) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Compose{" + "idList=" + idList + ", idItem=" + idItem + ", quantity= " + quantity + ", istake= " + take +'}';
    }
    
    
    
}
