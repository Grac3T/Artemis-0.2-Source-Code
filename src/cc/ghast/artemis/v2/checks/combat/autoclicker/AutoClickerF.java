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
import cc.ghast.artemis.v2.utils.location.Position;

@Check
public class AutoClickerF
extends AbstractCheck {
    private double vl;
    private double multiplier = Math.pow(2.0, 24.0);
    private double prevGCD = 0.0;
    private long lastSwing;
    private boolean swung;

    @Override
    public void handle(PlayerData data, NMSObject e) {
        if (e instanceof WrappedInArmAnimationPacket && !data.isLagging()) {
            Position lastPosition = data.movement.getLocation();
            if (lastPosition == null) {
                return;
            }
            long delay = System.currentTimeMillis() - lastPosition.getTimestamp();
            if ((double)delay <= 25.0) {
                this.lastSwing = System.currentTimeMillis();
                this.swung = true;
                return;
            }
            this.vl -= 0.25;
        } else if (e instanceof WrappedInFlyingPacket && this.swung) {
            long time = System.currentTimeMillis() - this.lastSwing;
            if (time >= 25L && !data.isLagging()) {
                if (vl++ > 6.0) {
                    this.log(data, 3, "vl=" + this.vl + " time=" + time);
                }
            } else {
                this.vl -= 0.25;
            }
            this.swung = false;
        }
    }
}

