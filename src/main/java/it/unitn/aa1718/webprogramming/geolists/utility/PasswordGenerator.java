package it.unitn.aa1718.webprogramming.geolists.utility;

import java.util.Random;

/**
 * class for generate password
 * @author Giorgio Segalla
 */
public class PasswordGenerator {
 
    static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static Random rnd = new Random(System.currentTimeMillis());
    static private final int LENGHT = 8;
 
    public static String generate() {
        StringBuilder sb = new StringBuilder(LENGHT);
        for (int i = 0; i < LENGHT; i++) {
            sb.append(ALPHABET.charAt(rnd.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}