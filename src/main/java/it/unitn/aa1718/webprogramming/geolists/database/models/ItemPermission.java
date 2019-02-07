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
public class ItemPermission {
    private long categoryListId;
    private long categoryItemId;
    
    public ItemPermission(long categoryListId, long categoryItemId){
        this.categoryItemId=categoryItemId;
        this.categoryListId=categoryListId;
    }

    public long getCategoryListId() {
        return categoryListId;
    }

    public long getCategoryItemId() {
        return categoryItemId;
    }

    public void setCategoryListId(long categoryListId) {
        this.categoryListId = categoryListId;
    }

    public void setCategoryItemId(long categoryItemId) {
        this.categoryItemId = categoryItemId;
    }

    @Override
    public String toString() {
        return "ItemPermission{" + "categoryListId=" + categoryListId + ", categoryItemId=" + categoryItemId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (this.categoryListId ^ (this.categoryListId >>> 32));
        hash = 17 * hash + (int) (this.categoryItemId ^ (this.categoryItemId >>> 32));
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
        final ItemPermission other = (ItemPermission) obj;
        if (this.categoryListId != other.categoryListId) {
            return false;
        }
        if (this.categoryItemId != other.categoryItemId) {
            return false;
        }
        return true;
    }
    
    
}
