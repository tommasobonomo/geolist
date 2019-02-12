/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.aa1718.webprogramming.geolists.endpoint;

/**
 *
 * @author root
 */
public class MessageSession {
    private long itemId;
    private long listId;
    private String op;
    
    public MessageSession(String txt) {
        String[] words = txt.split(" ");
        
        for(String w : words){
            System.out.println(w);
        }
        
        op    =words[0];
        itemId=Long.valueOf(words[1]);
        listId=Long.valueOf(words[2]);
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }
    
}
