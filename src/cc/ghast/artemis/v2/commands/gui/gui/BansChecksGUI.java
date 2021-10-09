/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 */
package cc.ghast.artemis.v2.commands.gui.gui;

import cc.ghast.artemis.v2.commands.gui.controllers.BansChecksController;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.utils.smartinvs.SmartInventory;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BansChecksGUI {
    public static void openInventory(Player player, String uuid) {
        BansChecksGUI.returnCheckGui(uuid).open(player);
    }

    public static SmartInventory returnCheckGui(String uuid) {
        return SmartInventory.builder().id(uuid).provider(new BansChecksController(uuid)).size(4, 9).title((Object)ChatColor.AQUA + "Ban info for: " + ConfigManager.getBans().getString(uuid + ".name")).build();
    }
}

