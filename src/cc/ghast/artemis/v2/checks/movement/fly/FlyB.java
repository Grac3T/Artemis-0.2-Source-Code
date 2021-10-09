/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package cc.ghast.artemis.v2.checks.movement.fly;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.check.annotations.Experimental;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.utils.misc.TimeUtil;
import java.util.ArrayList;
import java.util.Collection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Check
@Experimental
public class FlyB
extends AbstractCheck {
    private long lastGround;
    private long lastMovement;

    @Override
    public void handle(PlayerData playerData, PlayerMoveEvent event) {
        Player player = event.getPlayer();
        this.lastMovement = System.currentTimeMillis();
        if (this.isInLiquid(player) || this.isInWeb(player) || player.getAllowFlight() || !TimeUtil.hasExpired(playerData.combat.getLastExplosion(), 5L) || player.getVehicle() != null || event.getFrom().getY() > event.getTo().getY()) {
            return;
        }
        if (this.isOnGround(player) || this.isOnLilyPad(player) || this.blocksNear(player.getLocation())) {
            this.lastGround = System.currentTimeMillis();
            return;
        }
        if (playerData.wasOnSlime()) {
            return;
        }
        if (playerData.hasBeenDamaged()) {
            return;
        }
        long airTicks = System.currentTimeMillis() - this.lastGround;
        long expected = playerData.hasJumped() ? 800L : 202L;
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            if (potionEffect.getType() != PotionEffectType.JUMP) continue;
            expected += (long)potionEffect.getAmplifier() * 300L;
        }
        if (airTicks > expected && System.currentTimeMillis() - this.lastMovement < 500L) {
            this.log(playerData, "(Experimental) - Count: " + airTicks + " -> expected: " + expected);
        }
    }

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
        return a.getBlock().getRelative(BlockFace.DOWN).getType().isSolid();
    }

    private boolean isInLiquid(Player player) {
        return player.getLocation().getBlock().getRelative(BlockFace.DOWN).isLiquid();
    }

    private boolean isInWeb(Player player) {
        return player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.WEB;
    }

    private boolean blocksNear(Location loc) {
        boolean nearBlocks = false;
        for (Block block : this.getSurrounding(loc.getBlock(), true)) {
            if (block.getType() == Material.AIR) continue;
            nearBlocks = true;
            break;
        }
        for (Block block : this.getSurrounding(loc.getBlock(), false)) {
            if (block.getType() == Material.AIR) continue;
            nearBlocks = true;
            break;
        }
        loc.setY(loc.getY() - 0.5);
        if (loc.getBlock().getType() != Material.AIR) {
            nearBlocks = true;
        }
        if (this.isBlock(loc.getBlock().getRelative(BlockFace.DOWN), new Material[]{Material.FENCE, Material.FENCE_GATE, Material.COBBLE_WALL, Material.LADDER})) {
            nearBlocks = true;
        }
        return nearBlocks;
    }

    private ArrayList<Block> getSurrounding(Block block, boolean diagonals) {
        ArrayList<Block> blocks = new ArrayList<Block>();
        if (diagonals) {
            for (int x = -1; x <= 1; ++x) {
                for (int y = -1; y <= 1; ++y) {
                    for (int z = -1; z <= 1; ++z) {
                        if (x == 0 && y == 0 && z == 0) continue;
                        blocks.add(block.getRelative(x, y, z));
                    }
                }
            }
        } else {
            blocks.add(block.getRelative(BlockFace.UP));
            blocks.add(block.getRelative(BlockFace.DOWN));
            blocks.add(block.getRelative(BlockFace.NORTH));
            blocks.add(block.getRelative(BlockFace.SOUTH));
            blocks.add(block.getRelative(BlockFace.EAST));
            blocks.add(block.getRelative(BlockFace.WEST));
        }
        return blocks;
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

    private boolean isOnLilyPad(Player player) {
        Block block = player.getLocation().getBlock();
        Material lily = Material.WATER_LILY;
        return block.getType() == lily || block.getRelative(BlockFace.NORTH).getType() == lily || block.getRelative(BlockFace.SOUTH).getType() == lily || block.getRelative(BlockFace.EAST).getType() == lily || block.getRelative(BlockFace.WEST).getType() == lily;
    }
}

