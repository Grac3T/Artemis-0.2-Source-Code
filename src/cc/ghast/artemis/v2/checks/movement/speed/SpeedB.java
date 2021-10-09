/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package cc.ghast.artemis.v2.checks.movement.speed;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.utils.misc.TimeUtil;
import java.util.Collection;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Check
public class SpeedB
extends AbstractCheck
implements Listener {
    private double lastDistance;

    @Override
    public void handle(PlayerData data, PlayerMoveEvent event) {
        Player mover = event.getPlayer();
        if (mover.getAllowFlight() || mover.getGameMode().equals((Object)GameMode.CREATIVE) || mover.isInsideVehicle() || !TimeUtil.hasExpired(data.combat.getLastBowDamage(), 1L)) {
            return;
        }
        double distance = Math.abs(event.getTo().getX() - event.getFrom().getX()) + Math.abs(event.getTo().getZ() - event.getFrom().getZ());
        if (this.lastDistance == 0.0) {
            this.lastDistance = distance;
            return;
        }
        double scaledDistanceDif = Math.abs(distance - this.lastDistance * 0.91) * 100.0;
        if (mover.getWorld().getBlockAt(new Location(mover.getWorld(), mover.getLocation().getX(), mover.getLocation().getY() + 2.0, mover.getLocation().getZ())).getType() != Material.AIR) {
            scaledDistanceDif -= 1.0;
        }
        if (data.movement.isOnIce()) {
            return;
        }
        if ((double)mover.getWalkSpeed() > 1.0) {
            scaledDistanceDif -= 1.1 * (double)mover.getWalkSpeed();
        }
        for (PotionEffect potionEffect : mover.getActivePotionEffects()) {
            if (potionEffect.getType() != PotionEffectType.SPEED) continue;
            scaledDistanceDif -= (double)potionEffect.getAmplifier() * 1.1;
        }
        if (data.wasOnSlime()) {
            return;
        }
        if (mover.getNoDamageTicks() > 0) {
            return;
        }
        Location b = mover.getLocation().clone();
        b.setY(b.getY() - 1.0);
        if (b.getBlock().getType() != Material.AIR && b.getBlock() != null) {
            if (this.isOnGround(mover) && scaledDistanceDif >= 65.0) {
                this.log(data, "(Experimental) - " + scaledDistanceDif);
            } else if (scaledDistanceDif >= 60.0) {
                this.log(data, "(Experimental) - " + scaledDistanceDif);
            }
        }
        this.lastDistance = distance;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isOnGround(Player player) {
        if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
            return true;
        }
        Location a = player.getLocation().clone();
        a.setY(a.getY() - 0.5);
        if (a.getBlock().getType() != Material.AIR) {
            return true;
        }
        a = player.getLocation().clone();
        a.setY(a.getY() + 0.5);
        if (a.getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) return true;
        if (!this.isBlock(player.getLocation().getBlock().getRelative(BlockFace.DOWN), new Material[]{Material.FENCE, Material.FENCE_GATE, Material.COBBLE_WALL, Material.LADDER})) return false;
        return true;
    }

    private boolean isBlock(Block block, Material[] materials) {
        Material type = block.getType();
        Material[] arrayOfMaterial = materials;
        int j = materials.length;
        for (int i = 0; i < j; ++i) {
            Material m = arrayOfMaterial[i];
            if (m != type) continue;
            return true;
        }
        return false;
    }
}

