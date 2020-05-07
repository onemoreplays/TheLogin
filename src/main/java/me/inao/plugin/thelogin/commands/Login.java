package me.inao.plugin.thelogin.commands;

import me.inao.plugin.thelogin.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Login extends Command {
    private Main main;
    public Login(String name, Main main) {
        super(name);
        this.setDescription("Login command. What else do you need? :)");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        return false;
    }
}
