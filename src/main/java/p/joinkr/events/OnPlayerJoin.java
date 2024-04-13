package p.joinkr.events;

import java.util.HashMap;

import java.util.HashMap;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import p.joinkr.Join_KR;
import p.joinkr.utils.HexColorConverter;

public class OnPlayerJoin implements Listener {

    private final Join_KR plugin;

    public OnPlayerJoin(Join_KR plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = plugin.getConfig();

        String playerName = player.getName();

        String title = replacePlaceholders(config.getString("Options.WelcomeMessage.Title"), playerName);
        String subtitle = replacePlaceholders(config.getString("Options.WelcomeMessage.Subtitle"), playerName);

        if (config.getBoolean("WelcomeMessage.Enabled")) {
            String welcomeMessage = config.getString("WelcomeMessage.Message");
            if (welcomeMessage != null && !welcomeMessage.isEmpty()) {
                welcomeMessage = welcomeMessage.replace("%PlayerName%", playerName);
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', welcomeMessage));
            }
        }

        // Enviar título al jugador
        sendTitle(player, title, subtitle);

        // Enviar partículas si están habilitadas en la configuración
        spawnParticles(player);

        // Reproducir sonido si está habilitado en la configuración
        playSound(player);
    }

    private String replacePlaceholders(String message, String playerName) {
        if (message == null) {
            return null;
        }

        return ChatColor.translateAlternateColorCodes('&', message.replace("%PlayerName%", playerName));
    }

    private void sendTitle(Player player, String title, String subtitle) {
        int fadeIn = plugin.getConfig().getInt("Options.TitleFadeIn", 10);
        int stay = plugin.getConfig().getInt("Options.TitleStay", 70);
        int fadeOut = plugin.getConfig().getInt("Options.TitleFadeOut", 20);
        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

    private void spawnParticles(Player player) {
        FileConfiguration config = plugin.getConfig();
        if (config.getBoolean("Options.Particles.Enabled")) {
            String particleType = config.getString("Options.Particles.Type");
            Particle particle = Particle.valueOf(particleType.toUpperCase());
            player.getWorld().spawnParticle(particle, player.getLocation(), 10);
        }
    }

    private void playSound(Player player) {
        FileConfiguration config = plugin.getConfig();
        if (config.getBoolean("Options.PlaySoundOnJoin.Enabled")) {
            String soundName = config.getString("Options.PlaySoundOnJoin.Sound");
            Sound sound = Sound.valueOf(soundName.toUpperCase());
            player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
        }
    }
}