package p.joinkr.utils;

import net.md_5.bungee.api.ChatColor;


public class HexColorConverter {

    public static String convertHexCodes(String input) {
        StringBuilder result = new StringBuilder();
        int index = 0;
        while (index < input.length()) {
            if (input.charAt(index) == '&' && index + 7 <= input.length() && input.charAt(index + 1) == '#' && isHex(input.substring(index + 2, index + 8))) {
                String hexCode = input.substring(index + 1, index + 8);
                ChatColor color = hexToChatColor(hexCode);
                result.append(color);
                index += 8;
            } else {
                result.append(input.charAt(index));
                index++;
            }
        }
        return result.toString();
    }
    public static ChatColor hexToChatColor(String hexCode) {
        if (!hexCode.startsWith("#") || hexCode.length() != 7) {
            return ChatColor.RESET;
        }

        try {
            int r = Integer.parseInt(hexCode.substring(1, 3), 16);
            int g = Integer.parseInt(hexCode.substring(3, 5), 16);
            int b = Integer.parseInt(hexCode.substring(5, 7), 16);

            return ChatColor.of(new java.awt.Color(r, g, b));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ChatColor.RESET;
        }
    }

    private static boolean isHex(String s) {
        String hexPattern = "[0-9a-fA-F]+";
        return s.matches(hexPattern);
    }
}
