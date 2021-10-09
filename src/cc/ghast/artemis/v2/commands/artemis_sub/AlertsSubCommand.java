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
import cc.ghast.artemis.v2.api.command.AbstractSubCommand;
import cc.ghast.artemis.v2.api.command.SubCommand;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.data.StaffEnums;
import cc.ghast.artemis.v2.managers.PlayerDataManager;
import cc.ghast.artemis.v2.utils.chat.Chat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SubCommand(name="alerts", permission="artemis.alert", aliases={""})
public class AlertsSubCommand
extends AbstractSubCommand {
    @Override
    public void run(CommandSender executor, String ... args) {
        if (!(executor instanceof Player)) {
            return;
        }
        Player player = (Player)executor;
        PlayerData data = Artemis.INSTANCE.getApi().getPlayerDataManager().getData(player);
        data.staff.setStaffAlert(data.staff.getStaffAlert().equals((Object)StaffEnums.StaffAlerts.ALERTS) ? StaffEnums.StaffAlerts.NONE : StaffEnums.StaffAlerts.ALERTS);
        player.sendMessage(Chat.translate(data.staff.getStaffAlert().equals((Object)StaffEnums.StaffAlerts.ALERTS) ? "&cToggled alerts on" : "&cToggled alerts off"));
    }
}

