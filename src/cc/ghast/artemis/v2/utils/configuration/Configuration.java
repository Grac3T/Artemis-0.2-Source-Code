/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.configuration.InvalidConfigurationException
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.configuration.file.YamlConfigurationOptions
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.java.JavaPlugin
 */
package cc.ghast.artemis.v2.utils.configuration;

import cc.ghast.artemis.v2.utils.chat.Chat;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Configuration {
    private JavaPlugin plugin;
    private String name;
    private File file;
    private YamlConfiguration config;

    public Configuration(String name, JavaPlugin plugin) {
        this.plugin = plugin;
        this.name = name;
        this.file = new File(plugin.getDataFolder(), name);
        if (!this.file.exists()) {
            this.file.getParentFile().mkdir();
            plugin.saveResource(name, true);
        }
        this.config = new YamlConfiguration();
        try {
            this.config.load(this.file);
        }
        catch (IOException | InvalidConfigurationException ex) {
            Chat.sendConsoleMessage("&cError loading configuration file " + name);
            ex.printStackTrace();
        }
    }

    public void save() {
        try {
            this.config.save(this.file);
        }
        catch (Exception e) {
            Chat.sendConsoleMessage("&cError saving config file " + this.name);
        }
    }

    public String getString(String path) {
        return this.getConfig().getString(path);
    }

    public int getInt(String path) {
        return this.getConfig().getInt(path);
    }

    public boolean getBoolean(String path) {
        return this.getConfig().getBoolean(path);
    }

    public List<String> getStringList(String path) {
        return this.getConfig().getStringList(path);
    }

    public ItemStack getItemStack(String path) {
        return this.getConfig().getItemStack(path);
    }

    public double getDouble(String path) {
        return this.getConfig().getDouble(path);
    }

    public Long getLong(String path) {
        return this.getConfig().getLong(path);
    }

    public Object get(String path) {
        return this.getConfig().get(path);
    }

    public void set(String path, Object value) {
        this.getConfig().set(path, value);
        this.save();
    }

    public YamlConfigurationOptions getOptions() {
        return this.getConfig().options();
    }

    public void setLocation(String path, Location location) {
        this.getConfig().set(path + ".X", (Object)location.getX());
        this.getConfig().set(path + ".Y", (Object)location.getY());
        this.getConfig().set(path + ".Z", (Object)location.getZ());
        this.getConfig().set(path + ".WORLD", (Object)location.getWorld().getName());
        this.getConfig().set(path + ".YAW", (Object)Float.valueOf(location.getYaw()));
        this.getConfig().set(path + ".PITCH", (Object)Float.valueOf(location.getPitch()));
        this.save();
    }

    public Location getLocation(String path) {
        return new Location(Bukkit.getWorld((String)this.getConfig().getString(path + ".WORLD")), this.getConfig().getDouble(path + ".X"), this.getConfig().getDouble(path + ".Y"), this.getConfig().getDouble(path + ".Z"), (float)this.getConfig().getLong(path + ".YAW"), (float)this.getConfig().getLong(path + ".PITCH"));
    }

    public YamlConfiguration getConfig() {
        return this.config;
    }
}

