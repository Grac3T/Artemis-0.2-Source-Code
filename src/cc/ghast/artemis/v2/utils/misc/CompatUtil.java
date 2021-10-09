/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 */
package cc.ghast.artemis.v2.utils.misc;

import org.bukkit.Bukkit;

public class CompatUtil {
    private static boolean is17 = false;

    public static void detectVersion() {
        String nmsVersion = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        if (nmsVersion.contains("v1_7_R4")) {
            is17 = true;
        }
    }

    public static boolean is17() {
        return is17;
    }
}

