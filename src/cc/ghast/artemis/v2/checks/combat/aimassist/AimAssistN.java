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
public class AimAssistN
extends AbstractCheck {
    private float lastYawChange;
    private float lastPitchChange;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInFlyingPacket && ((WrappedInFlyingPacket)packet).isLook() && ((WrappedInFlyingPacket)packet).isPos()) {
            if (data.movement.getRotation() == null || data.movement.getLastRotation() == null) {
                return;
            }
            Rotation to = data.movement.getRotation();
            Rotation from = data.movement.getLastRotation();
            float yawChange = Math.abs(to.getYaw() - from.getYaw());
            float pitchChange = Math.abs(to.getPitch() - from.getPitch());
            float yawDifference = Math.abs(yawChange - this.lastYawChange);
            float pitchDifference = Math.abs(pitchChange - this.lastPitchChange);
            float yawChangeDifference = Math.abs(yawChange - yawDifference);
            float pitchChangeDifference = Math.abs(pitchChange - pitchDifference);
            if ((double)pitchChange > 0.0 && (double)pitchChange < 0.0094 && (double)pitchDifference > 1.0 && (double)yawDifference > 1.0 && (double)yawChange > 3.0 && (double)this.lastYawChange > 1.5 && (pitchChangeDifference > 1.0f || yawChangeDifference > 1.0f)) {
                this.log(data, "Experimental [3]");
            }
            this.lastPitchChange = pitchChange;
            this.lastYawChange = yawChange;
        }
    }
}

