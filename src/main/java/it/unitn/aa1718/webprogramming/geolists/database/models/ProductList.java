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
    private long userOwner;
    private long userAnonOwner;
    private long idCat;
    private String name;
    private String description;
    private String image;

    /**
     * constructor with id
     * @param id
     * @param userOwner
     * @param userAnonOwner
     * @param idCat
     * @param name
     * @param description
     * @param image
     */
    public ProductList(long id, long userOwner, long userAnonOwner, long idCat, String name, String description, String image) {
        this.id = id;
        this.userOwner = userOwner;
        this.userAnonOwner = userAnonOwner;
        this.idCat = idCat;
        this.name = name;
        this.description = description;
        this.image = image;
    }
  
    /**
     * constructor without id
     * @param userOwner
     * @param userAnonOwner
     * @param idCat
     * @param name
     * @param description
     * @param image
     */
    public ProductList(long userOwner, long userAnonOwner, long idCat, String name, String description, String image) {
        this.userOwner = userOwner;
        this.userAnonOwner = userAnonOwner;
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

    public long getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(long userOwner) {
        this.userOwner = userOwner;
    }
    
    public long getUserAnonOwner() {
        return userAnonOwner;
    }

    public void setUserAnonOwner(long userAnonOwner) {
        this.userAnonOwner = userAnonOwner;
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
        return "List{" + "id=" + id + ", userCreator=" + userOwner + ", idCat=" + idCat + ", name=" + name + ", description=" + description + ", image=" + image + '}';
    }
  
}
