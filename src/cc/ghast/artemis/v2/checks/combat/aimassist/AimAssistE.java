/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.combat.aimassist;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.utils.location.Position;

@Check
public class AimAssistE
extends AbstractCheck {
    private int buffer;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInFlyingPacket && ((WrappedInFlyingPacket)packet).isLook() && ((WrappedInFlyingPacket)packet).isPos()) {
            if (data.movement.getLocation() == null || data.movement.getLastLocation() == null) {
                return;
            }
            Position to = data.movement.getLocation();
            Position from = data.movement.getLastLocation();
            float yawChange = Math.abs(to.getYaw() - from.getYaw());
            if (yawChange >= 1.0f && yawChange % 0.1f == 0.0f) {
                boolean badYaw;
                boolean bl = badYaw = yawChange % 1.0f == 0.0f || yawChange % 10.0f == 0.0f || yawChange % 30.0f == 0.0f;
                if (badYaw) {
                    ++this.buffer;
                }
                this.log(data, "Buffer -> " + this.buffer);
            }
        }
    }
}

