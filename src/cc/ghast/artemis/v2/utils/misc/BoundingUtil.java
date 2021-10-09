/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.AxisAlignedBB
 *  net.minecraft.server.v1_8_R3.EntityPlayer
 *  net.minecraft.server.v1_8_R3.WorldServer
 *  org.bukkit.World
 *  org.bukkit.craftbukkit.v1_8_R3.CraftWorld
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.utils.misc;

import cc.ghast.artemis.v2.utils.misc.CompatUtil;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class BoundingUtil {
    public static boolean boundingBoxOnGround(Player player) {
        CompatUtil.detectVersion();
        if (!CompatUtil.is17()) {
            AxisAlignedBB axisalignedbb = ((CraftPlayer)player).getHandle().getBoundingBox().grow(0.0625, 0.0625, 0.0625).a(0.0, -0.2, 0.0);
            CraftWorld cw = (CraftWorld)player.getWorld();
            return cw.getHandle().c(axisalignedbb);
        }
        return true;
    }
}

