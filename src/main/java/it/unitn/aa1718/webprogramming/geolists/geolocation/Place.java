/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.geolocation;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class to represent the shops that are returned from HERE API
 * @author tommaso
 */
public class Place {

    private String title;
    private String location;
    private String vicinity;
    private String category;
    private double distance;
    
    public Place(JSONObject place) {
        title = place.getString("highlightedTitle");
        vicinity = place.getString("vicinity");
        category = place.getString("categoryTitle");
        distance = place.getDouble("distance");
        
        JSONArray tmp = place.getJSONArray("position");
        location = Double.toString(tmp.getDouble(0)) + "," + Double.toString(tmp.getDouble(1));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Place{" + "title=" + title + ", location=" + location + ", vicinity=" + vicinity + ", category=" + category + ", distance=" + distance + '}';
    }
    
}
