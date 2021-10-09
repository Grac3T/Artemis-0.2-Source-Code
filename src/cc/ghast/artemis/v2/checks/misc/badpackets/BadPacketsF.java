/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.misc.badpackets;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInBlockDigPacket;

@Check
public class BadPacketsF
extends AbstractCheck {
    private final String something = "%%__USERNAME__%%";

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInBlockDigPacket && ((WrappedInBlockDigPacket)packet).getAction() == WrappedInBlockDigPacket.EnumPlayerDigType.RELEASE_USE_ITEM && data.user.isPlaced()) {
            this.log(data, 1, new String[0]);
        }
    }
}

