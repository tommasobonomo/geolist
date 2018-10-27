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
    private long id;
    private long idCat;
    private String calorie;
    private String name;
    private String logo;
    private String note;

    /**
     * constructor with all field
     * @param id
     * @param idCat
     * @param calorie
     * @param name
     * @param logo
     * @param note
     */
    public Item(long id, long idCat, String calorie, String name, String logo, String note) {
        this.id = id;
        this.idCat = idCat;
        this.calorie = calorie;
        this.name = name;
        this.logo = logo;
        this.note = note;
    }
    
    /**
     * constructor without id
     *
     * @param idCat
     * @param calorie
     * @param name
     * @param logo
     * @param note
     */
    public Item(long idCat, String calorie, String name, String logo, String note) {
        this.idCat = idCat;
        this.calorie = calorie;
        this.name = name;
        this.logo = logo;
        this.note = note;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        final Item other = (Item) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Item{" + "idItem=" + id + ", idCat=" + idCat + ", calorie=" + calorie + ", name=" + name + ", logo=" + logo + ", note=" + note + '}';
    }
    
}
