package me.inao.plugin.thelogin.encryption;

import lombok.RequiredArgsConstructor;
import me.inao.plugin.thelogin.Main;
import me.inao.plugin.thelogin.utils.Announce;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@RequiredArgsConstructor
public class AES {
    private final Main main;
    public SecretKey getKey(){
        if(!main.isSlave()){
            new Announce(main).sayConsole("You are running as a master. You cannot generate AES since there is no way to deliver PrivateKey secure to the slave.");
            return null;
        }
        try{
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(256);
            return generator.generateKey();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
