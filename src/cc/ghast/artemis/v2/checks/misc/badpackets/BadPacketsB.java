/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.checks.misc.badpackets;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.utils.location.Position;
import org.bukkit.entity.Player;

@Check
public class BadPacketsB
extends AbstractCheck {
    private double yMap;
    private double yDifference;
    private int verbose;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInFlyingPacket) {
            if (data.movement.getLocation() == null || data.movement.getLastLocation() == null || !data.getPlayer().isOnGround()) {
                return;
            }
            double dif = this.yMap - data.movement.getLocation().getY();
            int d = (int)(dif * 100.0);
            if (dif > 0.0 && dif < 0.2 && d != 98 && d != 99 && d != 97) {
                if (Math.abs(dif - this.yDifference) == (double)Math.round(Math.abs(dif - this.yDifference) * 100.0) / 100.0) {
                    ++this.verbose;
                    if (this.verbose > 2) {
                        this.log(data, "Consistent Y-Dif, " + dif + " -> Verbose " + this.verbose);
                    }
                } else {
                    this.verbose = 1;
                }
            }
            this.yDifference = dif;
            this.yMap = data.movement.getLocation().getY();
        }
    }
}

