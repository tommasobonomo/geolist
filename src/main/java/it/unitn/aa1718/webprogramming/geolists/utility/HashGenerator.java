package it.unitn.aa1718.webprogramming.geolists.utility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {
    //seleziono l'algoritmo che ho intenzione di utilizzare
    private static final String ALGORITHM = "SHA-1";
    
    //funzione che ritorna l'hash o null nel caso di errori
    public static String Hash( String password) throws NoSuchAlgorithmException{
        
        try{
            // MessageDigest permette di manopale stringhe e trasformarle in hashcodes
            // attraverso di un algoritmo specificatp
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            
            // Inserisco nel md l'array di bytes ricavati dalla password
            md.update(password.getBytes("UTF-8"));
            
            // restituisco la hash generata dal digest del md
            return getHash(md.digest());
            
        } catch( NoSuchAlgorithmException | UnsupportedEncodingException e){
            return null;
        }
    }
    
    
    // funzione che genera la Hash attraverso
    private static String getHash(byte[] data){
        // non so perchè ho bisogno del StringBuffer e non di una string normale...
        // con string normale e concat non funziona
        StringBuffer hash = new StringBuffer();
        
        for (byte b : data){
            //per ogni byte della password genero un chunk da aggiungere in coda all'hash
            String chunk = Integer.toString(b, 16);
            
            if (chunk.length() == 1){
                hash.append("0");
            }
            hash.append(chunk);
        }
        
        return hash.toString();
    }
}
