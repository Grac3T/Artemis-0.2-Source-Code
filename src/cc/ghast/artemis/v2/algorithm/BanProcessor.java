/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.YamlConfiguration
 */
package cc.ghast.artemis.v2.algorithm;

import cc.ghast.artemis.v2.algorithm.BanProfile;
import cc.ghast.artemis.v2.api.check.enums.Category;
import cc.ghast.artemis.v2.api.check.enums.Type;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.utils.chat.Chat;
import cc.ghast.artemis.v2.utils.configuration.Configuration;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class BanProcessor {
    public static void issueBan(BanProfile data) {
        Configuration settings = ConfigManager.getSettings();
        Bukkit.dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), (String)Chat.translate(settings.getString("ban.command").replace("%player%", data.getPlayer().getName())));
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
            if (key.equalsIgnoreCase("flag") && (section = config.getConfig().getConfigurationSection(key + "." + checkType.name().toUpperCase() + var)).getInt("count") > ConfigManager.getChecks().getInt(checkType.getCategory().name().toLowerCase() + "." + checkType.name().toLowerCase() + "." + var + ".max-vls") && ConfigManager.getChecks().getBoolean(checkType.name().toLowerCase() + "." + var + ".enabled") && s.getBoolean(key + ".active")) {
                Bukkit.dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), (String)("unban " + key));
                s.set(key + ".active", (Object)false);
            }
        });
        config.save();
    }
}

