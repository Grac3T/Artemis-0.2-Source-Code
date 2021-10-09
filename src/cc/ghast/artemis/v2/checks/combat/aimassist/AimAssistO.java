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
public class AimAssistO
extends AbstractCheck {
    private int buffer;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInFlyingPacket && ((WrappedInFlyingPacket)packet).isLook() && ((WrappedInFlyingPacket)packet).isPos()) {
            if (data.movement.getRotation() == null || data.movement.getLastRotation() == null) {
                return;
            }
            Rotation to = data.movement.getRotation();
            Rotation from = data.movement.getLastRotation();
            float pitchChange = Math.abs(to.getPitch() - from.getPitch());
            if (pitchChange >= 1.0f && pitchChange % 0.1f == 0.0f) {
                boolean badPitch;
                boolean bl = badPitch = pitchChange % 1.0f == 0.0f || pitchChange % 10.0f == 0.0f || pitchChange % 30.0f == 0.0f;
                if (badPitch) {
                    ++this.buffer;
                }
                this.log(data, "Buffer -> " + this.buffer);
            }
        }
    }
}

