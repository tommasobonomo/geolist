/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.utility;

import it.unitn.aa1718.webprogramming.geolists.database.ItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.ItemPermissionDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.CatItem;
import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mattia
 */
public class test {
    public static void main(String[] args){
        List<CatItem> idCategories = new ItemPermissionDAO().getItemCategories(1);
        List<Item> items = new ArrayList<>();
        for (CatItem elem : idCategories){
            List<Item> itemOfCategory = new ItemDAO().getAllByIdCat(elem.getIdCatItem());
            items.addAll(itemOfCategory);
        }
        
        for(Item elem : items){
            System.out.println("catItem:"+ elem.getIdCat()+" ----- name:"+elem.getName());
        }
    }
}
