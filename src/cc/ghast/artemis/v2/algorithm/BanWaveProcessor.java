/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.IOUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.json.simple.JSONArray
 *  org.json.simple.JSONObject
 *  org.json.simple.JSONValue
 */
package cc.ghast.artemis.v2.algorithm;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.api.API;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.enums.Type;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.data.StaffEnums;
import cc.ghast.artemis.v2.api.data.Violation;
import cc.ghast.artemis.v2.api.events.ArtemisBanEvent;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.managers.PlayerDataManager;
import cc.ghast.artemis.v2.utils.chat.Chat;
import cc.ghast.artemis.v2.utils.configuration.Configuration;
import java.net.InetSocketAddress;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class BanWaveProcessor {
    public static void addPlayerToBW(PlayerData data) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            PlayerData targetData = Artemis.INSTANCE.getApi().getPlayerDataManager().getData((Player)player);
            if (player.hasPermission("artemis.alert")) {
                String msg = Chat.translate(ConfigManager.getSettings().getString("general.prefix") + " " + ConfigManager.getSettings().getString("message.banwave").replace("%player%", data.getPlayer().getName()));
                if (targetData.staff.getStaffAlert().equals((Object)StaffEnums.StaffAlerts.ALERTS) || targetData.staff.getStaffAlert().equals((Object)StaffEnums.StaffAlerts.VERBOSE) || targetData.staff.getStaffAlert().equals((Object)StaffEnums.StaffAlerts.EXPERIMENTAL_VERBOSE)) {
                    player.sendMessage(msg);
                }
            }
        });
        Configuration config = ConfigManager.getBanwave();
        config.set(data.getPlayer().getUniqueId() + ".date", LocalDateTime.now().toString());
        config.set(data.getPlayer().getUniqueId() + ".ip", data.getPlayer().getAddress().getHostString());
        config.set(data.getPlayer().getUniqueId() + ".name", data.getPlayer().getName());
        config.set(data.getPlayer().getUniqueId() + ".total-flags", data.user.getViolations().size());
        config.set(data.getPlayer().getUniqueId() + ".active", true);
        data.user.getViolations().forEach((check, vl) -> {
            config.set(data.getPlayer().getUniqueId() + ".flag." + check.getType().name() + check.getVar() + ".count", vl.getCount());
            config.set(data.getPlayer().getUniqueId() + ".flag." + check.getType().name() + check.getVar() + ".timestamp", vl.getTimestamp());
        });
    }

    public static int executeBanWave() {
        int count = ConfigManager.getBanwave().getConfig().getConfigurationSection("").getKeys(false).size();
        Configuration config = ConfigManager.getBans();
        Configuration bans = ConfigManager.getBanwave();
        bans.getConfig().getConfigurationSection("").getKeys(false).forEach(uuid -> {
            if (uuid != null) {
                config.set(uuid + ".date", bans.get(uuid + ".date"));
                config.set(uuid + ".ip", bans.get(uuid + ".ip"));
                config.set(uuid + ".name", bans.get(uuid + ".name"));
                config.set(uuid + ".total-flags", bans.get(uuid + ".total-flags"));
                config.set(uuid + ".active", true);
                if (bans.getConfig().getConfigurationSection(uuid + ".flag").getKeys(false) != null) {
                    bans.getConfig().getConfigurationSection(uuid + ".flag").getKeys(false).forEach(key -> {
                        config.set(uuid + ".flag." + key + ".count", bans.getInt(uuid + ".flag." + key + ".count"));
                        config.set(uuid + ".flag." + key + ".timestamp", bans.getInt(uuid + ".flag." + key + ".timestamp"));
                    });
                }
                bans.set((String)uuid, null);
                Bukkit.dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), (String)Chat.translate(ConfigManager.getSettings().getString("ban.command").replace("%player%", config.getString(uuid + ".name"))));
                Bukkit.getPluginManager().callEvent((Event)new ArtemisBanEvent(Bukkit.getOfflinePlayer((String)uuid)));
            }
        });
        ConfigManager.getBanwave().save();
        ConfigManager.getBans().save();
        ConfigManager.reload();
        return count;
    }

    private static String checkUsername(UUID uuid, String username) {
        String s = uuid.toString();
        String url = "https://api.mojang.com/user/profiles/" + s.replace("-", "") + "/names";
        try {
            String nameJson = IOUtils.toString((URL)new URL(url));
            JSONArray nameValue = (JSONArray)JSONValue.parseWithException((String)nameJson);
            String playerSlot = nameValue.get(nameValue.size() - 1).toString();
            JSONObject nameObject = (JSONObject)JSONValue.parseWithException((String)playerSlot);
            return nameObject.get((Object)"name").toString();
        }
        catch (Exception e) {
            return null;
        }
    }
}

