/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 */
package cc.ghast.artemis.v2.api.command;

import cc.ghast.artemis.v2.api.command.AbstractCommand;
import cc.ghast.artemis.v2.api.command.AbstractSubCommand;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.utils.chat.Chat;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandListener
implements CommandExecutor {
    private final AbstractCommand abstractCommand;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        switch (args.length) {
            case 0: {
                if (sender.hasPermission(this.abstractCommand.getPermission())) {
                    this.abstractCommand.run(sender, new String[0]);
                } else {
                    sender.sendMessage(Chat.translate(ConfigManager.getSettings().getString("message.no-permission")));
                }
                return true;
            }
            case 1: {
                AbstractSubCommand sub = this.abstractCommand.getAbstractSubCommands().stream().filter(sls -> sls.getName().equalsIgnoreCase(args[0])).findFirst().orElse(null);
                if (sub != null) {
                    if (sender.hasPermission(sub.getPermission())) {
                        sub.run(sender, new String[0]);
                    } else {
                        sender.sendMessage(Chat.translate(ConfigManager.getSettings().getString("message.no-permission")));
                    }
                } else if (sender.hasPermission(this.abstractCommand.getPermission())) {
                    this.abstractCommand.run(sender, args);
                } else {
                    sender.sendMessage(Chat.translate(ConfigManager.getSettings().getString("message.no-permission")));
                }
                return true;
            }
        }
        AbstractSubCommand sub = this.abstractCommand.getAbstractSubCommands().stream().filter(sls -> sls.getName().equalsIgnoreCase(args[0])).findFirst().orElse(null);
        if (sub != null) {
            if (sender.hasPermission(sub.getPermission())) {
                String[] vars = new String[args.length - 1];
                for (int i = 1; i < args.length; ++i) {
                    vars[i - 1] = args[i];
                }
                sub.run(sender, vars);
            } else {
                sender.sendMessage(Chat.translate(ConfigManager.getSettings().getString("message.no-permission")));
            }
        } else if (sender.hasPermission(this.abstractCommand.getPermission())) {
            this.abstractCommand.run(sender, new String[0]);
        } else {
            sender.sendMessage(Chat.translate(ConfigManager.getSettings().getString("message.no-permission")));
        }
        return false;
    }

    public CommandListener(AbstractCommand abstractCommand) {
        this.abstractCommand = abstractCommand;
    }
}

