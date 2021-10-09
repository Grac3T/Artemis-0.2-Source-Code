/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.plugin.PluginDescriptionFile
 */
package cc.ghast.artemis.v2.utils.chat;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;

public class Chat {
    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)message);
    }

    public static List<String> translate(List<String> strings) {
        ArrayList<String> translated = new ArrayList<String>();
        strings.forEach(s -> translated.add(Chat.translate(s)));
        return translated;
    }

    public static void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(Chat.translate(message));
    }

    public static void sendLogo() {
        String[] logo;
        for (String s : logo = new String[]{"     _         _                 _", "    / \\   _ __| |_ ___ _ __ ___ (_)___", "   / _ \\ | '__| __/ _ \\ '_ ` _ \\| / __|", "  / ___ \\| |  | ||  __/ | | | | | \\__ \\", " /_/   \\_\\_|   \\__\\___|_| |_| |_|_|___/", "                                       "}) {
            Chat.sendConsoleMessage("&b&l" + s);
        }
        PluginDescriptionFile artemis = Artemis.INSTANCE.getPlugin().getDescription();
        Chat.sendConsoleMessage(Chat.spacer());
        Chat.sendConsoleMessage("&b&lVersion&7:&f " + artemis.getVersion());
        Chat.sendConsoleMessage("&b&lLicense&7:&f [Dev works only]");
        StringBuilder builder = new StringBuilder();
        artemis.getAuthors().forEach(author -> builder.append(author + ", "));
        Chat.sendConsoleMessage("&b&lAuthors&7:&f " + builder.toString());
    }

    public static void sendConsoleError(String id) {
        Bukkit.getConsoleSender().sendMessage(Chat.translate("&aInternal error with Lock Anticheat, please report it to the developer with the following id: " + id));
    }

    public static void notPlayer() {
        Bukkit.getConsoleSender().sendMessage(Chat.translate("&cThis command is for the use of players only!"));
    }

    public static String spacer() {
        return Chat.translate("&8&m----------------------------");
    }
}

