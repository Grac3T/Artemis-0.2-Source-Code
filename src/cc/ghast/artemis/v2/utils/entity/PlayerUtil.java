/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.EntityPlayer
 *  net.minecraft.server.v1_8_R3.IChatBaseComponent
 *  net.minecraft.server.v1_8_R3.IChatBaseComponent$ChatSerializer
 *  net.minecraft.server.v1_8_R3.Packet
 *  net.minecraft.server.v1_8_R3.PacketPlayOutTitle
 *  net.minecraft.server.v1_8_R3.PacketPlayOutTitle$EnumTitleAction
 *  net.minecraft.server.v1_8_R3.PlayerConnection
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.util.Vector
 */
package cc.ghast.artemis.v2.utils.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class PlayerUtil {
    public static int getPing(Player player) {
        return ((CraftPlayer)player).getHandle().ping;
    }

    public static double[] getOffsetsOffCursor(Player player, EntityPlayer entity) {
        Location entityLoc = entity.getBukkitEntity().getLocation().add(0.0, entity.getBukkitEntity().getLocation().getY() + 0.8, 0.0);
        Location playerLoc = player.getLocation().add(0.0, player.getEyeHeight(), 0.0);
        Vector playerRotation = new Vector(playerLoc.getYaw(), playerLoc.getPitch(), 0.0f);
        Vector expectedRotation = PlayerUtil.getRotation(playerLoc, entityLoc);
        double deltaYaw = PlayerUtil.clamp180(playerRotation.getX() - expectedRotation.getX());
        double deltaPitch = PlayerUtil.clamp180(playerRotation.getY() - expectedRotation.getY());
        double horizontalDistance = PlayerUtil.getHorizontalDistance(playerLoc, entityLoc);
        double distance = PlayerUtil.getDistance3D(playerLoc, entityLoc);
        double offsetX = deltaYaw * horizontalDistance * distance;
        double offsetY = deltaPitch * Math.abs(Math.sqrt(entityLoc.getY() - playerLoc.getY())) * distance;
        return new double[]{Math.abs(offsetX), Math.abs(offsetY)};
    }

    public static double clamp180(double dub) {
        if ((dub %= 360.0) >= 180.0) {
            dub -= 360.0;
        }
        if (dub < -180.0) {
            dub += 360.0;
        }
        return dub;
    }

    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        CraftPlayer craftplayer = (CraftPlayer)player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a((String)("{'text': '" + title + "'}"));
        IChatBaseComponent subtitleJSON = IChatBaseComponent.ChatSerializer.a((String)("{'text': '" + subtitle + "'}"));
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleJSON, fadeIn, stay, fadeOut);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleJSON);
        connection.sendPacket((Packet)titlePacket);
        connection.sendPacket((Packet)subtitlePacket);
    }

    public static boolean isReallyOnground(Player p) {
        Location l = p.getLocation();
        int x = l.getBlockX();
        int y = l.getBlockY();
        int z = l.getBlockZ();
        Location b = new Location(p.getWorld(), (double)x, (double)(y - 1), (double)z);
        return p.isOnGround() && b.getBlock().getType() != Material.AIR && b.getBlock().getType() != Material.WEB && !b.getBlock().isLiquid();
    }

    public static Vector getRotation(Location one, Location two) {
        double dx = two.getX() - one.getX();
        double dy = two.getY() - one.getY();
        double dz = two.getZ() - one.getZ();
        double distanceXZ = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float)(Math.atan2(dz, dx) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-Math.atan2(dy, distanceXZ) * 180.0 / 3.141592653589793);
        return new Vector(yaw, pitch, 0.0f);
    }

    public static double getHorizontalDistance(Location to, Location from) {
        double x = Math.abs(Math.abs(to.getX()) - Math.abs(from.getX()));
        double z = Math.abs(Math.abs(to.getZ()) - Math.abs(from.getZ()));
        return Math.sqrt(x * x + z * z);
    }

    public static double getDistance3D(Location one, Location two) {
        double toReturn = 0.0;
        double xSqr = (two.getX() - one.getX()) * (two.getX() - one.getX());
        double ySqr = (two.getY() - one.getY()) * (two.getY() - one.getY());
        double zSqr = (two.getZ() - one.getZ()) * (two.getZ() - one.getZ());
        double sqrt = Math.sqrt(xSqr + ySqr + zSqr);
        toReturn = Math.abs(sqrt);
        return toReturn;
    }

    public static int getPotionEffectLevel(Player player, PotionEffectType pet) {
        for (PotionEffect pe : player.getActivePotionEffects()) {
            if (!pe.getType().getName().equals(pet.getName())) continue;
            return pe.getAmplifier() + 1;
        }
        return 0;
    }

    public static Entity getNearestEntityInSight(Player player, int range) {
        int i;
        ArrayList entities = (ArrayList)player.getNearbyEntities((double)range, (double)range, (double)range);
        ArrayList sightBlock = (ArrayList)player.getLineOfSight((Set)null, range);
        ArrayList<Location> sight = new ArrayList<Location>();
        for (i = 0; i < sightBlock.size(); ++i) {
            sight.add(((Block)sightBlock.get(i)).getLocation());
        }
        for (i = 0; i < sight.size(); ++i) {
            for (int k = 0; k < entities.size(); ++k) {
                if (!(Math.abs(((Entity)entities.get(k)).getLocation().getX() - ((Location)sight.get(i)).getX()) < 1.3) || !(Math.abs(((Entity)entities.get(k)).getLocation().getY() - ((Location)sight.get(i)).getY()) < 1.5) || !(Math.abs(((Entity)entities.get(k)).getLocation().getZ() - ((Location)sight.get(i)).getZ()) < 1.3)) continue;
                return (Entity)entities.get(k);
            }
        }
        return null;
    }
}

