/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 */
package cc.ghast.artemis.v2.utils.smartinvs.opener;

import cc.ghast.artemis.v2.utils.smartinvs.ClickableItem;
import cc.ghast.artemis.v2.utils.smartinvs.SmartInventory;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryContents;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface InventoryOpener {
    public Inventory open(SmartInventory var1, Player var2);

    public boolean supports(InventoryType var1);

    default public void fill(Inventory handle, InventoryContents contents) {
        ClickableItem[][] items = contents.all();
        for (int row = 0; row < items.length; ++row) {
            for (int column = 0; column < items[row].length; ++column) {
                if (items[row][column] == null) continue;
                handle.setItem(9 * row + column, items[row][column].getItem());
            }
        }
    }
}

