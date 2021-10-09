/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.combat.autoclicker;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInArmAnimationPacket;

@Check
public class AutoClickerJ
extends AbstractCheck {
    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInArmAnimationPacket && data.combat.getLastCps() >= 30.0 && !data.user.isDigging() && !data.user.isPlaced()) {
            this.log(data, 3, new String[0]);
        }
    }
}

