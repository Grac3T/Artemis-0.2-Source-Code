/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.plugin.PluginDescriptionFile
 */
package cc.ghast.artemis.v2.commands;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.api.command.AbstractCommand;
import cc.ghast.artemis.v2.api.command.AbstractSubCommand;
import cc.ghast.artemis.v2.api.command.Command;
import cc.ghast.artemis.v2.commands.artemis_sub.AlertsSubCommand;
import cc.ghast.artemis.v2.commands.artemis_sub.BanWaveSubCommand;
import cc.ghast.artemis.v2.commands.artemis_sub.BansSubCommand;
import cc.ghast.artemis.v2.commands.artemis_sub.CheckSubCommand;
import cc.ghast.artemis.v2.commands.artemis_sub.ChecksSubCommand;
import cc.ghast.artemis.v2.commands.artemis_sub.DebugSubCommand;
import cc.ghast.artemis.v2.commands.artemis_sub.GuiSubCommand;
import cc.ghast.artemis.v2.commands.artemis_sub.OutputSubCommand;
import cc.ghast.artemis.v2.commands.artemis_sub.PacketsSubCommand;
import cc.ghast.artemis.v2.commands.artemis_sub.ReportsSubCommand;
import cc.ghast.artemis.v2.commands.artemis_sub.RestoreSubCommand;
import cc.ghast.artemis.v2.commands.artemis_sub.VerboseSubCommand;
import cc.ghast.artemis.v2.utils.chat.Chat;
import java.util.Arrays;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

@Command(name="artemis", permission="artemis.staff", aliases={""})
public class MainCommand
extends AbstractCommand {
    @Override
    public void run(CommandSender executor, String ... args) {
        String[] message;
        for (String s : message = new String[]{"&7&m-----------------------------------", "&b&lArtemis&7 made by &bGhast &7&&b 7x6", "&7Version: &c" + Artemis.INSTANCE.getPlugin().getDescription().getVersion(), "&7&m-----------------------------------", "&7/&bArtemis&7 -> &bMain ji command", "&7/&bArtemis verbose &7 -> &bToggle verboses", "&7/&bArtemis alerts &7 -> &bToggle alerts", "&7/&bArtemis checks &7 -> &bSee your current checks", "&7/&bArtemis forcecheck <player> &7 -> &bForcefully start the algorithm", "&7&m-----------------------------------"}) {
            executor.sendMessage(Chat.translate(s));
        }
    }

    @Override
    public List<AbstractSubCommand> initSubCommands() {
        return Arrays.asList(new VerboseSubCommand(), new AlertsSubCommand(), new ChecksSubCommand(), new OutputSubCommand(), new CheckSubCommand(), new DebugSubCommand(), new BansSubCommand(), new RestoreSubCommand(), new PacketsSubCommand(), new BanWaveSubCommand(), new ReportsSubCommand(), new GuiSubCommand());
    }
}

