/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.misc.badpackets;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInArmAnimationPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInUseEntityPacket;

@Check(invalidVersions={ProtocolVersion.V1_9, ProtocolVersion.V1_9_1, ProtocolVersion.V1_9_2, ProtocolVersion.V1_9_4, ProtocolVersion.V1_10, ProtocolVersion.V1_10_2, ProtocolVersion.V1_11, ProtocolVersion.V1_12, ProtocolVersion.V1_12_1, ProtocolVersion.V1_12_2, ProtocolVersion.V1_13, ProtocolVersion.V1_13_1, ProtocolVersion.V1_13_2, ProtocolVersion.V1_14})
public class BadPacketsO
extends AbstractCheck {
    private boolean swung;
    private double vl;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInUseEntityPacket) {
            WrappedInUseEntityPacket.EnumEntityUseAction playerAction = ((WrappedInUseEntityPacket)packet).getAction();
            if (playerAction.equals((Object)WrappedInUseEntityPacket.EnumEntityUseAction.ATTACK)) {
                if (this.swung) {
                    this.vl = 0.0;
                    return;
                }
                if (!data.isLagging()) {
                    if (vl++ > 1.0) {
                        this.log(data);
                    }
                }
            }
        } else if (packet instanceof WrappedInArmAnimationPacket) {
            this.swung = true;
        } else if (packet instanceof WrappedInFlyingPacket) {
            this.swung = false;
        }
    }
}

