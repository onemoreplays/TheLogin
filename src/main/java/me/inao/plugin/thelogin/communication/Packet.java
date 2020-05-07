package me.inao.plugin.thelogin.communication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import me.inao.plugin.thelogin.Main;
import me.inao.plugin.thelogin.communication.packets.Hello;
import me.inao.plugin.thelogin.communication.packets.Keyexchange;
import me.inao.plugin.thelogin.encryption.AES;
import me.inao.plugin.thelogin.encryption.RSA;
import me.inao.plugin.thelogin.encryption.Utils;
import me.inao.plugin.thelogin.utils.Announce;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;

@RequiredArgsConstructor
public class Packet {
    private final Main main;
    public void sendPacket(){
        if(!main.isSlave()){
            new Announce(main).sayConsole("You are running as a master. You don't have to write packets standalone.");
            return;
        }

    }
    public void doKeyExchange(){
        if(!main.isSlave()){
            new Announce(main).sayConsole("You are running as a master. You don't have to write packets standalone.");
            return;
        }
        try (Socket socket = new Socket("127.0.0.1", 5544)){
            socket.setKeepAlive(true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Gson gson = new Gson();
            writer.println(gson.toJson(new Hello()));
            Keyexchange stage1 = gson.fromJson(reader.readLine(), Keyexchange.class);
            main.getCommunicationClient().setAesKey(new AES(main).getKey());
            main.getAnnouncer().sayConsole(Base64.getEncoder().encodeToString(main.getCommunicationClient().getAesKey().getEncoded()));
            writer.println(gson.toJson(new Keyexchange("2", new Utils().getEncryptedBytesRSA(main.getCommunicationClient().getAesKey().getEncoded(), new RSA(main).genPublicFromServer(stage1.getData())))));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
