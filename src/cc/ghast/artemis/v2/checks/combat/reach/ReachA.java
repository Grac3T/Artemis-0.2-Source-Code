/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 */
package cc.ghast.artemis.v2.checks.combat.reach;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.utils.location.Position;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

@Check
public class ReachA
extends AbstractCheck {
    @Override
    public void handle(PlayerData data, BlockBreakEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }
        Block block = e.getBlock();
        if (!block.getType().isSolid()) {
            return;
        }
        double dis = this.calcDistance(data.movement.getLastLocation().getBukkitLocation(), block.getLocation());
        if (dis >= 5.0) {
            this.log(data, "Broke block too far " + dis);
        }
    }

    @Override
    public void handle(PlayerData data, BlockPlaceEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }
        Block block = e.getBlock();
        if (!block.getType().isSolid()) {
            return;
        }
        double dis = this.calcDistance(e.getPlayer().getLocation(), block.getLocation());
        if (dis >= 7.5) {
            this.log(data, "Placed too far " + dis);
        }
    }

    private double calcDistance(Location location, Location loc) {
        return Math.abs(location.getX() - loc.getX()) + Math.abs(location.getZ() - loc.getZ());
    }
}

