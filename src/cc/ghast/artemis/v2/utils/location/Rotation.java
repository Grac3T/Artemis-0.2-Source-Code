/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.utils.location;

import org.bukkit.entity.Player;

public class Rotation {
    private Player player;
    private float pitch;
    private float yaw;

    public Rotation(Player player, float pitch, float yaw) {
        this.player = player;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public Player getPlayer() {
        return this.player;
    }

    public float getPitch() {
        return this.pitch;
    }

    public float getYaw() {
        return this.yaw;
    }
}

