package me.inao.plugin.thelogin.utils;

import lombok.RequiredArgsConstructor;
import me.inao.plugin.thelogin.Main;
import me.inao.plugin.thelogin.data.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.help.GenericCommandHelpTopic;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.IndexHelpTopic;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.*;

@RequiredArgsConstructor
public class Loader {
    private final Main main;
    public void loadEvents(String prefix){
        Reflections reflections = new Reflections(prefix);
        Set<Class<? extends Listener>> events = reflections.getSubTypesOf(Listener.class);
        for (Class<? extends Listener> event : events){
            try{
                Bukkit.getPluginManager().registerEvents(event.getDeclaredConstructor(new Class[]{Main.class}).newInstance(main), main);
                new Announce(main).sayConsole("Loaded event " + event.getSimpleName());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void loadCommands(String prefix){
        CommandMap map = null;
        try{
            Field cmdMap = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            cmdMap.setAccessible(true);
            map = (CommandMap) cmdMap.get(Bukkit.getPluginManager());
        }catch (Exception e){
            e.printStackTrace();
        }
        HashSet<Command> coms = new HashSet<>();
        Reflections reflections = new Reflections(prefix);
        Set<Class<? extends Command>> commands = reflections.getSubTypesOf(Command.class);
        for(Class<?extends Command> cls : commands){
            try{
                Command command = cls.getDeclaredConstructor(new Class[]{String.class, Main.class}).newInstance(cls.getSimpleName().toLowerCase(), main);
                assert map != null;
                    map.register(cls.getSimpleName().toLowerCase(), command);
                    coms.add(command);
                    new Announce(main).sayConsole("&bLoaded command " + cls.getSimpleName().toLowerCase());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        genHelp(coms);
    }
    private void genHelp(Collection<Command> commands){
        List<HelpTopic> topics = new ArrayList<>();
        for(Command c : commands){
            topics.add(new GenericCommandHelpTopic(c));
        }
        Bukkit.getHelpMap().addTopic(new IndexHelpTopic(
                "TheLogin",
                "TheLogin, made by inao.",
                Permission.COMMAND_HELP.get(),
                topics,
                "TheLogin Help"
        ));
    }
}
