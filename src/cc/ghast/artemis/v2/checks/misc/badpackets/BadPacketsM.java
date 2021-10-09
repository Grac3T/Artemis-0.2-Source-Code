/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.misc.badpackets;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInSteerVehiclePacket;
import cc.ghast.artemis.v2.utils.location.Position;

@Check
public class BadPacketsM
extends AbstractCheck {
    private double vl;
    private float lastYaw;
    private float lastPitch;
    private boolean ignore;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInFlyingPacket) {
            if (((WrappedInFlyingPacket)packet).isLook() && ((WrappedInFlyingPacket)packet).isPos()) {
                if (data.movement.getLocation() == null || data.movement.getLastLocation() == null) {
                    return;
                }
                if (this.lastYaw == data.movement.getLocation().getYaw() && this.lastPitch == data.movement.getLocation().getPitch()) {
                    if (!this.ignore) {
                        if (vl++ > 5.0) {
                            this.log(data, "vl=" + this.vl);
                        }
                    } else {
                        this.vl = Math.max(0.0, this.vl - 1.0);
                    }
                    this.ignore = false;
                }
                this.lastYaw = data.movement.getLocation().getYaw();
                this.lastPitch = data.movement.getLocation().getPitch();
            } else {
                this.ignore = true;
            }
        } else if (packet instanceof WrappedInSteerVehiclePacket) {
            this.ignore = true;
        }
    }
}

