/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.checks.combat.hitbox;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.Vec3D;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@Check(maxVls=1)
public class HitboxA
extends AbstractCheck {
    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInUseEntityPacket && ((WrappedInUseEntityPacket)packet).entity instanceof Player) {
            Player player = (Player)((WrappedInUseEntityPacket)packet).entity;
            if (player.getGameMode() == GameMode.CREATIVE) {
                return;
            }
            Vec3D vec = ((WrappedInUseEntityPacket)packet).body;
            double x = vec.a;
            double y = vec.b;
            double z = vec.c;
            this.debug(data, "x= " + x + " y=" + y + " z=" + z);
            if (Math.max(Math.abs(x), Math.abs(z)) > 0.401 || Math.abs(y) > 1.91) {
                this.log(data, "x= " + x + " y=" + y + " z=" + z);
            }
        }
    }
}

