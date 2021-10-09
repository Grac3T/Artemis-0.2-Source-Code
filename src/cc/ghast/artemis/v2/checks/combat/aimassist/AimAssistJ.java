/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.combat.aimassist;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.utils.location.Rotation;

@Check
public class AimAssistJ
extends AbstractCheck {
    private double lastDif;
    private double lastCalc;
    private int verbose = 0;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInFlyingPacket && ((WrappedInFlyingPacket)packet).isLook() && ((WrappedInFlyingPacket)packet).isPos()) {
            Rotation from;
            if (data.movement.getRotation() == null || data.movement.getLastRotation() == null) {
                return;
            }
            Rotation to = data.movement.getRotation();
            if (to == (from = data.movement.getLastRotation())) {
                return;
            }
            double dif = Math.abs(to.getYaw() - from.getYaw());
            int calc = (int)Math.abs(this.lastDif - dif) * 10;
            if ((double)calc == this.lastCalc) {
                if (this.verbose++ > 3) {
                    this.log(data, "Dif: " + calc);
                }
            } else {
                --this.verbose;
            }
            this.lastDif = dif;
            this.lastCalc = calc;
        }
    }
}

