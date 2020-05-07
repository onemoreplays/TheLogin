package me.inao.plugin.thelogin.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class Utils {
    public String getDecryptedRSA(String encrypted, PrivateKey key){
        try{
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return (new String(cipher.doFinal(encrypted.getBytes())));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String getEncryptedRSA(String plain, PublicKey key){
        try{
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return (new String(Base64.getEncoder().encode(cipher.doFinal(plain.getBytes()))));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String getEncryptedBytesRSA(byte[] input, PublicKey key){
        try{
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return (new String(Base64.getEncoder().encode(cipher.doFinal(input))));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String getDecryptedBytesRSA(byte[] input, PrivateKey key){
        try{
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return (new String(Base64.getEncoder().encode(cipher.doFinal(input))));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String getDecryptedAES(String encrypted, SecretKey key){
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(encrypted.getBytes()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String getEncryptedAES(String plain, SecretKey key){
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return new String(Base64.getEncoder().encode(cipher.doFinal(plain.getBytes())));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
