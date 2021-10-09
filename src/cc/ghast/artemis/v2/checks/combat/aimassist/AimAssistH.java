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
import cc.ghast.artemis.v2.utils.misc.TimeUtil;

@Check
public class AimAssistH
extends AbstractCheck {
    private boolean previous;
    private int count;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInFlyingPacket && ((WrappedInFlyingPacket)packet).isLook() && ((WrappedInFlyingPacket)packet).isPos()) {
            if (data.movement.getRotation() == null || data.movement.getLastRotation() == null || !TimeUtil.hasExpired(data.user.getLastDig(), 3L)) {
                return;
            }
            Rotation to = data.movement.getRotation();
            Rotation from = data.movement.getLastRotation();
            boolean decreaseRate = from.getPitch() > to.getPitch();
            float yawChange = Math.abs(to.getYaw() - from.getYaw());
            float pitchChange = Math.abs(to.getPitch() - from.getPitch());
            if ((double)pitchChange > 0.001 && (double)pitchChange < 0.01 && (double)yawChange > 0.01 && decreaseRate != this.previous && this.count < 20) {
                this.log(data, "Pitch change -> " + pitchChange + " Yaw Change -> " + yawChange);
                this.count = 0;
            }
            this.previous = decreaseRate;
            ++this.count;
        }
    }
}

