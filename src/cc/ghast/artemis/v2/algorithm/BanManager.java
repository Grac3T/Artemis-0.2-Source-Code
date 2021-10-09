/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitTask
 */
package cc.ghast.artemis.v2.algorithm;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.algorithm.BanProcessor;
import cc.ghast.artemis.v2.algorithm.BanProfile;
import cc.ghast.artemis.v2.algorithm.BungeeBanProcessor;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.enums.Type;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.data.Violation;
import cc.ghast.artemis.v2.api.events.ArtemisBanEvent;
import cc.ghast.artemis.v2.api.manager.Manager;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.utils.configuration.Configuration;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class BanManager
extends Manager {
    private Deque<BanProfile> queue = new LinkedList<BanProfile>();

    @Override
    public void init() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask((Plugin)Artemis.INSTANCE.getPlugin(), () -> {
            if (this.queue.size() > 0) {
                this.cache(this.queue.poll());
            }
        }, 20L, 20L);
        Bukkit.getScheduler().scheduleAsyncRepeatingTask((Plugin)Artemis.INSTANCE.getPlugin(), () -> {
            ConfigManager.getBanwave().save();
            ConfigManager.getBans().save();
        }, 400L, 400L);
    }

    @Override
    public void disinit() {
        this.logAll();
    }

    public void addProfile(PlayerData data) {
        this.queue.add(new BanProfile((OfflinePlayer)data.getPlayer(), data.getUuid(), data.user.getViolations().size(), LocalDateTime.now(), data.getPlayer().getAddress().getHostString(), data.getPlayer().getName(), true, data.user.getViolations()));
    }

    private void cache(BanProfile data) {
        switch (ConfigManager.getSettings().getString("ban.type")) {
            case "autoban": {
                Configuration config = ConfigManager.getBans();
                config.set(data.getPlayer().getUniqueId() + ".date", LocalDateTime.now().toString());
                config.set(data.getPlayer().getUniqueId() + ".ip", data.getIp());
                config.set(data.getPlayer().getUniqueId() + ".name", data.getPlayer().getName());
                config.set(data.getPlayer().getUniqueId() + ".total-flags", data.getTotalFlags());
                config.set(data.getPlayer().getUniqueId() + ".active", true);
                data.getFlag().forEach((check, vl) -> {
                    config.set(data.getPlayer().getUniqueId() + ".flag." + check.getType().name() + check.getVar() + ".count", vl.getCount());
                    config.set(data.getPlayer().getUniqueId() + ".flag." + check.getType().name() + check.getVar() + ".timestamp", vl.getTimestamp());
                });
                Bukkit.getScheduler().runTask((Plugin)Artemis.INSTANCE.getPlugin(), () -> {
                    if (ConfigManager.getSettings().getBoolean("general.bungeecord")) {
                        BungeeBanProcessor.issueBan(data);
                    } else {
                        BanProcessor.issueBan(data);
                    }
                });
                Bukkit.getPluginManager().callEvent((Event)new ArtemisBanEvent(data.getPlayer()));
                return;
            }
            case "banwave": {
                Configuration config = ConfigManager.getBanwave();
                config.set(data.getPlayer().getUniqueId() + ".date", data.getTime().toString());
                config.set(data.getPlayer().getUniqueId() + ".ip", data.getIp());
                config.set(data.getPlayer().getUniqueId() + ".name", data.getUsername());
                config.set(data.getPlayer().getUniqueId() + ".total-flags", data.getTotalFlags());
                config.set(data.getPlayer().getUniqueId() + ".active", true);
                data.getFlag().forEach((check, vl) -> {
                    config.set(data.getPlayer().getUniqueId() + ".flag." + check.getType().name() + check.getVar() + ".count", vl.getCount());
                    config.set(data.getPlayer().getUniqueId() + ".flag." + check.getType().name() + check.getVar() + ".timestamp", vl.getTimestamp());
                });
                return;
            }
            case "none": {
                return;
            }
        }
    }

    public void logAll() {
        for (int i = 0; i < this.queue.size(); ++i) {
            this.cache(this.queue.poll());
        }
    }
}

