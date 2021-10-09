/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.combat.aimassist;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.check.annotations.Experimental;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.utils.location.Rotation;

@Check
@Experimental
public class AimAssistG
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
            float yawPitchDifference = Math.abs(yawChange - pitchChange);
            if (yawChange > 0.05f && pitchChange > 0.05f && ((double)pitchDifference > 1.0 || (double)yawDifference > 1.0) && (pitchChangeDifference > 1.0f || yawChangeDifference > 1.0f) && yawDifference < 0.009f && yawPitchDifference > 0.001f) {
                this.log(data, "Experimental [1]");
            }
            this.lastPitchChange = pitchChange;
            this.lastYawChange = yawChange;
        }
    }
}

