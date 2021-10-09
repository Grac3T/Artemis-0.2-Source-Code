/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.checks.misc.badpackets;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.utils.entity.MovingStats;
import cc.ghast.artemis.v2.utils.location.Position;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@Check
public class BadPacketsN
extends AbstractCheck {
    private double vl;
    private double streak;
    private double balance;
    private long lastFlying;
    private long lastTime;
    private MovingStats movingStats = new MovingStats(20);

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        Player player = data.getPlayer();
        if (packet instanceof WrappedInFlyingPacket && !data.isLagging() && data.movement.getRespawnTicks() == 0 && data.movement.getDeathTicks() == 0 && System.currentTimeMillis() - data.user.getLongInTimePassed() > 5000L && data.movement.getLocation() != null && data.user.getPing() > 0L && !player.isFlying() && player.getGameMode() == GameMode.SURVIVAL) {
            long now = System.currentTimeMillis();
            long time = System.currentTimeMillis();
            long lastTime = this.lastTime != 0L ? this.lastTime : time - 50L;
            this.lastTime = time;
            long rate = time - lastTime;
            this.balance += 50.0;
            this.balance -= (double)rate;
            this.movingStats.add(now - this.lastFlying);
            double max = 7.07;
            double stdDev = this.movingStats.getStdDev(max);
            if (this.balance <= -500.0) {
                this.balance = -500.0;
            } else if (this.balance >= 200.0) {
                this.balance = 200.0;
            }
            if (stdDev != Double.NaN && stdDev < max && this.balance > 10.0) {
                if (vl++ > 1.0) {
                    this.streak += 1.0;
                }
                if (this.streak > 15.0) {
                    this.log(data, "streak=" + this.streak);
                    this.streak = 0.0;
                }
            } else {
                this.vl = Math.max(0.0, this.vl - 1.0);
                this.streak = Math.max(0.0, this.streak - 0.125);
            }
            this.lastFlying = now;
        }
    }
}

