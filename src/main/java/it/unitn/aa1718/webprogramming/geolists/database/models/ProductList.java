/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.database.models;

/**
 * Model of the List relation
 * @author tommaso
 */
public class ProductList {

    private long id;
    private long userCreator;
    private long idCat;
    private String name;
    private String description;
    private String image;

    /**
     *  constructor with all element
     * 
     * @param id
     * @param userCreator
     * @param idCat
     * @param name
     * @param description
     * @param image
     */
    public ProductList(long id, long userCreator, long idCat, String name, String description, String image) {
        this.id = id;
        this.userCreator = userCreator;
        this.idCat = idCat;
        this.name = name;
        this.description = description;
        this.image = image;
    }
    
    /**
     * constructor without id
     *
     * @param userCreator
     * @param idCat
     * @param name
     * @param description
     * @param image
     */
    public ProductList(long userCreator, long idCat, String name, String description, String image) {
        this.userCreator = userCreator;
        this.idCat = idCat;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(long userCreator) {
        this.userCreator = userCreator;
    }

    public long getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final ProductList other = (ProductList) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "List{" + "id=" + id + ", userCreator=" + userCreator + ", idCat=" + idCat + ", name=" + name + ", description=" + description + ", image=" + image + '}';
    }
  
    
}
