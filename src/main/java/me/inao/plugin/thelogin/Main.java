package me.inao.plugin.thelogin;

import lombok.Getter;
import me.inao.plugin.thelogin.communication.Packet;
import me.inao.plugin.thelogin.communication.Server;
import me.inao.plugin.thelogin.object.CommunicationClient;
import me.inao.plugin.thelogin.utils.Announce;
import me.inao.plugin.thelogin.utils.Loader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Main extends JavaPlugin {
    private String prefix = "§7[§9Login§7] §f";
    private boolean slave = true;
    private List<CommunicationClient> clientList = null;
    private CommunicationClient communicationClient = null;
    private Announce announcer = new Announce(this);
    @Override
    public void onEnable(){
        new Announce(this).sayConsole("Loading..");
        new Loader(this).loadCommands("me.inao.plugin.thelogin.commands");
        //new Loader(this).loadEvents("me.inao.plugin.thelogin.events");
        if(slave){
            new Announce(this).sayConsole("Detected plugin mode 'slave'. That means I'm going to initialize CommunicationClient");
            communicationClient = new CommunicationClient(this, Bukkit.getIp());
            new Packet(this).doKeyExchange();
        }else{
            new Announce(this).sayConsole("Detected plugin mode 'master'. That means I'm going to enable List of CommunicationClient to cache KeyExchanged clients.");
            clientList = new ArrayList<>();
            new Server(this).start();
        }
        new Announce(this).sayConsole("Loaded.");
    }
    @Override
    public void onDisable(){

    }
}
