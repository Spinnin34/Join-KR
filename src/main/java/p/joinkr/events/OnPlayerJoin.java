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
        String rawTitle = replacePlaceholders(config.getString("WelcomeTitle.Title"), playerName);
        String rawSubtitle = replacePlaceholders(config.getString("WelcomeTitle.Subtitle"), playerName);

        String title = HexColorConverter.convertHexCodes(rawTitle);
        String subtitle = HexColorConverter.convertHexCodes(rawSubtitle);

        // Enviar título al jugador
        sendTitle(player, title, subtitle);

        // Enviar partículas si están habilitadas en la configuración
        handleParticles(player, config);

        handleSound(player, config);
    }

    private String replacePlaceholders(String message, String playerName) {
        if (message == null) {
            return null;
        }

        return ChatColor.translateAlternateColorCodes('&', message.replace("%PlayerName%", playerName));
    }

    private void sendTitle(Player player, String title, String subtitle) {
        int fadeIn = plugin.getConfig().getInt("TitleFadeIn", 10);
        int stay = plugin.getConfig().getInt("TitleStay", 70);
        int fadeOut = plugin.getConfig().getInt("TitleFadeOut", 20);
        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

    private void handleParticles(Player player, FileConfiguration config) {
        HashMap<String, Particle> particleMap = new HashMap<>();
        particleMap.put("Clouds", Particle.CLOUD);
        particleMap.put("Hearts", Particle.HEART);
        particleMap.put("Spark", Particle.FIREWORKS_SPARK);
        particleMap.put("Soul", Particle.SOUL_FIRE_FLAME);

        if (config.getBoolean("Particles.Enabled")) {
            String particleType = config.getString("Particles.ParticleType");

            if (particleMap.containsKey(particleType)) {
                Location particleLocation = player.getLocation().add(0, player.getEyeHeight(), 0);
                player.getWorld().spawnParticle(particleMap.get(particleType), particleLocation, 50);
            } else {
                plugin.getLogger().warning("Particle type " + particleType + " is not valid");
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
        if (config.getBoolean("PlaySoundOnJoin.Enabled")) {
            String soundName = config.getString("PlaySoundOnJoin.Sound");

            if (soundMap.containsKey(soundName)) {

                player.playSound(player.getLocation(), soundMap.get(soundName), 1.0f, 1.0f);

            } else {

                plugin.getLogger().warning("Sound " + soundName + " is not valid.");
            }
        }
    }
}