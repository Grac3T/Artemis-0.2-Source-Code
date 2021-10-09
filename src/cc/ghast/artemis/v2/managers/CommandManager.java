/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.plugin.java.JavaPlugin
 */
package cc.ghast.artemis.v2.managers;

import cc.ghast.artemis.v2.api.command.AbstractCommand;
import cc.ghast.artemis.v2.api.command.AbstractSubCommand;
import cc.ghast.artemis.v2.api.command.CommandListener;
import cc.ghast.artemis.v2.api.manager.Manager;
import cc.ghast.artemis.v2.commands.MainCommand;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandManager
extends Manager {
    private JavaPlugin plugin;

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        Arrays.asList(new MainCommand()).forEach(abstractCommand -> {
            this.plugin.getCommand(abstractCommand.getName()).setExecutor((CommandExecutor)new CommandListener((AbstractCommand)abstractCommand));
            abstractCommand.setAbstractSubCommands(abstractCommand.initSubCommands());
        });
    }

    @Override
    public void disinit() {
    }
}

