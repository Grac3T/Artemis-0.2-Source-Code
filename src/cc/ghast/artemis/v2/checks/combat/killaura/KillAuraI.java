/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.checks.combat.killaura;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import org.bukkit.entity.Player;

@Check
public class KillAuraI
extends AbstractCheck {
    private Long lastFlying;
    private long lastMovePacket;
    private double vl;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInFlyingPacket) {
            long now = System.currentTimeMillis();
            if (data.movement.getTeleportTicks() > 0 || data.user.isLagging() || data.getPlayer() == null || data.getPlayer().isDead()) {
                return;
            }
            if (this.lastFlying != null) {
                if (now - this.lastFlying > 40L && now - this.lastFlying < 100L) {
                    if ((vl += 0.25) > 1.0) {
                        this.log(data, 2);
                    }
                } else {
                    this.vl = Math.max(0.0, this.vl - 0.05);
                }
                this.lastFlying = null;
            }
            this.lastMovePacket = now;
        } else if (packet instanceof WrappedInUseEntityPacket && ((WrappedInUseEntityPacket)packet).getAction() == WrappedInUseEntityPacket.EnumEntityUseAction.ATTACK) {
            long lastFlying;
            long now = System.currentTimeMillis();
            if (now - (lastFlying = this.lastMovePacket) < 10L) {
                this.lastFlying = lastFlying;
            } else {
                this.vl = Math.max(0.0, this.vl - 0.025);
            }
        }
    }
}

