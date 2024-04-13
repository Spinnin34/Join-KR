package p.joinkr.events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import p.joinkr.Join_KR;

public class OnPlayerDisconnect implements Listener {

    private final Join_KR plugin;

    public OnPlayerDisconnect(Join_KR plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = plugin.getConfig();

        String playerName = player.getName();

        if (config.getBoolean("DisconnectMessage.Enabled")) {
            String disconnectMessage = config.getString("DisconnectMessage.Message");
            if (disconnectMessage != null && !disconnectMessage.isEmpty()) {
                disconnectMessage = disconnectMessage.replace("%PlayerName%", playerName);
                event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', disconnectMessage));
            }
        }
    }

    private String replacePlaceholders(String message, String playerName) {
        if (message == null) {
            return null;
        }

        return ChatColor.translateAlternateColorCodes('&', message.replace("%PlayerName%", playerName));
    }
}

