/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.misc.badpackets;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInEntityActionPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;

@Check
public class BadPacketsI
extends AbstractCheck {
    private boolean sneaked;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        WrappedInEntityActionPacket.EnumPlayerAction action;
        if (packet instanceof WrappedInFlyingPacket) {
            this.sneaked = false;
        } else if (packet instanceof WrappedInEntityActionPacket && ((action = ((WrappedInEntityActionPacket)packet).getAction()) == WrappedInEntityActionPacket.EnumPlayerAction.START_SNEAKING || action == WrappedInEntityActionPacket.EnumPlayerAction.STOP_SNEAKING)) {
            if (this.sneaked) {
                if (data.movement.getTeleportTicks() == 0 && data.movement.getRespawnTicks() == 0 && !data.isLagging() && data.movement.getDeathTicks() == 0) {
                    this.log(data, "Magic B");
                }
            } else {
                this.sneaked = true;
            }
        }
    }
}

