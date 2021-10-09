/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Server
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.plugin.messaging.Messenger
 */
package cc.ghast.artemis.v2.api;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.algorithm.BanManager;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.manager.Manager;
import cc.ghast.artemis.v2.api.packet.TinyProtocolHandler;
import cc.ghast.artemis.v2.lag.LagCore;
import cc.ghast.artemis.v2.managers.CommandManager;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.managers.DependencyManager;
import cc.ghast.artemis.v2.managers.PlayerDataManager;
import cc.ghast.artemis.v2.managers.ThemeManager;
import cc.ghast.artemis.v2.utils.chat.Chat;
import cc.ghast.artemis.v2.utils.smartinvs.SmartInvsAPI;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

public class API {
    private final PlayerDataManager playerDataManager;
    private final CommandManager commandManager;
    private final TinyProtocolHandler tinyProtocolHandler;
    private final ConfigManager configManager;
    private final LagCore lagCore;
    private final SmartInvsAPI smartInvsAPI;
    private final BanManager banManager;
    private final DependencyManager dependencyManager;
    private final ThemeManager themeManager;

    public API(ArtemisPlugin plugin) {
        Chat.sendLogo();
        this.configManager = new ConfigManager();
        this.themeManager = new ThemeManager(plugin);
        this.dependencyManager = new DependencyManager();
        this.playerDataManager = new PlayerDataManager();
        this.lagCore = new LagCore(plugin);
        this.smartInvsAPI = new SmartInvsAPI();
        this.commandManager = new CommandManager(plugin);
        this.banManager = new BanManager();
        this.initManagers();
        this.tinyProtocolHandler = new TinyProtocolHandler(plugin);
        if (ConfigManager.getSettings().getBoolean("general.bungeecord")) {
            Artemis.INSTANCE.getPlugin().getServer().getMessenger().registerOutgoingPluginChannel((Plugin)plugin, "BungeeCord");
        }
    }

    public void disinit() {
        this.disinitManagers();
    }

    private synchronized void disinitManagers() {
        Arrays.asList(this.themeManager, this.dependencyManager, this.configManager, this.playerDataManager, this.smartInvsAPI, this.commandManager, this.banManager).forEach(manager -> {
            manager.disinit();
            Chat.sendConsoleMessage("&b&lARTEMIS&7&l -> &cDisabled &b" + manager.getClass().getSimpleName() + "&a!");
        });
    }

    private synchronized void initManagers() {
        Arrays.asList(this.dependencyManager, this.configManager, this.themeManager, this.playerDataManager, this.smartInvsAPI, this.commandManager, this.banManager).forEach(manager -> {
            manager.init();
            Chat.sendConsoleMessage("&b&lARTEMIS&7&l -> &aDisabled &b" + manager.getClass().getSimpleName() + "&a!");
        });
    }

    public void injectCheck(AbstractCheck check) {
        this.playerDataManager.getToInject().add(check.getClass());
    }

    public PlayerDataManager getPlayerDataManager() {
        return this.playerDataManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public TinyProtocolHandler getTinyProtocolHandler() {
        return this.tinyProtocolHandler;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public LagCore getLagCore() {
        return this.lagCore;
    }

    public SmartInvsAPI getSmartInvsAPI() {
        return this.smartInvsAPI;
    }

    public BanManager getBanManager() {
        return this.banManager;
    }

    public DependencyManager getDependencyManager() {
        return this.dependencyManager;
    }

    public ThemeManager getThemeManager() {
        return this.themeManager;
    }
}

