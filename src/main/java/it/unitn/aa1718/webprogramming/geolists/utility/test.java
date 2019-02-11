/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.utility;

import it.unitn.aa1718.webprogramming.geolists.database.CatProductListDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.CatList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mattia
 */
public class test {
    public static void main(String[] args){
        Map<Long, String> mapCatOfLists = new HashMap<>();
        List<CatList> listProductList = new CatProductListDAO().getAll();
        for(CatList elem : listProductList){
            mapCatOfLists.put(elem.getIdCategory(), elem.getName());
        }
        
        for(int i = 1; i<=listProductList.size(); i++){
            System.out.println("key :"+i+" -- value:"+mapCatOfLists.get(i));
        }
    }
}
