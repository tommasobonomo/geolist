/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database.models;

import java.io.InputStream;

/**
 * Model of the CList relation
 * @author tommaso
 */
public class CatList {
    private long idCategory;
    private String name;
    private String description;
    private String HERECODE;
    private InputStream image;

    public CatList(long idCategory, String name, String description, String HERECODE, InputStream image) {
        this.idCategory = idCategory;
        this.name = name;
        this.description = description;
        this.HERECODE = HERECODE;
        this.image = image;
    }

    public long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(long idCategory) {
        this.idCategory = idCategory;
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

    public String getHERECODE() {
        return HERECODE;
    }

    public void setHERECODE(String HERECODE) {
        this.HERECODE = HERECODE;
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
        hash = 47 * hash + (int) (this.idCategory ^ (this.idCategory >>> 32));
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
        final CatList other = (CatList) obj;
        return this.idCategory == other.idCategory;
    }

    @Override
    public String toString() {
        return "CatList{" + "idCategory=" + idCategory + ", name=" + name + ", description=" + description + ", HERECODE=" + HERECODE + ", image=" + image + '}';
    }
    
}
