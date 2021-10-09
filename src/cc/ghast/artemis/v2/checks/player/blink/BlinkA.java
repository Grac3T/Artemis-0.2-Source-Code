/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.checks.player.blink;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInKeepAlivePacket;
import cc.ghast.artemis.v2.lag.LagCore;
import cc.ghast.artemis.v2.utils.location.Position;
import cc.ghast.artemis.v2.utils.misc.TimeUtil;
import org.bukkit.entity.Player;

@Check
public class BlinkA
extends AbstractCheck {
    private boolean blinking;
    private int forLoc = 0;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        int ping = LagCore.getPing(data.getPlayer());
        if (ping > 150) {
            return;
        }
        Player player = data.getPlayer();
        if (packet instanceof WrappedInKeepAlivePacket) {
            if (player.isInsideVehicle() || player.isDead()) {
                return;
            }
            long deltaMillis = (long)TimeUtil.convert(TimeUtil.elapsed(data.movement.getLastLocation().getTimestamp()), 3, TimeUtil.TimeUnits.SECONDS);
            if ((double)deltaMillis > 0.8) {
                this.blinking = true;
            }
        } else if (packet instanceof WrappedInFlyingPacket && this.blinking) {
            if (this.forLoc == 0) {
                ++this.forLoc;
            } else if (this.forLoc == 1) {
                double xDiff = data.movement.getLastLocation().getX() - data.movement.getLocation().getX();
                double yDiff = data.movement.getLastLocation().getY() - data.movement.getLocation().getY();
                double zDiff = data.movement.getLastLocation().getZ() - data.movement.getLocation().getZ();
                if (xDiff > 0.2 || xDiff < -0.2 || yDiff < -0.2 || yDiff > 0.2 || zDiff < -0.2 || zDiff > 0.2) {
                    this.log(data, "xDiff=" + xDiff + " yDiff=" + yDiff + " zDiff=" + zDiff);
                }
                this.blinking = false;
                this.forLoc = 0;
            }
        }
    }
}

