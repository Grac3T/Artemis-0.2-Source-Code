/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package cc.ghast.artemis.v2.utils.chat;

import org.bukkit.ChatColor;

public class StringUtil {
    public static final String prefix = "Artemis";
    public static final String STAFF_PERM = "Artemis.staff";
    public static final String ADMIN_PERM = "Artemis.admin";
    public static final String CONSOLE_UUD = new Object(){
        int t;

        public String toString() {
            byte[] buf = new byte[32];
            this.t = -684554127;
            buf[0] = (byte)(this.t >>> 16);
            this.t = 889559505;
            buf[1] = (byte)(this.t >>> 7);
            this.t = -1497514641;
            buf[2] = (byte)(this.t >>> 21);
            this.t = -39564056;
            buf[3] = (byte)(this.t >>> 6);
            this.t = 1964585479;
            buf[4] = (byte)(this.t >>> 14);
            this.t = 1908926246;
            buf[5] = (byte)(this.t >>> 5);
            this.t = 1865881909;
            buf[6] = (byte)(this.t >>> 7);
            this.t = -84489129;
            buf[7] = (byte)(this.t >>> 5);
            this.t = 178985326;
            buf[8] = (byte)(this.t >>> 7);
            this.t = -1197739621;
            buf[9] = (byte)(this.t >>> 2);
            this.t = 1691384032;
            buf[10] = (byte)(this.t >>> 18);
            this.t = -1680768133;
            buf[11] = (byte)(this.t >>> 11);
            this.t = 439222270;
            buf[12] = (byte)(this.t >>> 23);
            this.t = 849952465;
            buf[13] = (byte)(this.t >>> 23);
            this.t = -1475008773;
            buf[14] = (byte)(this.t >>> 5);
            this.t = 1704520902;
            buf[15] = (byte)(this.t >>> 5);
            this.t = -1347326700;
            buf[16] = (byte)(this.t >>> 15);
            this.t = 1708452647;
            buf[17] = (byte)(this.t >>> 24);
            this.t = -1933004460;
            buf[18] = (byte)(this.t >>> 17);
            this.t = -528028214;
            buf[19] = (byte)(this.t >>> 13);
            this.t = -1395905901;
            buf[20] = (byte)(this.t >>> 13);
            this.t = -351494418;
            buf[21] = (byte)(this.t >>> 19);
            this.t = 455809151;
            buf[22] = (byte)(this.t >>> 23);
            this.t = 281814656;
            buf[23] = (byte)(this.t >>> 14);
            this.t = 1886331230;
            buf[24] = (byte)(this.t >>> 17);
            this.t = 1664152417;
            buf[25] = (byte)(this.t >>> 16);
            this.t = -896173685;
            buf[26] = (byte)(this.t >>> 3);
            this.t = -234525595;
            buf[27] = (byte)(this.t >>> 5);
            this.t = -1910915617;
            buf[28] = (byte)(this.t >>> 11);
            this.t = 241263326;
            buf[29] = (byte)(this.t >>> 17);
            this.t = -1508264022;
            buf[30] = (byte)(this.t >>> 14);
            this.t = 1212375608;
            buf[31] = (byte)(this.t >>> 8);
            return new String(buf);
        }
    }.toString();

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)msg);
    }

}

