/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.IOUtils
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.json.simple.JSONArray
 *  org.json.simple.JSONObject
 *  org.json.simple.JSONValue
 */
package cc.ghast.artemis.v2.commands.artemis_sub;

import cc.ghast.artemis.v2.api.command.AbstractSubCommand;
import cc.ghast.artemis.v2.api.command.SubCommand;
import cc.ghast.artemis.v2.commands.gui.gui.BansGUI;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.utils.chat.Chat;
import cc.ghast.artemis.v2.utils.configuration.Configuration;
import java.net.URL;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@SubCommand(name="bans", permission="artemis.full", aliases={""})
public class BansSubCommand
extends AbstractSubCommand {
    @Override
    public void run(CommandSender executor, String ... args) {
        switch (args.length) {
            case 1: {
                switch (args[0].toLowerCase()) {
                    case "restore": {
                        executor.sendMessage(Chat.translate("&b&bArtemis &7>> &aStarting restore!"));
                        Configuration config = ConfigManager.getBans();
                        Set uuids = config.getConfig().getConfigurationSection("").getKeys(false);
                        for (Object s : uuids) {
                            if (config.getString(s + ".name") != null) continue;
                            String url = "https://api.mojang.com/user/profiles/" + s.toString().replace("-", "") + "/names";
                            try {
                                String nameJson = IOUtils.toString((URL)new URL(url));
                                JSONArray nameValue = (JSONArray)JSONValue.parseWithException((String)nameJson);
                                String playerSlot = nameValue.get(nameValue.size() - 1).toString();
                                JSONObject nameObject = (JSONObject)JSONValue.parseWithException((String)playerSlot);
                                String name = nameObject.get((Object)"name").toString();
                                config.set(s + ".name", name);
                                executor.sendMessage(Chat.translate("&b&bArtemis &7>> &aRestored player&f " + name + " from UUID &f" + s));
                            }
                            catch (Exception e) {
                                executor.sendMessage(Chat.translate("&b&bArtemis &7>> &cFailed to restore UUID&f " + s));
                            }
                        }
                        config.save();
                        ConfigManager.reload();
                        return;
                    }
                }
                return;
            }
        }
        if (!(executor instanceof Player)) {
            return;
        }
        Player player = (Player)executor;
        BansGUI.openInventory(player);
    }
}

