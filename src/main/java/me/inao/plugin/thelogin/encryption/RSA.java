package me.inao.plugin.thelogin.encryption;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.inao.plugin.thelogin.Main;
import me.inao.plugin.thelogin.utils.Announce;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@RequiredArgsConstructor
public class RSA {
    private final Main main;
    public KeyPair generateKeys(){
        if(main.isSlave()){
            new Announce(main).sayConsole("You are running as a slave. You cannot generate own RSA keypair.");
            return null;
        }
        try{
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            return generator.generateKeyPair();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public PublicKey genPublicFromServer(String encodedKey){
        if(!main.isSlave()){
            new Announce(main).sayConsole("You are running as a master. You don't have to get public key from server since you already have private and public keys generated.");
            return null;
        }
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(encodedKey.getBytes()));
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return factory.generatePublic(keySpec);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
