/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.commands.artemis_sub;

import cc.ghast.artemis.v2.algorithm.BanWaveProcessor;
import cc.ghast.artemis.v2.api.command.AbstractSubCommand;
import cc.ghast.artemis.v2.api.command.SubCommand;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.utils.chat.Chat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SubCommand(name="banwave", permission="artemis.full", aliases={""})
public class BanWaveSubCommand
extends AbstractSubCommand {
    @Override
    public void run(CommandSender executor, String ... args) {
        if (!(executor instanceof Player)) {
            return;
        }
        Player player = (Player)executor;
        player.sendMessage(Chat.translate(ConfigManager.getSettings().getString("general.prefix") + " &bBan Wave&7 succesfully executed&f:&b " + BanWaveProcessor.executeBanWave() + "&7 players banned"));
    }
}

