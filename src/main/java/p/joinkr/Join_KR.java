package p.joinkr;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import p.joinkr.events.OnPlayerJoin;

public final class Join_KR extends JavaPlugin {

    public ConsoleCommandSender console = getServer().getConsoleSender();

    @Override
    public void onEnable() {

        //Used to get the config.yml file from the server folder
        saveDefaultConfig();

        //Saves the configuration file
        this.saveDefaultConfig();

        //Registers the player join event defined in OnPlayerJoin();
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(this), this);

        //Woo hoo no errors!
        console.sendMessage(ChatColor.GREEN + "Title on join working as expected :)");

    }

    @Override
    public void onDisable() {

    }

}
