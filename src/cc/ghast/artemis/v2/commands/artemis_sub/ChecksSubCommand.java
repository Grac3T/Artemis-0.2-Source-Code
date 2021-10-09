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
import cc.ghast.artemis.v2.api.check.enums.Category;
import cc.ghast.artemis.v2.api.check.enums.Type;
import cc.ghast.artemis.v2.api.command.AbstractSubCommand;
import cc.ghast.artemis.v2.api.command.SubCommand;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.managers.PlayerDataManager;
import cc.ghast.artemis.v2.utils.chat.Chat;
import java.util.List;
import java.util.function.Consumer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SubCommand(name="checks", permission="artemis.full", aliases={""})
public class ChecksSubCommand
extends AbstractSubCommand {
    @Override
    public void run(CommandSender executor, String ... args) {
        if (!(executor instanceof Player)) {
            return;
        }
        Player player = (Player)executor;
        PlayerData data = Artemis.INSTANCE.getApi().getPlayerDataManager().getData(player);
        data.getCheckManager().getAbstractChecks().forEach(check -> player.sendMessage(ConfigManager.getChecks().getBoolean(check.getType().getCategory().name().toLowerCase() + "." + check.getType().name().toLowerCase() + "." + check.getVar() + ".enabled") ? Chat.translate("&7&l-> &a" + check.getType().name() + " &7(&a" + check.getVar() + "&7)") : Chat.translate("&7&l-> &c" + check.getType().name() + " &7(&c" + check.getVar() + "&7)")));
        player.sendMessage(Chat.translate("&bThere is currently &a" + data.getCheckManager().getAbstractChecks().size() + "&b" + " checks!"));
    }
}

