/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.misc.badpackets;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInBlockPlacePacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInHeldItemSlotPacket;

@Check
public class BadPacketsK
extends AbstractCheck {
    private boolean placed;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInHeldItemSlotPacket) {
            if (this.placed && data.movement.getTeleportTicks() == 0 && data.movement.getRespawnTicks() == 0 && !data.isLagging()) {
                this.log(data, "Magic!");
            }
        } else if (packet instanceof WrappedInBlockPlacePacket) {
            this.placed = true;
        } else if (packet instanceof WrappedInFlyingPacket) {
            this.placed = false;
        }
    }
}

