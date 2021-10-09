/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.commands.artemis_sub;

import cc.ghast.artemis.v2.api.command.AbstractSubCommand;
import cc.ghast.artemis.v2.api.command.SubCommand;
import cc.ghast.artemis.v2.commands.gui.gui.MainGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SubCommand(name="gui", permission="artemis.full", aliases={""})
public class GuiSubCommand
extends AbstractSubCommand {
    @Override
    public void run(CommandSender executor, String ... args) {
        if (!(executor instanceof Player)) {
            return;
        }
        Player player = (Player)executor;
        MainGUI.openInventory(player);
    }
}

