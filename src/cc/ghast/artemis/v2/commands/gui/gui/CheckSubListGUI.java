/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 */
package cc.ghast.artemis.v2.commands.gui.gui;

import cc.ghast.artemis.v2.commands.gui.controllers.CheckSubListController;
import cc.ghast.artemis.v2.utils.smartinvs.SmartInventory;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CheckSubListGUI {
    public static void openInventory(Player player, String category, String type) {
        CheckSubListGUI.openInventory(player, 0, category, type);
    }

    public static void openInventory(Player player, int page, String category, String type) {
        CheckSubListGUI.returnCheckGui(player, category, type).open(player, page);
    }

    public static SmartInventory returnCheckGui(Player player, String category, String type) {
        return SmartInventory.builder().id(category + type).provider(new CheckSubListController(category, type)).size(4, 9).title((Object)ChatColor.AQUA + "Check info for: " + category + ":" + type).closeable(false).build();
    }
}

