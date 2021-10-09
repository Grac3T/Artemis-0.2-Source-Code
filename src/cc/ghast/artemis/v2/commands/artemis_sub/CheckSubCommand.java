/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.commands.artemis_sub;

import cc.ghast.artemis.v2.algorithm.BanProcessor;
import cc.ghast.artemis.v2.api.check.enums.Category;
import cc.ghast.artemis.v2.api.check.enums.Type;
import cc.ghast.artemis.v2.api.command.AbstractSubCommand;
import cc.ghast.artemis.v2.api.command.SubCommand;
import cc.ghast.artemis.v2.commands.gui.gui.CheckInfoGUI;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.utils.chat.Chat;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SubCommand(name="check", permission="artemisartemis.admin.check", aliases={""})
public class CheckSubCommand
extends AbstractSubCommand {
    @Override
    public void run(CommandSender executor, String ... args) {
        Type checkType = Arrays.stream(Type.values()).filter(type -> args[0].toUpperCase().contains(type.name())).findFirst().orElse(null);
        if (checkType == null) {
            executor.sendMessage(Chat.translate(ConfigManager.getSettings().getString("general.prefix") + " &cCheck not found! "));
            return;
        }
        String var = args[0].toUpperCase().replace(checkType.name(), "");
        switch (args.length) {
            case 1: {
                boolean enabled = ConfigManager.getChecks().getBoolean(checkType.getCategory().name().toLowerCase() + "." + checkType.name().toLowerCase() + "." + var + ".enabled");
                int maxVls = ConfigManager.getChecks().getInt(checkType.getCategory().name().toLowerCase() + "." + checkType.name().toLowerCase() + "." + var + ".max-vls");
                String[] message = new String[]{Chat.spacer(), Chat.translate("&bCheck type&7: &b" + checkType.name()), Chat.translate("&bCheck variable&7: &b" + var), Chat.translate("&bMax violations&7: &b" + maxVls), Chat.translate("&bStatus&7: " + (enabled ? "&a&lEnabled" : "&c&lDisabled")), Chat.spacer()};
                executor.sendMessage(message);
                return;
            }
            case 2: {
                switch (args[1].toLowerCase()) {
                    case "gui": {
                        if (!(executor instanceof Player)) {
                            return;
                        }
                        Player player = (Player)executor;
                        CheckInfoGUI.openInventory(player, checkType.name() + var);
                        return;
                    }
                    case "toggle": {
                        boolean enabled = ConfigManager.getChecks().getBoolean(checkType.getCategory().name().toLowerCase() + "." + checkType.name().toLowerCase() + "." + var + ".enabled");
                        ConfigManager.getChecks().set(checkType.getCategory().name().toLowerCase() + "." + checkType.name().toLowerCase() + "." + var + ".enabled", !enabled);
                        ConfigManager.getChecks().save();
                        ConfigManager.reload();
                        executor.sendMessage(Chat.translate(ConfigManager.getSettings().getString("general.prefix") + "&b" + " toggled check " + (!enabled ? "&a&lEnabled" : "&c&lDisabled")));
                        return;
                    }
                    case "restore": {
                        ConfigManager.getChecks().set(checkType.getCategory().name().toLowerCase() + "." + checkType.name().toLowerCase() + "." + var + ".enabled", true);
                        ConfigManager.getChecks().set(checkType.getCategory().name().toLowerCase() + "." + checkType.name().toLowerCase() + "." + var + ".max-vls", 15);
                        ConfigManager.getChecks().save();
                        ConfigManager.reload();
                        executor.sendMessage(Chat.translate(ConfigManager.getSettings().getString("general.prefix") + "&b" + " Restored base configuration from cloud"));
                        return;
                    }
                    case "purge": {
                        BanProcessor.revokeBans(checkType.name() + var);
                        executor.sendMessage(Chat.translate(ConfigManager.getSettings().getString("general.prefix") + "&b" + " Revoked all bans from check &a" + checkType.name() + var));
                        return;
                    }
                }
                return;
            }
            case 4: {
                switch (args[1].toLowerCase()) {
                    case "set": {
                        switch (args[2].toLowerCase()) {
                            case "maxvls": {
                                int vls = Integer.parseInt(args[3]);
                                ConfigManager.getChecks().set(checkType.getCategory().name().toLowerCase() + "." + checkType.name().toLowerCase() + "." + var + ".max-vls", vls);
                                ConfigManager.getChecks().save();
                                ConfigManager.reload();
                                executor.sendMessage(Chat.translate(ConfigManager.getSettings().getString("general.prefix") + "&b" + " Set max vls to check " + checkType.name() + var + " to " + vls));
                            }
                        }
                    }
                }
            }
        }
    }

    public void sendCheckNotFound() {
    }
}

