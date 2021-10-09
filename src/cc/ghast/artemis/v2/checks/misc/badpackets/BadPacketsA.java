/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.misc.badpackets;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.utils.location.Position;
import cc.ghast.artemis.v2.utils.location.Rotation;

@Check(maxVls=1)
public class BadPacketsA
extends AbstractCheck {
    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInFlyingPacket) {
            if (data.movement.getLocation() == null || data.movement.getLastLocation() == null) {
                return;
            }
            if (Math.abs(data.movement.getRotation().getPitch()) > 90.0f) {
                this.log(data, "pitch=" + Math.abs(data.movement.getLocation().getPitch()));
            }
        }
    }
}

