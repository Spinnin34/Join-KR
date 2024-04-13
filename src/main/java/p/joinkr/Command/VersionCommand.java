package p.joinkr.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import p.joinkr.utils.MessageUtils;

public class VersionCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando solo puede ser ejecutado por un jugador.");
            return true;
        }

        Player player = (Player) sender;

        // Construir el mensaje de varias líneas
        TextComponent message = new TextComponent(MessageUtils.sendCenteredMessage("§x§B§8§5§7§F§Bᴊ§x§B§9§5§E§F§Bᴏ§x§B§A§6§6§F§Cɪ§x§B§B§6§D§F§Cɴ §x§B§B§7§4§F§Cᴋ§x§B§C§7§C§F§Dᴀ§x§B§D§8§3§F§Dʀ§x§B§E§8§A§F§Dᴍ§x§B§F§9§2§F§Eᴀ§x§C§0§9§9§F§Eɴ§x§C§0§A§0§F§Eᴄ§x§C§1§A§8§F§Fᴏ§x§C§2§A§F§F§Fs\n \n§fHᴀᴢ ᴄʟɪᴄ ᴘᴀʀᴀ ᴀᴄᴄᴇᴅᴇʀ ᴀ ɪɴғᴏʀᴍᴀᴄɪᴏɴ ᴀᴄᴛᴜᴀʟɪᴢᴀᴅᴀ ʏ ɴᴜᴇᴠᴀs ᴀᴄᴛᴜᴀʟɪᴢᴀᴄɪᴏɴᴇs.\n \n§7Developer ©Spinnin34"));
        message.addExtra("Web Oficial ");

        // Componente de texto para el enlace
        TextComponent link = new TextComponent("sitio web");
        link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://example.com"));
        link.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{
                new TextComponent("¡Haz clic aquí para visitar nuestro sitio web!")
        }));

        // Agregar el enlace al mensaje
        message.addExtra(link);
        message.addExtra(" para obtener más información.");

        // Enviar el mensaje al jugador
        player.spigot().sendMessage(message);

        return true;
    }
}