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
import cc.ghast.artemis.v2.utils.misc.TimeUtil;

@Check
public class AutoClickerC
extends AbstractCheck {
    private int ticks;
    private int clicks;
    private int oldClicks;
    private boolean alerted;
    private long lastClick;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInArmAnimationPacket) {
            if (!(data.user.isDigging() && data.user.isPlaced() && TimeUtil.hasExpired(data.user.getLastDig(), 3L))) {
                this.ticks = 0;
                if (System.currentTimeMillis() - this.lastClick > 250L) {
                    this.clicks = this.oldClicks;
                }
                this.lastClick = System.currentTimeMillis();
            }
        } else if (packet instanceof WrappedInFlyingPacket) {
            if (data.user.isDigging() || data.user.isPlaced() || !TimeUtil.hasExpired(data.user.getLastDig(), 3L)) {
                return;
            }
            if (++this.ticks <= 2) {
                if (++this.clicks > 100) {
                    if (this.clicks > 220) {
                        this.log(data, 2, "Abnormal");
                    }
                    if (this.clicks > 400) {
                        this.log(data, 15, "Impossible");
                        this.oldClicks = this.clicks;
                        this.clicks = 0;
                    }
                }
            } else if (this.ticks == 3) {
                this.oldClicks = this.clicks;
                this.clicks = 0;
            }
        }
    }
}

