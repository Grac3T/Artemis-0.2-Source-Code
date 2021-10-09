/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.combat.autoclicker;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.check.annotations.Experimental;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInArmAnimationPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInUseEntityPacket;

@Check(invalidVersions={ProtocolVersion.V1_9})
@Experimental
public class AutoClickerG
extends AbstractCheck {
    private boolean sent;
    private int vl;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInUseEntityPacket) {
            if (((WrappedInUseEntityPacket)packet).getAction().equals((Object)WrappedInUseEntityPacket.EnumEntityUseAction.ATTACK)) {
                this.sent = false;
            }
        } else if (packet instanceof WrappedInArmAnimationPacket) {
            if (!this.sent && this.vl++ > 10) {
                this.log(data, new String[0]);
            }
        } else if (packet instanceof WrappedInFlyingPacket) {
            this.sent = true;
        }
    }
}

