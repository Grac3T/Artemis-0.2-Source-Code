/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 */
package cc.ghast.artemis.v2.commands.gui.gui;

import cc.ghast.artemis.v2.commands.gui.controllers.ChecksController;
import cc.ghast.artemis.v2.utils.smartinvs.SmartInventory;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ChecksGUI {
    public static final SmartInventory inv = SmartInventory.builder().id("checklist").provider(new ChecksController()).size(3, 9).title((Object)ChatColor.AQUA + "Checks GUI").closeable(false).build();

    public static void openInventory(Player player) {
        inv.open(player);
    }
}

