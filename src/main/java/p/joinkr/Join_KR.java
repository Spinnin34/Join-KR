package p.joinkr;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import p.joinkr.Command.VersionCommand;
import p.joinkr.events.OnPlayerDisconnect;
import p.joinkr.events.OnPlayerJoin;
import p.joinkr.events.OnPlayerJoinMessage;

public final class Join_KR extends JavaPlugin {

    public ConsoleCommandSender console = getServer().getConsoleSender();

    @Override
    public void onEnable() {

        //Used to get the config.yml file from the server folder
        saveDefaultConfig();

        //Registers the player join event defined in OnPlayerJoin()
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerDisconnect(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoinMessage(this), this);

        //Commandos
        getCommand("version").setExecutor(new VersionCommand());

        //Woo hoo no errors!
        console.sendMessage(ChatColor.GREEN + "El título al unirse funciona como se esperaba :)");

    }

    @Override
    public void onDisable() {

    }

}
