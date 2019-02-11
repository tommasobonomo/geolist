/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database.models;

import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Model of the Item relation
 * @author tommaso
 */
public class Item implements Serializable{
    private long id;
    private long idCat;
    private String name;
    transient private InputStream logo;
    private String note;
    private BigDecimal price;

    /**
     * constructor with all field
     * use BigDecimal.valueOf(value) for set the price
     * @param id
     * @param idCat
     * @param name
     * @param logo
     * @param note
     */
    public Item(long id, long idCat, String name, InputStream logo, String note) {
        this.id = id;
        this.idCat = idCat;
        this.name = name;
        this.logo = logo;
        this.note = note;
    }
    
    /**
     * constructor without id
     * use BigDecimal.valueOf(value) for set the price
     * @param idCat
     * @param name
     * @param logo
     * @param note
     */
    public Item(long idCat, String name, InputStream logo, String note) {
        this.idCat = idCat;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InputStream getLogo() {
        return logo;
    }

    public void setLogo(InputStream logo) {
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
        return "Item{" + "idItem=" + id + ", idCat=" + idCat + ", name=" + name + ", logo=" + logo + ", note=" + note + '}';
    }
    
}
