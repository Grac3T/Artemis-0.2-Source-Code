/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.commands.artemis_sub;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.api.API;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.CheckManager;
import cc.ghast.artemis.v2.api.check.enums.Type;
import cc.ghast.artemis.v2.api.command.AbstractSubCommand;
import cc.ghast.artemis.v2.api.command.SubCommand;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.managers.PlayerDataManager;
import cc.ghast.artemis.v2.utils.chat.Chat;
import cc.ghast.artemis.v2.utils.hastebin.Hastebin;
import java.io.IOException;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SubCommand(name="output", permission="artemis.alert", aliases={""})
public class OutputSubCommand
extends AbstractSubCommand {
    @Override
    public void run(CommandSender executor, String ... args) {
        if (!(executor instanceof Player)) {
            return;
        }
        Player player = (Player)executor;
        PlayerData data = Artemis.INSTANCE.getApi().getPlayerDataManager().getData(player);
        if (args.length == 0) {
            player.sendMessage(Chat.translate("&7[&6&lDEBUG&7]&c Invalid arguments! Use /&6debug <check name> &c!"));
            return;
        }
        AbstractCheck abstractCheck = data.getCheckManager().getCheckByName(args[0]);
        if (abstractCheck == null) {
            this.returnInvalidCheck(player);
            return;
        }
        switch (args.length) {
            case 1: 
            case 2: {
                switch (args[1].toLowerCase()) {
                    case "toggle": {
                        if (data.isLogDebug(abstractCheck)) {
                            List<String> list = data.staff.getLog();
                            String[] payload = new String[list.size()];
                            for (int i = 0; i < list.size(); ++i) {
                                payload[i] = list.get(i);
                            }
                            try {
                                player.sendMessage(Chat.translate("&7[&6&lOUTPUT&7] &6Pasted hastebin at: " + Hastebin.paste(payload)));
                            }
                            catch (IOException e) {
                                player.sendMessage(Chat.translate("&7[&6&lOUTPUT&7] &6Error when pasting to Hastebin. Check console"));
                            }
                            data.staff.getLogDebug().remove(abstractCheck);
                            data.staff.getLog().clear();
                        } else {
                            player.sendMessage(Chat.translate("&7[&6&lDEBUG&7]&a Enabled &6recording!"));
                            data.staff.getLogDebug().add(abstractCheck);
                        }
                        return;
                    }
                    case "paste": {
                        if (data.isLogDebug(abstractCheck)) {
                            List<String> list = data.staff.getLog();
                            String[] payload = new String[list.size()];
                            for (int i = 0; i < list.size(); ++i) {
                                payload[i] = list.get(i);
                            }
                            try {
                                player.sendMessage(Chat.translate("&7[&6&lOUTPUT&7] &6Pasted hastebin at: " + Hastebin.paste(payload)));
                            }
                            catch (IOException e) {
                                player.sendMessage(Chat.translate("&7[&6&lOUTPUT&7] &6Error when pasting to Hastebin. Check console"));
                            }
                        } else {
                            player.sendMessage(Chat.translate("&7[&6&lDEBUG&7]&c Check is not in log debug!"));
                        }
                        return;
                    }
                    case "clear": {
                        if (data.isLogDebug(abstractCheck)) {
                            data.staff.getLog().clear();
                            player.sendMessage(Chat.translate("&7[&6&lOUTPUT&7] &aSuccesfully removed logs for " + abstractCheck.getType().name() + abstractCheck.getVar()));
                        } else {
                            player.sendMessage(Chat.translate("&7[&6&lDEBUG&7]&c Check is not in log debug!"));
                        }
                        return;
                    }
                }
            }
        }
    }

    private void returnInvalidCheck(Player player) {
        player.sendMessage(Chat.translate(ConfigManager.getSettings().getString("message.invalid-check")));
    }
}

