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
public class AimAssistS
extends AbstractCheck {
    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInFlyingPacket && ((WrappedInFlyingPacket)packet).isLook() && ((WrappedInFlyingPacket)packet).isPos()) {
            if (data.movement.getRotation() == null || data.movement.getLastRotation() == null) {
                return;
            }
            Rotation to = data.movement.getRotation();
            Rotation from = data.movement.getLastRotation();
            if (TimeUtil.hasExpired(data.combat.getLastAttack(), 5L)) {
                return;
            }
            float yawChange = Math.abs(to.getYaw() - from.getYaw());
            if (yawChange > 1.0f && yawChange % 0.1f == 0.0f) {
                this.log(data, new String[0]);
            }
        }
    }
}

