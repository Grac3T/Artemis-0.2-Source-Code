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
public class AutoClickerE
extends AbstractCheck {
    private int swings;
    private int movements;
    private long lastSwing;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInFlyingPacket) {
            ++this.movements;
            if (this.movements == 20 && this.swings > 20) {
                this.log(data, 2, new String[0]);
            }
            if (System.currentTimeMillis() - this.lastSwing <= 350L) {
                boolean b = false;
                this.movements = b ? 1 : 0;
                int n = this.swings = b ? 1 : 0;
            }
        }
        if (packet instanceof WrappedInArmAnimationPacket && System.currentTimeMillis() - data.movement.getLastDelayedMovePacket() > 110L && System.currentTimeMillis() - data.movement.getLastMovePacket() < 110L && !data.user.isDigging() && !data.user.isPlaced()) {
            ++this.swings;
            this.lastSwing = System.currentTimeMillis();
        }
    }
}

