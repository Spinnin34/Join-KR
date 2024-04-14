package p.joinkr.events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import p.joinkr.Join_KR;
import p.joinkr.utils.HexColorConverter;

public class OnPlayerDisconnect implements Listener {

    private final Join_KR plugin;

    public OnPlayerDisconnect(Join_KR plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        Player player = event.getPlayer();
        FileConfiguration config = plugin.getConfig();

        String playerName = player.getName();

        if (config.getBoolean("DisconnectMessage.Enabled")) {
            String rawdisconnectMessage = config.getString("DisconnectMessage.Message");
            String disconnectMessage = HexColorConverter.convertHexCodes(rawdisconnectMessage);

            if (disconnectMessage != null && !disconnectMessage.isEmpty()) {
                disconnectMessage = disconnectMessage.replace("%PlayerName%", playerName);
                event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', disconnectMessage));
            }
        }
    }
}

