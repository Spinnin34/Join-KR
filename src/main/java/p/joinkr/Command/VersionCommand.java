package p.joinkr.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import p.joinkr.utils.HexColorConverter;
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
        TextComponent message = new TextComponent(MessageUtils.sendCenteredMessage(""));
        message.addExtra(MessageUtils.sendCenteredMessage(HexColorConverter.convertHexCodes("§cᴊᴏɪɴ ᴋᴀʀᴍᴀɴᴄᴏs\n \n")));


        // Componente de texto para el enlace
        TextComponent link = new TextComponent(MessageUtils.sendCenteredMessage("§fʜᴀᴢ ᴄʟɪᴄ ᴘᴀʀᴀ ᴀᴄᴄᴇᴅᴇʀ ᴀ ɪɴғᴏʀᴍᴀᴄɪᴏɴ ᴀᴄᴛᴜᴀʟɪᴢᴀᴅᴀ ʏ ɴᴜᴇᴠᴀs ᴀᴄᴛᴜᴀʟɪᴢᴀᴄɪᴏɴᴇs.\n \n"));
        link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://karmancos.42web.io/download/join-kr.html"));
        link.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{
                new TextComponent("¡Haz clic aquí para visitar nuestro sitio web!")
        }));

        // Agregar el enlace al mensaje
        message.addExtra(link);
        message.addExtra(MessageUtils.sendCenteredMessage("§7Developer ©Spinnin34"));

        // Enviar el mensaje al jugador
        player.spigot().sendMessage(message);

        return true;
    }
}