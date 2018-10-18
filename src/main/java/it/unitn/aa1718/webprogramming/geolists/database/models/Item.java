/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database.models;

/**
 * Model of the Item relation
 * @author tommaso
 */
public class Item {
    private long idItem;
    private long idCat;
    private String calorie;
    private String name;
    private String logo;
    private String note;

    public Item(long idItem, long idCat, String calorie, String name, String logo, String note) {
        this.idItem = idItem;
        this.idCat = idCat;
        this.calorie = calorie;
        this.name = name;
        this.logo = logo;
        this.note = note;
    }

    public long getIdItem() {
        return idItem;
    }

    public void setIdItem(long idItem) {
        this.idItem = idItem;
    }

    public long getIdCat() {
        return idCat;
    }

    public void setIdCat(long idCat) {
        this.idCat = idCat;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (int) (this.idItem ^ (this.idItem >>> 32));
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
        final Item other = (Item) obj;
        return this.idItem == other.idItem;
    }

    @Override
    public String toString() {
        return "Item{" + "idItem=" + idItem + ", idCat=" + idCat + ", calorie=" + calorie + ", name=" + name + ", logo=" + logo + ", note=" + note + '}';
    }
    
}
