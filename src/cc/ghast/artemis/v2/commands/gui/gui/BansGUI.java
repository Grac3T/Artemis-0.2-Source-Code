/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 */
package cc.ghast.artemis.v2.commands.gui.gui;

import cc.ghast.artemis.v2.commands.gui.controllers.BansController;
import cc.ghast.artemis.v2.utils.smartinvs.SmartInventory;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BansGUI {
    public static final SmartInventory inv = SmartInventory.builder().id("bans").provider(new BansController()).size(4, 9).title((Object)ChatColor.AQUA + "Bans list").build();

    public static void openInventory(Player player) {
        inv.open(player);
    }
}

