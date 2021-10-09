/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.commands.artemis_sub;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.api.API;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.enums.Type;
import cc.ghast.artemis.v2.api.command.AbstractSubCommand;
import cc.ghast.artemis.v2.api.command.SubCommand;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.data.Violation;
import cc.ghast.artemis.v2.managers.PlayerDataManager;
import cc.ghast.artemis.v2.utils.chat.Chat;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SubCommand(name="reports", permission="artemis.full", aliases={""})
public class ReportsSubCommand
extends AbstractSubCommand {
    @Override
    public void run(CommandSender executor, String ... args) {
        if (!(executor instanceof Player)) {
            return;
        }
        Player player = (Player)executor;
        PlayerData data = Artemis.INSTANCE.getApi().getPlayerDataManager().getData(player);
        int count = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerData targetData = Artemis.INSTANCE.getApi().getPlayerDataManager().getData(player);
            if (targetData.user.getViolations().size() <= 0) continue;
            StringBuilder sb = new StringBuilder("&b - " + p.getName() + " &8&l-> " + "&b");
            for (Map.Entry<AbstractCheck, Violation> entry : targetData.user.getViolations().entrySet()) {
                sb.append("&b" + entry.getKey().getType().name() + " &8(" + "&b" + entry.getKey().getVar() + "&8) &8[" + "&b" + entry.getValue().getCount() + "&8]" + "&7" + "&7;  ");
            }
            player.sendMessage(Chat.translate(sb.toString()));
            ++count;
        }
        player.sendMessage(Chat.translate("&bCurrently &7" + count + "&b" + " players are suspected of cheating on the server"));
    }
}

