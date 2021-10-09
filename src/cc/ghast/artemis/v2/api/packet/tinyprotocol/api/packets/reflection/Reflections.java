/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.WrappedClass;
import org.bukkit.Bukkit;

public class Reflections {
    private static final String craftBukkitString;
    private static final String netMinecraftServerString;

    public static boolean classExists(String name) {
        try {
            Class.forName(name);
            return true;
        }
        catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static WrappedClass getCBClass(String name) {
        return Reflections.getClass(craftBukkitString + name);
    }

    public static WrappedClass getNMSClass(String name) {
        return Reflections.getClass(netMinecraftServerString + name);
    }

    public static WrappedClass getClass(String name) {
        try {
            return new WrappedClass(Class.forName(name));
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static WrappedClass getClass(Class clazz) {
        return new WrappedClass(clazz);
    }

    static {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        craftBukkitString = "org.bukkit.craftbukkit." + version + ".";
        netMinecraftServerString = "net.minecraft.server." + version + ".";
    }
}

