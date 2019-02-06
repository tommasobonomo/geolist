/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database.models;

import java.io.Serializable;
import java.io.InputStream;

/**
 * Model of relation CItem
 * @author tommaso
 */
public class CatItem implements Serializable {
    
    private long idCatItem;
    private String name;
    private String description;
    private InputStream image;

    public CatItem(long idCatItem, String name, String description, InputStream image) {
        this.idCatItem = idCatItem;
        this.name = name;
        this.description = description;
        this.image = image;
    }
    
    public CatItem( String name, String description, InputStream image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public long getIdCatItem() {
        return idCatItem;
    }

    public void setIdCatItem(long idCatItem) {
        this.idCatItem = idCatItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (this.idCatItem ^ (this.idCatItem >>> 32));
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
        final CatItem other = (CatItem) obj;
        return this.idCatItem == other.idCatItem;
    }

    @Override
    public String toString() {
        return "CatItem{" + "idCatItem=" + idCatItem + ", name=" + name + ", description=" + description + ", image=" + image + '}';
    }
  
}
