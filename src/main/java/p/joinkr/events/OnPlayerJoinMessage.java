package p.joinkr.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import p.joinkr.Join_KR;
import p.joinkr.utils.HexColorConverter;

public class OnPlayerJoinMessage implements Listener {

    private final Join_KR plugin;

    public OnPlayerJoinMessage(Join_KR plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        FileConfiguration config = plugin.getConfig();

        String playerName = player.getName();


        if (config.getBoolean("WelcomeMessage.Enabled")) {
            String rawWelcomeMessage = config.getString("WelcomeMessage.Message");
            String welcomeMessage = HexColorConverter.convertHexCodes(rawWelcomeMessage);

            if (welcomeMessage != null && !welcomeMessage.isEmpty()) {
                welcomeMessage = welcomeMessage.replace("%PlayerName%", playerName);
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', welcomeMessage));
            }
        }
    }
}
