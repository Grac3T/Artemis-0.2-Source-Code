/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.utils.location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class BlockUtil {
    public static List<Material> blocked = new ArrayList<Material>();
    private static Set<Byte> blockSolidPassSet = new HashSet<Byte>();
    private static Set<Byte> blockStairsSet = new HashSet<Byte>();
    private static Set<Byte> blockLiquidsSet = new HashSet<Byte>();
    private static Set<Byte> blockWebsSet = new HashSet<Byte>();
    private static Set<Byte> blockIceSet = new HashSet<Byte>();
    private static Set<Byte> blockCarpetSet = new HashSet<Byte>();
    private static Set<Material> blockInvalid = new HashSet<Material>();

    public static boolean isOnStairs(Location location, int down) {
        return BlockUtil.isUnderBlock(location, blockStairsSet, down);
    }

    public static boolean isOnLiquid(Location location, int down) {
        return BlockUtil.isUnderBlock(location, blockLiquidsSet, down);
    }

    public static boolean isOnWeb(Location location, int down) {
        return BlockUtil.isUnderBlock(location, blockWebsSet, down);
    }

    public static boolean isOnIce(Location location, int down) {
        return BlockUtil.isUnderBlock(location, blockIceSet, down);
    }

    public static boolean isOnCarpet(Location location, int down) {
        return BlockUtil.isUnderBlock(location, blockCarpetSet, down);
    }

    public static boolean isSlab(Player player) {
        return blocked.contains((Object)player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType());
    }

    public static boolean isBlockFaceAir(Player player) {
        Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        return block.getType().equals((Object)Material.AIR) && block.getRelative(BlockFace.WEST).getType().equals((Object)Material.AIR) && block.getRelative(BlockFace.NORTH).getType().equals((Object)Material.AIR) && block.getRelative(BlockFace.EAST).getType().equals((Object)Material.AIR) && block.getRelative(BlockFace.SOUTH).getType().equals((Object)Material.AIR);
    }

    private static boolean isUnderBlock(Location location, Set<Byte> itemIDs, int down) {
        double posX = location.getX();
        double posZ = location.getZ();
        double fracX = posX % 1.0 > 0.0 ? Math.abs(posX % 1.0) : 1.0 - Math.abs(posX % 1.0);
        double fracZ = posZ % 1.0 > 0.0 ? Math.abs(posZ % 1.0) : 1.0 - Math.abs(posZ % 1.0);
        int blockX = location.getBlockX();
        int blockY = location.getBlockY() - down;
        int blockZ = location.getBlockZ();
        World world = location.getWorld();
        if (itemIDs.contains((byte)world.getBlockAt(blockX, blockY, blockZ).getTypeId())) {
            return true;
        }
        if (fracX < 0.3) {
            if (itemIDs.contains((byte)world.getBlockAt(blockX - 1, blockY, blockZ).getTypeId())) {
                return true;
            }
            if (fracZ < 0.3) {
                if (itemIDs.contains((byte)world.getBlockAt(blockX - 1, blockY, blockZ - 1).getTypeId())) {
                    return true;
                }
                if (itemIDs.contains((byte)world.getBlockAt(blockX, blockY, blockZ - 1).getTypeId())) {
                    return true;
                }
                if (itemIDs.contains((byte)world.getBlockAt(blockX + 1, blockY, blockZ - 1).getTypeId())) {
                    return true;
                }
            } else if (fracZ > 0.7) {
                if (itemIDs.contains((byte)world.getBlockAt(blockX - 1, blockY, blockZ + 1).getTypeId())) {
                    return true;
                }
                if (itemIDs.contains((byte)world.getBlockAt(blockX, blockY, blockZ + 1).getTypeId())) {
                    return true;
                }
                if (itemIDs.contains((byte)world.getBlockAt(blockX + 1, blockY, blockZ + 1).getTypeId())) {
                    return true;
                }
            }
        } else if (fracX > 0.7) {
            if (itemIDs.contains((byte)world.getBlockAt(blockX + 1, blockY, blockZ).getTypeId())) {
                return true;
            }
            if (fracZ < 0.3) {
                if (itemIDs.contains((byte)world.getBlockAt(blockX - 1, blockY, blockZ - 1).getTypeId())) {
                    return true;
                }
                if (itemIDs.contains((byte)world.getBlockAt(blockX, blockY, blockZ - 1).getTypeId())) {
                    return true;
                }
                if (itemIDs.contains((byte)world.getBlockAt(blockX + 1, blockY, blockZ - 1).getTypeId())) {
                    return true;
                }
            } else if (fracZ > 0.7) {
                if (itemIDs.contains((byte)world.getBlockAt(blockX - 1, blockY, blockZ + 1).getTypeId())) {
                    return true;
                }
                if (itemIDs.contains((byte)world.getBlockAt(blockX, blockY, blockZ + 1).getTypeId())) {
                    return true;
                }
                if (itemIDs.contains((byte)world.getBlockAt(blockX + 1, blockY, blockZ + 1).getTypeId())) {
                    return true;
                }
            }
        } else if (fracZ < 0.3 ? itemIDs.contains((byte)world.getBlockAt(blockX, blockY, blockZ - 1).getTypeId()) : fracZ > 0.7 && itemIDs.contains((byte)world.getBlockAt(blockX, blockY, blockZ + 1).getTypeId())) {
            return true;
        }
        return false;
    }

    public static boolean isOnGround(Location location, int down) {
        double posX = location.getX();
        double posZ = location.getZ();
        double fracX = posX % 1.0 > 0.0 ? Math.abs(posX % 1.0) : 1.0 - Math.abs(posX % 1.0);
        double fracZ = posZ % 1.0 > 0.0 ? Math.abs(posZ % 1.0) : 1.0 - Math.abs(posZ % 1.0);
        int blockX = location.getBlockX();
        int blockY = location.getBlockY() - down;
        int blockZ = location.getBlockZ();
        World world = location.getWorld();
        if (!blockSolidPassSet.contains((byte)world.getBlockAt(blockX, blockY, blockZ).getTypeId())) {
            return true;
        }
        if (fracX < 0.3) {
            if (!blockSolidPassSet.contains((byte)world.getBlockAt(blockX - 1, blockY, blockZ).getTypeId())) {
                return true;
            }
            if (fracZ < 0.3) {
                if (!blockSolidPassSet.contains((byte)world.getBlockAt(blockX - 1, blockY, blockZ - 1).getTypeId())) {
                    return true;
                }
                if (!blockSolidPassSet.contains((byte)world.getBlockAt(blockX, blockY, blockZ - 1).getTypeId())) {
                    return true;
                }
                if (!blockSolidPassSet.contains((byte)world.getBlockAt(blockX + 1, blockY, blockZ - 1).getTypeId())) {
                    return true;
                }
            } else if (fracZ > 0.7) {
                if (!blockSolidPassSet.contains((byte)world.getBlockAt(blockX - 1, blockY, blockZ + 1).getTypeId())) {
                    return true;
                }
                if (!blockSolidPassSet.contains((byte)world.getBlockAt(blockX, blockY, blockZ + 1).getTypeId())) {
                    return true;
                }
                if (!blockSolidPassSet.contains((byte)world.getBlockAt(blockX + 1, blockY, blockZ + 1).getTypeId())) {
                    return true;
                }
            }
        } else if (fracX > 0.7) {
            if (!blockSolidPassSet.contains((byte)world.getBlockAt(blockX + 1, blockY, blockZ).getTypeId())) {
                return true;
            }
            if (fracZ < 0.3) {
                if (!blockSolidPassSet.contains((byte)world.getBlockAt(blockX - 1, blockY, blockZ - 1).getTypeId())) {
                    return true;
                }
                if (!blockSolidPassSet.contains((byte)world.getBlockAt(blockX, blockY, blockZ - 1).getTypeId())) {
                    return true;
                }
                if (!blockSolidPassSet.contains((byte)world.getBlockAt(blockX + 1, blockY, blockZ - 1).getTypeId())) {
                    return true;
                }
            } else if (fracZ > 0.7) {
                if (!blockSolidPassSet.contains((byte)world.getBlockAt(blockX - 1, blockY, blockZ + 1).getTypeId())) {
                    return true;
                }
                if (!blockSolidPassSet.contains((byte)world.getBlockAt(blockX, blockY, blockZ + 1).getTypeId())) {
                    return true;
                }
                if (!blockSolidPassSet.contains((byte)world.getBlockAt(blockX + 1, blockY, blockZ + 1).getTypeId())) {
                    return true;
                }
            }
        } else if (fracZ < 0.3 ? !blockSolidPassSet.contains((byte)world.getBlockAt(blockX, blockY, blockZ - 1).getTypeId()) : fracZ > 0.7 && !blockSolidPassSet.contains((byte)world.getBlockAt(blockX, blockY, blockZ + 1).getTypeId())) {
            return true;
        }
        return false;
    }

    public static boolean againstWall(Player player) {
        Location loc = player.getLocation();
        Location xPos = loc.subtract(0.5, 0.0, 0.0);
        Location yPos = loc.add(0.0, 0.5, 0.0);
        Location zPos = loc.add(0.0, 0.0, 0.5);
        Location xNeg = loc.subtract(0.5, 0.0, 0.0);
        Location yNeg = loc.subtract(0.0, 0.5, 0.0);
        Location zNeg = loc.subtract(0.0, 0.0, 0.5);
        return xPos.getBlock().getType().isSolid() || yPos.getBlock().getType().isSolid() || zPos.getBlock().getType().isSolid() || xNeg.getBlock().getType().isSolid() || yNeg.getBlock().getType().isSolid() || zNeg.getBlock().getType().isSolid();
    }

    static {
        blockSolidPassSet.addAll(Arrays.asList((byte)0, (byte)6, (byte)8, (byte)9, (byte)10, (byte)11, (byte)27, (byte)28, (byte)30, (byte)31, (byte)32, (byte)37, (byte)38, (byte)39, (byte)40, (byte)50, (byte)51, (byte)55, (byte)59, (byte)63, (byte)66, (byte)68, (byte)69, (byte)70, (byte)72, (byte)75, (byte)76, (byte)77, (byte)78, (byte)83, (byte)90, (byte)104, (byte)105, (byte)115, (byte)119, (byte)-124, (byte)-113, (byte)-81));
        blockStairsSet.addAll(Arrays.asList((byte)53, (byte)67, (byte)108, (byte)109, (byte)114, (byte)-128, (byte)-122, (byte)-121, (byte)-120, (byte)-100, (byte)-93, (byte)-93, (byte)-76, (byte)126, (byte)-74, (byte)44, (byte)78, (byte)99, (byte)-112, (byte)-115, (byte)-116, (byte)-105, (byte)-108, (byte)100));
        blockLiquidsSet.addAll(Arrays.asList((byte)8, (byte)9, (byte)10, (byte)11));
        blockWebsSet.add((byte)30);
        blockIceSet.add((byte)79);
        blockIceSet.add((byte)-82);
        blockCarpetSet.add((byte)-85);
        blocked.addAll(Arrays.asList(new Material[]{Material.ACTIVATOR_RAIL, Material.ANVIL, Material.BED_BLOCK, Material.POTATO, Material.POTATO_ITEM, Material.CARROT, Material.CARROT_ITEM, Material.BIRCH_WOOD_STAIRS, Material.BREWING_STAND, Material.BOAT, Material.BRICK_STAIRS, Material.BROWN_MUSHROOM, Material.CAKE_BLOCK, Material.CARPET, Material.CAULDRON, Material.COBBLESTONE_STAIRS, Material.COBBLE_WALL, Material.DARK_OAK_STAIRS, Material.DIODE, Material.DIODE_BLOCK_ON, Material.DIODE_BLOCK_OFF, Material.DEAD_BUSH, Material.DETECTOR_RAIL, Material.DOUBLE_PLANT, Material.DOUBLE_STEP, Material.DRAGON_EGG, Material.PAINTING, Material.FLOWER_POT, Material.GOLD_PLATE, Material.HOPPER, Material.STONE_PLATE, Material.IRON_PLATE, Material.HUGE_MUSHROOM_1, Material.HUGE_MUSHROOM_2, Material.IRON_DOOR_BLOCK, Material.IRON_DOOR, Material.FENCE, Material.IRON_FENCE, Material.IRON_PLATE, Material.ITEM_FRAME, Material.JUKEBOX, Material.JUNGLE_WOOD_STAIRS, Material.LADDER, Material.LEVER, Material.LONG_GRASS, Material.NETHER_FENCE, Material.NETHER_STALK, Material.NETHER_WARTS, Material.MELON_STEM, Material.PUMPKIN_STEM, Material.QUARTZ_STAIRS, Material.RAILS, Material.RED_MUSHROOM, Material.RED_ROSE, Material.SAPLING, Material.SEEDS, Material.SIGN, Material.SIGN_POST, Material.SKULL, Material.SMOOTH_STAIRS, Material.NETHER_BRICK_STAIRS, Material.SPRUCE_WOOD_STAIRS, Material.STAINED_GLASS_PANE, Material.REDSTONE_COMPARATOR, Material.REDSTONE_COMPARATOR_OFF, Material.REDSTONE_COMPARATOR_ON, Material.REDSTONE_LAMP_OFF, Material.REDSTONE_LAMP_ON, Material.REDSTONE_TORCH_OFF, Material.REDSTONE_TORCH_ON, Material.REDSTONE_WIRE, Material.SANDSTONE_STAIRS, Material.STEP, Material.ACACIA_STAIRS, Material.SUGAR_CANE, Material.SUGAR_CANE_BLOCK, Material.ENCHANTMENT_TABLE, Material.SOUL_SAND, Material.TORCH, Material.TRAP_DOOR, Material.TRIPWIRE, Material.TRIPWIRE_HOOK, Material.WALL_SIGN, Material.VINE, Material.WATER_LILY, Material.WEB, Material.WOOD_DOOR, Material.WOOD_DOUBLE_STEP, Material.WOOD_PLATE, Material.WOOD_STAIRS, Material.WOOD_STEP, Material.HOPPER, Material.WOODEN_DOOR, Material.YELLOW_FLOWER, Material.LAVA, Material.WATER, Material.STATIONARY_WATER, Material.STATIONARY_LAVA, Material.CACTUS, Material.CHEST, Material.PISTON_BASE, Material.PISTON_MOVING_PIECE, Material.PISTON_EXTENSION, Material.PISTON_STICKY_BASE, Material.TRAPPED_CHEST, Material.SNOW, Material.ENDER_CHEST, Material.THIN_GLASS, Material.ENDER_PORTAL_FRAME}));
    }
}

