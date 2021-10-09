/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 */
package cc.ghast.artemis.v2.commands.gui.gui;

import cc.ghast.artemis.v2.commands.gui.controllers.CheckInfoController;
import cc.ghast.artemis.v2.utils.smartinvs.SmartInventory;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CheckInfoGUI {
    public static void openInventory(Player player, String check) {
        CheckInfoGUI.returnCheckGui(player, check).open(player);
    }

    public static SmartInventory returnCheckGui(Player player, String check) {
        return SmartInventory.builder().id(check).provider(new CheckInfoController(check)).size(3, 9).title((Object)ChatColor.AQUA + "Check info for: " + check).closeable(false).build();
    }
}

