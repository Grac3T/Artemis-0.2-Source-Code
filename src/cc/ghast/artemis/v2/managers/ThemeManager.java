/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.java.JavaPlugin
 */
package cc.ghast.artemis.v2.managers;

import cc.ghast.artemis.v2.api.manager.Manager;
import cc.ghast.artemis.v2.api.theme.AbstractTheme;
import cc.ghast.artemis.v2.api.theme.ThemeNotFoundException;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.utils.chat.Chat;
import cc.ghast.artemis.v2.utils.configuration.Configuration;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.bukkit.plugin.java.JavaPlugin;

public class ThemeManager
extends Manager {
    private final JavaPlugin plugin;
    private Deque<AbstractTheme> themes = new LinkedList<AbstractTheme>();
    private AbstractTheme currentTheme;

    public ThemeManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        File dir = Arrays.stream(Objects.requireNonNull(this.plugin.getDataFolder().listFiles())).filter(file -> file.getName().equalsIgnoreCase("themes")).findFirst().orElse(null);
        if (dir == null) {
            Chat.sendConsoleError("INVALID_DIRECTORY: FAILED TO LOAD THEMES | LOADING NEW THEME");
            new Configuration("themes/artemis.yml", this.plugin);
        }
        for (File file2 : Objects.requireNonNull(dir.listFiles())) {
            AbstractTheme theme = this.resolveTheme(file2.getName(), new Configuration("themes/" + file2.getName(), this.plugin));
            this.themes.add(theme);
            Chat.sendConsoleMessage("&7[&b&lArtemis&7]&a Successfully loaded theme " + file2.getName());
        }
        this.setTheme(ConfigManager.getSettings().getString("general.theme"));
    }

    @Override
    public void disinit() {
        ConfigManager.getSettings().set("general.theme", this.currentTheme.getId());
    }

    public void setTheme(String name) {
        this.currentTheme = this.themes.stream().filter(t -> t.getId().equalsIgnoreCase(name)).findFirst().orElseThrow(() -> new ThemeNotFoundException(name));
        this.themes.remove(this.currentTheme);
        this.themes.addFirst(this.currentTheme);
    }

    public void setNextTheme() {
        this.currentTheme = this.themes.pollFirst();
        this.themes.addLast(this.currentTheme);
    }

    public void setPreviousTheme() {
        this.currentTheme = this.themes.pollLast();
        this.themes.addFirst(this.currentTheme);
    }

    public List<AbstractTheme> getThemes() {
        return new ArrayList<AbstractTheme>(this.themes);
    }

    public Deque<AbstractTheme> getThemeQueue() {
        return this.themes;
    }

    private AbstractTheme resolveTheme(String id, Configuration config) {
        String colorMain = Chat.translate(config.getString("colors.main"));
        String colorSecondary = Chat.translate(config.getString("colors.secondary"));
        String colorBrackets = Chat.translate(config.getString("colors.brackets"));
        String prefix = this.translateValues(Chat.translate(config.getString("general.prefix")), colorMain, colorSecondary, colorBrackets, "");
        String violationMsg = this.translateValues(Chat.translate(config.getString("messages.violation")), colorMain, colorSecondary, colorBrackets, prefix);
        String verboseMsg = this.translateValues(Chat.translate(config.getString("messages.verbose")), colorMain, colorSecondary, colorBrackets, prefix);
        String banMsg = this.translateValues(Chat.translate(config.getString("messages.ban")), colorMain, colorSecondary, colorBrackets, prefix);
        List<String> helpMsg = Chat.translate(config.getStringList("messages.help-message"));
        return new AbstractTheme(id, colorMain, colorSecondary, colorBrackets, prefix, violationMsg, verboseMsg, banMsg, helpMsg);
    }

    private String translateValues(String s, String main, String secondary, String brackets, String prefix) {
        return s.replace("%main%", main).replace("%secondary%", secondary).replace("%brackets%", brackets).replace("%prefix%", prefix);
    }

    public AbstractTheme getCurrentTheme() {
        return this.currentTheme;
    }
}

