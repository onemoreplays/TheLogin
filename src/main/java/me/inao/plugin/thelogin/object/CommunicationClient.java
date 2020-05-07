package me.inao.plugin.thelogin.object;

import lombok.Getter;
import lombok.Setter;
import me.inao.plugin.thelogin.Main;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.PrivateKey;

@Getter
public class CommunicationClient {
    private final Main main;
    @Setter
    private KeyPair rsaPair;
    @Setter
    private SecretKey aesKey = null;
    @Setter
    private String token = null;
    private final String ip;

    public CommunicationClient(Main main, String ip) {
        this.main = main;
        this.ip = ip;
        if(!main.isSlave()){
            main.getClientList().add(this);
        }
    }
}
