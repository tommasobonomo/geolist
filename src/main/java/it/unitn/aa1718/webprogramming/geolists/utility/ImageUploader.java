/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.utility;

import it.unitn.aa1718.webprogramming.geolists.database.ItemDAO;
import it.unitn.aa1718.webprogramming.geolists.database.models.Item;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class to help upload images to the Database
 * @author tommaso
 */
public class ImageUploader {
    
    private static File chooseImage(String name) {
        String userDir = System.getProperty("user.dir");
        String imagesDir = userDir + File.separator + "images" + File.separator;
        File f = new File(imagesDir + name + ".png");
        
        return f;
    }
    
    private static InputStream getInputStreamFromFile(String name) {
        File selectedFile = chooseImage(name);
        boolean res = true;
        InputStream image = null;
        
        try {
            image = new FileInputStream(selectedFile);
        } catch (FileNotFoundException ex) {
            res = false;
            Logger.getLogger(ImageUploader.class.getName()).log(Level.SEVERE, null, ex);   
        }
        return image;
    }
    
    /**
     * A class that takes an Item and if there is an image I in the images folder
     * such that I == item.getName(), it uploads it to the DB
     * @param item
     * @return boolean to tell if the operation succeeds
     */
    private static boolean uploadImage(Item item) {
        
        ItemDAO itemDAO = new ItemDAO();
        boolean res = false;
        InputStream image = getInputStreamFromFile("/items/" + item.getName());
        
        if (image != null) {
            res = true;
            item.setLogo(image);
            System.out.println("Uploading image " + item.getName() + ".png...");
            itemDAO.update(item.getId(), item);
        }
        
        return res;
    }
    
    /**
     * Upload all images of items found in DB
     * @param args 
     */
    public static void main(String[] args) {
        ItemDAO itemDAO = new ItemDAO();
        List<Item> allItems = itemDAO.getAll();
        for (Item item : allItems) {
            if (uploadImage(item)) {
                System.out.println("Item image " + item.getName() + ".png uploaded to the DB");
            } else {
                System.out.println("Item image " + item.getName() + ".png could not be found");
            }
        }
    }
}
