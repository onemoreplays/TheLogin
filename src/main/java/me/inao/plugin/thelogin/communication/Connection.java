package me.inao.plugin.thelogin.communication;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import me.inao.plugin.thelogin.Main;
import me.inao.plugin.thelogin.communication.packets.Keyexchange;
import me.inao.plugin.thelogin.encryption.AES;
import me.inao.plugin.thelogin.encryption.RSA;
import me.inao.plugin.thelogin.encryption.Utils;
import me.inao.plugin.thelogin.object.CommunicationClient;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;

@RequiredArgsConstructor
public class Connection extends Thread{
    private final Main main;
    private final Socket socket;
    @Override
    public void run(){
        main.getAnnouncer().sayConsole("Received Connection!");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            CommunicationClient client = null;
            Gson gson = new Gson();
            String received = reader.readLine();
            if(received.contains("Hello")){
                client = new CommunicationClient(main, socket.getInetAddress().getHostAddress());
                client.setRsaPair(new RSA(main).generateKeys());
                socket.setKeepAlive(true);
                writer.println(gson.toJson(new Keyexchange("1", Base64.getEncoder().encodeToString(client.getRsaPair().getPublic().getEncoded()))));
                String st2 = reader.readLine();
                Keyexchange stage2 = gson.fromJson(st2, Keyexchange.class);
                String key = new Utils().getDecryptedBytesRSA(Base64.getDecoder().decode(stage2.getData()), client.getRsaPair().getPrivate());
                main.getAnnouncer().sayConsole(key);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
