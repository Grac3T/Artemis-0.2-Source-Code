/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryClickEvent
 */
package cc.ghast.artemis.v2.checks.movement.inventory;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

@Check
public class InventoryWalkA
extends AbstractCheck {
    @Override
    public void handle(PlayerData data, InventoryClickEvent e) {
        Player player;
        if (e.getWhoClicked() instanceof Player && (player = (Player)e.getWhoClicked()).isSprinting()) {
            this.log(data, new String[0]);
        }
    }
}

