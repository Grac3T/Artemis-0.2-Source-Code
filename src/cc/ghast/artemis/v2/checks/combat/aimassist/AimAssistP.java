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
public class AimAssistP
extends AbstractCheck {
    @Override
    public void handle(PlayerData data, NMSObject e) {
        if (e instanceof WrappedInFlyingPacket) {
            if (data.movement.getLocation() == null || data.movement.getLastLocation() == null) {
                return;
            }
            Position to = data.movement.getLocation();
            Position from = data.movement.getLastLocation();
            float yawChange = Math.abs(to.getYaw() - from.getYaw());
            if (Float.toString(yawChange).length() > 12) {
                this.log(data, "Yaw Change Size -> " + Float.toString(yawChange).length());
            }
        }
    }
}

