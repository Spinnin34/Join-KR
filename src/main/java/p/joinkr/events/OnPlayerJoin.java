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

        // Enviar título al jugador
        sendTitle(player, title, subtitle);

        // Enviar partículas si están habilitadas en la configuración
        handleParticles(player, config);

        handleSound(player, config);

        if (config.getBoolean("WelcomeMessage.Enabled")) {
            String welcomeMessage = config.getString("WelcomeMessage.Message");
            if (welcomeMessage != null && !welcomeMessage.isEmpty()) {
                welcomeMessage = welcomeMessage.replace("%PlayerName%", playerName);
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', welcomeMessage));
            }
        }
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



    private void handleParticles(Player player, FileConfiguration config) {

        HashMap<String, Particle> particleMap = new HashMap<>();
        particleMap.put("Clouds", Particle.CLOUD);
        particleMap.put("Hearts", Particle.HEART);
        particleMap.put("Spark", Particle.FIREWORKS_SPARK);
        particleMap.put("Soul", Particle.SOUL_FIRE_FLAME);

        if (config.getBoolean("Options.Particles.Enabled")) {

            String partticleType = config.getString("Options.Particles.Type");

            if (particleMap.containsKey(partticleType)) {
                player.getWorld().spawnParticle(particleMap.get(partticleType), player.getLocation(), 10);
            } else {
                plugin.getLogger().warning("Particle type " + partticleType + " is not valid");
            }
        }
    }

    private void handleSound(Player player, FileConfiguration config) {
        HashMap<String, Sound> soundMap = new HashMap<>();
        soundMap.put("EXP", Sound.ENTITY_PLAYER_LEVELUP);
        soundMap.put("Anvil", Sound.BLOCK_ANVIL_HIT);
        soundMap.put("Trade", Sound.ENTITY_VILLAGER_TRADE);
        soundMap.put("Firework", Sound.ENTITY_FIREWORK_ROCKET_SHOOT);

        // true o false
        if (config.getBoolean("Options.PlayerSoundOnJoin.Enabled")) {
            String soundName = config.getString("Options.PlaySoundOnJoin.Sound");

            if (soundMap.containsKey(soundName)) {

                player.playSound(player.getLocation(), soundMap.get(soundName), 1.0f, 1.0f);

            } else {

                plugin.getLogger().warning("Sound " + soundName + " is not valid.");
            }
        }
    }
}