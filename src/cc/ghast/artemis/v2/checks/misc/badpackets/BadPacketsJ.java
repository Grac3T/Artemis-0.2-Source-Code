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
public class BadPacketsJ
extends AbstractCheck {
    private double vl;

    @Override
    public void handle(PlayerData data, NMSObject e) {
        if (e instanceof WrappedInBlockDigPacket) {
            WrappedInBlockDigPacket.EnumPlayerDigType playerAction = ((WrappedInBlockDigPacket)e).getAction();
            if (playerAction == WrappedInBlockDigPacket.EnumPlayerDigType.RELEASE_USE_ITEM && data.user.isPlaced()) {
                if (vl++ > 6.0) {
                    this.log(data, "vl=" + this.vl);
                }
            } else {
                this.vl = Math.max(0.0, this.vl - 1.0);
            }
        }
    }
}

