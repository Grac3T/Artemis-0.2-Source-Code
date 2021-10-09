/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.combat.autoclicker;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInArmAnimationPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;

@Check
public class AutoClickerI
extends AbstractCheck {
    private int swings;
    private int movements;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInArmAnimationPacket && !data.user.isFakeDigging() && !data.user.isPlaced() && System.currentTimeMillis() - data.getMovement().getLastDelayedMovePacket() > 220L && System.currentTimeMillis() - data.getMovement().getLastMovePacket() < 110L) {
            ++this.swings;
        } else if (packet instanceof WrappedInFlyingPacket && ++this.movements == 20) {
            if (this.swings > 20) {
                this.log(data, 5, new String[0]);
            }
            data.combat.setLastCps(this.swings);
            this.swings = 0;
            this.movements = 0;
        }
    }
}

