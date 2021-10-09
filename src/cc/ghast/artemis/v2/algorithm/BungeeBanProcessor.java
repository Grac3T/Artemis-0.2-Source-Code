/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.common.io.ByteStreams
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Server
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.plugin.Plugin
 */
package cc.ghast.artemis.v2.algorithm;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.algorithm.BanProfile;
import cc.ghast.artemis.v2.api.check.enums.Type;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.utils.chat.Chat;
import cc.ghast.artemis.v2.utils.configuration.Configuration;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class BungeeBanProcessor {
    public static void issueBan(BanProfile data) {
        Configuration settings = ConfigManager.getSettings();
        BungeeBanProcessor.sendCommand(Chat.translate(settings.getString("ban.command").replace("%player%", data.getPlayer().getName())));
    }

    public static void revokeBans(String check) {
        Type checkType = Arrays.stream(Type.values()).filter(type -> check.toUpperCase().contains(type.name())).findFirst().orElse(null);
        if (checkType == null) {
            return;
        }
        String var = check.toUpperCase().replace(checkType.name(), "");
        Configuration config = ConfigManager.getBans();
        ConfigurationSection s = config.getConfig().getConfigurationSection("");
        s.getKeys(true).forEach(key -> {
            ConfigurationSection section;
            if (key.equalsIgnoreCase("flag") && (section = config.getConfig().getConfigurationSection(key + "." + checkType.name().toUpperCase() + var)).getInt("count") > ConfigManager.getChecks().getInt(checkType.name().toLowerCase() + "." + var + ".max-vls") && ConfigManager.getChecks().getBoolean(checkType.name().toLowerCase() + "." + var + ".enabled") && s.getBoolean(key + ".active")) {
                BungeeBanProcessor.sendCommand("unban " + key);
                s.set(key + ".active", (Object)false);
                return;
            }
        });
        config.save();
    }

    public static void sendCommand(String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Artemis");
        out.writeUTF(message);
        Artemis.INSTANCE.getPlugin().getServer().sendPluginMessage((Plugin)Artemis.INSTANCE.getPlugin(), "BungeeCord", out.toByteArray());
    }
}

