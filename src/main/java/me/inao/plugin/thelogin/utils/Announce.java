package me.inao.plugin.thelogin.utils;

import lombok.RequiredArgsConstructor;
import me.inao.plugin.thelogin.Main;
import org.bukkit.Bukkit;

@RequiredArgsConstructor
public class Announce {
    private final Main main;
    public void sayConsole(String message){
        Bukkit.getServer().getConsoleSender().sendMessage(main.getPrefix() + message);
    }
    public void sayChat(boolean prefix, String message){
        Bukkit.getServer().broadcastMessage((prefix ? (main.getPrefix() + message) : (message)));
    }
}
