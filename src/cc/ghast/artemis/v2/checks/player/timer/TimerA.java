/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.player.timer;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import java.util.Deque;
import java.util.LinkedList;

@Check
public class TimerA
extends AbstractCheck {
    private double vl;
    private Deque<Long> delays = new LinkedList<Long>();
    private long lastPacketTime;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInFlyingPacket) {
            if (data.user.isTpUnknown() && System.currentTimeMillis() - data.movement.getLastDelayedMovePacket() > 110L) {
                this.delays.add(System.currentTimeMillis() - this.lastPacketTime);
                if (this.delays.size() == 40) {
                    double average = 0.0;
                    for (long l : this.delays) {
                        average += (double)l;
                    }
                    if ((average /= (double)this.delays.size()) <= 49.0) {
                        double d = 0;
                        this.vl += 1.25;
                        if (d > 4.0 && average >= 35.714285714285715) {
                            this.log(data, "avg=" + average);
                        }
                    } else {
                        this.vl -= 0.5;
                    }
                    this.delays.clear();
                }
                this.lastPacketTime = System.currentTimeMillis();
            }
            this.debug(data, "delaysize=" + this.delays.size());
        }
    }
}

