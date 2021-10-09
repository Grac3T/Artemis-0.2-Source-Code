/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerMoveEvent
 */
package cc.ghast.artemis.v2.checks.movement.speed;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

@Check
public class SpeedC
extends AbstractCheck {
    private int buffer = 0;

    @Override
    public void handle(PlayerData data, PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.getFrom().getY() >= event.getTo().getY()) {
            return;
        }
        if (this.isOnStairs(player) || this.isInLiquid(player) || this.isInWeb(player) || data.hasBeenDamaged()) {
            return;
        }
        if (player.getAllowFlight()) {
            return;
        }
        if (data.wasOnSlime() || data.movement.isOnIce()) {
            return;
        }
        double delta = event.getTo().getY() - event.getFrom().getY();
        if (event.getFrom().getY() % 1.0 == 0.0 && event.getTo().getY() % 1.0 > 0.0) {
            double expected;
            double d = expected = this.isUnderBlock(player) ? 0.20000004768371582 : 0.41999998688697815;
            if (delta < expected) {
                if (this.buffer++ > 3) {
                    this.log(data, "dy=" + delta + " ! exp=" + expected);
                }
            } else if (delta > 0.6) {
                if (this.buffer++ > 3) {
                    this.log(data, "dy=" + delta + " ! exp=0.6 (7x6)");
                }
            } else {
                --this.buffer;
            }
        }
    }

    private boolean isUnderBlock(Player player) {
        Block block = player.getEyeLocation().getBlock().getRelative(BlockFace.UP);
        boolean b = block != null && block.getType().isSolid();
        return b;
    }

    private boolean isOnStairs(Player player) {
        Material material = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType();
        boolean b = material == Material.WOOD_STAIRS || material == Material.COBBLESTONE_STAIRS || material == Material.QUARTZ_STAIRS || material == Material.SMOOTH_STAIRS;
        return b;
    }

    private boolean isInLiquid(Player player) {
        boolean b = player.getLocation().getBlock().getRelative(BlockFace.DOWN).isLiquid();
        return b;
    }

    private boolean isInWeb(Player player) {
        boolean b = player.getLocation().getBlock() != null && player.getLocation().getBlock().getType() == Material.WEB;
        return b;
    }
}

