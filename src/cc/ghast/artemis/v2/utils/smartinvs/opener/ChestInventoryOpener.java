/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.InventoryView
 */
package cc.ghast.artemis.v2.utils.smartinvs.opener;

import cc.ghast.artemis.v2.utils.smartinvs.InventoryManager;
import cc.ghast.artemis.v2.utils.smartinvs.SmartInventory;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryContents;
import cc.ghast.artemis.v2.utils.smartinvs.opener.InventoryOpener;
import com.google.common.base.Preconditions;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;

public class ChestInventoryOpener
implements InventoryOpener {
    @Override
    public Inventory open(SmartInventory inv, Player player) {
        Preconditions.checkArgument((boolean)(inv.getColumns() == 9), (String)"The column count for the chest inventory must be 9, found: %s.", (Object[])new Object[]{inv.getColumns()});
        Preconditions.checkArgument((boolean)(inv.getRows() >= 1 && inv.getRows() <= 6), (String)"The row count for the chest inventory must be between 1 and 6, found: %s", (Object[])new Object[]{inv.getRows()});
        InventoryManager manager = inv.getManager();
        Inventory handle = Bukkit.createInventory((InventoryHolder)player, (int)(inv.getRows() * inv.getColumns()), (String)inv.getTitle());
        this.fill(handle, manager.getContents(player).get());
        player.openInventory(handle);
        return handle;
    }

    @Override
    public boolean supports(InventoryType type) {
        return type == InventoryType.CHEST || type == InventoryType.ENDER_CHEST;
    }
}

