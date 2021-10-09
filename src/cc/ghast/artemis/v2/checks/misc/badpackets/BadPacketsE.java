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
public class BadPacketsE
extends AbstractCheck {
    private boolean sent;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        WrappedInEntityActionPacket.EnumPlayerAction action;
        if (packet instanceof WrappedInFlyingPacket) {
            this.sent = false;
        } else if (packet instanceof WrappedInEntityActionPacket && ((action = ((WrappedInEntityActionPacket)packet).getAction()) == WrappedInEntityActionPacket.EnumPlayerAction.START_SNEAKING || action == WrappedInEntityActionPacket.EnumPlayerAction.STOP_SNEAKING)) {
            if (this.sent) {
                this.log(data, 1, new String[0]);
            } else {
                this.sent = true;
            }
        }
    }
}

