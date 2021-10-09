/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.java.JavaPlugin
 */
package cc.ghast.artemis.v2.managers;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.api.manager.Manager;
import cc.ghast.artemis.v2.utils.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager
extends Manager {
    private static Configuration settings;
    private static Configuration checks;
    private static Configuration bans;
    private static Configuration banwave;

    @Override
    public void init() {
        settings = new Configuration("settings.yml", Artemis.INSTANCE.getPlugin());
        checks = new Configuration("checks.yml", Artemis.INSTANCE.getPlugin());
        bans = new Configuration("bans.yml", Artemis.INSTANCE.getPlugin());
        banwave = new Configuration("banwave.yml", Artemis.INSTANCE.getPlugin());
    }

    @Override
    public void disinit() {
    }

    public static void reload() {
        settings = new Configuration("settings.yml", Artemis.INSTANCE.getPlugin());
        checks = new Configuration("checks.yml", Artemis.INSTANCE.getPlugin());
        bans = new Configuration("bans.yml", Artemis.INSTANCE.getPlugin());
        banwave = new Configuration("banwave.yml", Artemis.INSTANCE.getPlugin());
    }

    public static Configuration getSettings() {
        return settings;
    }

    public static Configuration getChecks() {
        return checks;
    }

    public static Configuration getBans() {
        return bans;
    }

    public static Configuration getBanwave() {
        return banwave;
    }
}

