/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.combat.autoclicker;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInArmAnimationPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.utils.misc.TimeUtil;

@Check
public class AutoClickerH
extends AbstractCheck {
    private double swings;
    private double ticks;
    private int verbose;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInArmAnimationPacket) {
            if (data.user.isDigging() || data.user.isPlaced() || !TimeUtil.hasExpired(data.user.getLastDig(), 3L)) {
                return;
            }
            this.swings += 1.0;
            if (this.ticks > 10.0) {
                double swingRate = this.ticks / this.swings;
                if (swingRate < 1.1) {
                    if (this.verbose++ > 2) {
                        this.log(data, swingRate + "");
                    }
                } else if (this.verbose != 0) {
                    --this.verbose;
                }
            }
            if (this.swings > 100.0) {
                this.swings = 0.0;
                this.ticks = 0.0;
            }
        } else if (packet instanceof WrappedInFlyingPacket) {
            if (data.user.isDigging() || data.user.isPlaced() || !TimeUtil.hasExpired(data.user.getLastDig(), 3L)) {
                return;
            }
            this.ticks += 1.0;
        }
    }
}

