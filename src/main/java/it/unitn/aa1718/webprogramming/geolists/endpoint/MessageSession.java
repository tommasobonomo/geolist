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
    private long id2;
    private long id1;
    private String op;
    
    public MessageSession(String txt) {
        String[] words = txt.split(" ");
        
        op    =words[0];
        id1=Long.valueOf(words[1]);
        id2=Long.valueOf(words[2]);
    }
    
    public MessageSession(String txt, int x) {
        String[] words = txt.split(" ");
        
        op    =words[0];
        id1=Long.valueOf(words[1]);
    }

    public long getId1() {
        return id1;
    }

    public void setId1(long id1) {
        this.id1 = id1;
    }

    public long getId2() {
        return id2;
    }

    public void setId2(long id2) {
        this.id2 = id2;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }
    
}
