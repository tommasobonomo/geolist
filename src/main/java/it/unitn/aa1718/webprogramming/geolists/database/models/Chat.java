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
public class Chat {
    private long id;
    private String name;
    private String description;
    private String picture;
    
    /**
     * con id nel costrutto
     * @param id
     * @param name
     * @param description
     * @param picture
     */
    public Chat(long id, String name, String description, String picture){
        this.id = id;
        this.name = name;
        this.description = description;
        this.picture = picture;
    }
    
    /**
     * senza id nel costrutto
     * @param name
     * @param description
     * @param picture
     */
    public Chat(String name, String description, String picture){
        this.name = name;
        this.description = description;
        this.picture = picture;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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
        final Chat other = (Chat) obj;
        return this.id == other.id;
    }
}
