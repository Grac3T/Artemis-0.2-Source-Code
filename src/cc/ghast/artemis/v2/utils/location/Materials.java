/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 */
package cc.ghast.artemis.v2.utils.location;

import org.bukkit.Material;

public class Materials {
    private static final int[] MATERIAL_FLAGS = new int[256];
    public static final int SOLID = 1;
    public static final int LIQUID = 2;
    public static final int LADDER = 4;
    public static final int WALL = 8;
    private static final int STAIRS = 16;

    public Materials() {
        for (int i = 0; i < MATERIAL_FLAGS.length; ++i) {
            Material material = Material.values()[i];
            if (material.isSolid()) {
                int n = i;
                MATERIAL_FLAGS[n] = MATERIAL_FLAGS[n] | 1;
            }
            if (material.name().endsWith("_STAIRS")) {
                int n = i;
                MATERIAL_FLAGS[n] = MATERIAL_FLAGS[n] | 0x10;
            }
            if (!material.name().toLowerCase().contains("clay")) continue;
            int n = i;
            MATERIAL_FLAGS[n] = MATERIAL_FLAGS[n] | 1;
        }
        Materials.MATERIAL_FLAGS[Material.SIGN_POST.getId()] = 0;
        Materials.MATERIAL_FLAGS[Material.WALL_SIGN.getId()] = 0;
        Materials.MATERIAL_FLAGS[Material.DIODE_BLOCK_OFF.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.DIODE_BLOCK_ON.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.CARPET.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.SNOW.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.ANVIL.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.STAINED_CLAY.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.TRAP_DOOR.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.ENCHANTMENT_TABLE.getId()] = 1;
        Materials.MATERIAL_FLAGS[Material.ENDER_PORTAL_FRAME.getId()] = 1;
        Materials.MATERIAL_FLAGS[172] = 1;
        Materials.MATERIAL_FLAGS[Material.IRON_TRAPDOOR.getId()] = 1;
        int n = Material.WATER.getId();
        MATERIAL_FLAGS[n] = MATERIAL_FLAGS[n] | 2;
        int n2 = Material.STATIONARY_WATER.getId();
        MATERIAL_FLAGS[n2] = MATERIAL_FLAGS[n2] | 2;
        int n3 = Material.LAVA.getId();
        MATERIAL_FLAGS[n3] = MATERIAL_FLAGS[n3] | 2;
        int n4 = Material.STATIONARY_LAVA.getId();
        MATERIAL_FLAGS[n4] = MATERIAL_FLAGS[n4] | 2;
        int n5 = Material.LADDER.getId();
        MATERIAL_FLAGS[n5] = MATERIAL_FLAGS[n5] | 5;
        int n6 = Material.VINE.getId();
        MATERIAL_FLAGS[n6] = MATERIAL_FLAGS[n6] | 5;
        int n7 = Material.FENCE.getId();
        MATERIAL_FLAGS[n7] = MATERIAL_FLAGS[n7] | 8;
        int n8 = Material.SKULL.getId();
        MATERIAL_FLAGS[n8] = MATERIAL_FLAGS[n8] | 8;
        int n9 = Material.FENCE_GATE.getId();
        MATERIAL_FLAGS[n9] = MATERIAL_FLAGS[n9] | 8;
        int n10 = Material.COBBLE_WALL.getId();
        MATERIAL_FLAGS[n10] = MATERIAL_FLAGS[n10] | 8;
        int n11 = Material.NETHER_FENCE.getId();
        MATERIAL_FLAGS[n11] = MATERIAL_FLAGS[n11] | 8;
    }

    public static boolean checkFlag(Material material, int flag) {
        return (MATERIAL_FLAGS[material.getId()] & flag) == flag;
    }
}

