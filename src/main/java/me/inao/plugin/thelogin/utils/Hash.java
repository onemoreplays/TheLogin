package me.inao.plugin.thelogin.utils;

import de.mkammerer.argon2.Argon2Factory;

public class Hash {
    public static String getHash(String plain){
        return Argon2Factory.create().hash(20, 65536, 1, plain.toCharArray());
    }
    public static boolean verifyHash(String hash, String plain){
        if(hash.length() == 0 || plain.length() == 0){
            return false;
        }
        return Argon2Factory.create().verify(hash, plain.toCharArray());
    }
}
