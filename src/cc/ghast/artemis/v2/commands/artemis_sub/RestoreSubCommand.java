/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.commands.artemis_sub;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.api.API;
import cc.ghast.artemis.v2.api.command.AbstractSubCommand;
import cc.ghast.artemis.v2.api.command.SubCommand;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.managers.PlayerDataManager;
import cc.ghast.artemis.v2.utils.configuration.Configuration;
import java.util.Set;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

@SubCommand(name="restore", permission="artemis.full", aliases={""})
public class RestoreSubCommand
extends AbstractSubCommand {
    @Override
    public void run(CommandSender executor, String ... args) {
        if (!(executor instanceof Player)) {
            return;
        }
        Player player = (Player)executor;
        PlayerData data = Artemis.INSTANCE.getApi().getPlayerDataManager().getData(player);
        switch (args.length) {
            case 1: {
                switch (args[0].toLowerCase()) {
                    case "checks_bannable": {
                        Configuration config = ConfigManager.getChecks();
                        Set<String> checks = config.getConfig().getConfigurationSection("").getKeys(false);
                        for (String s : checks) {
                            Set<String> ts = config.getConfig().getConfigurationSection(s).getKeys(false);
                            for (String t : ts) {
                                for (String var : config.getConfig().getConfigurationSection(t).getKeys(false)) {
                                    if (config.getBoolean(t + "." + var + ".bannable")) continue;
                                    config.set(t + "." + var + ".bannable", true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

