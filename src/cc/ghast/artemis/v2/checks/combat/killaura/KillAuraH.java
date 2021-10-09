/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.combat.killaura;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInArmAnimationPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInUseEntityPacket;

@Check
public class KillAuraH
extends AbstractCheck {
    private boolean sentArmAnimation;
    private boolean sentAttack;
    private boolean sentBlockPlace;
    private boolean sentUseEntity;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInUseEntityPacket) {
            this.sentAttack = ((WrappedInUseEntityPacket)packet).getAction().equals((Object)WrappedInUseEntityPacket.EnumEntityUseAction.ATTACK);
        } else if (packet instanceof WrappedInArmAnimationPacket) {
            this.sentArmAnimation = true;
        }
    }
}

