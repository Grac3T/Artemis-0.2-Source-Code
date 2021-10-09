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
import cc.ghast.artemis.v2.api.command.AbstractSubCommand;
import cc.ghast.artemis.v2.api.command.SubCommand;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.managers.PlayerDataManager;
import cc.ghast.artemis.v2.utils.chat.Chat;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SubCommand(name="debug", permission="artemis.alert", aliases={""})
public class DebugSubCommand
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
        if (data.isDebug(abstractCheck)) {
            data.staff.getDebug().remove(abstractCheck);
            data.staff.getLog().clear();
        } else {
            data.staff.getDebug().add(abstractCheck);
        }
        player.sendMessage(Chat.translate(data.isDebug(abstractCheck) ? "&7[&6&lDEBUG&7]&a Enabled &6debug!" : "&7[&6&lDEBUG&7]&c Disabled &6debug!"));
    }

    private void returnInvalidCheck(Player player) {
        player.sendMessage(Chat.translate(ConfigManager.getSettings().getString("message.invalid-check")));
    }
}

