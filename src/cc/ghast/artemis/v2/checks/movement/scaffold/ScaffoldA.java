/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.event.player.PlayerMoveEvent
 */
package cc.ghast.artemis.v2.checks.movement.scaffold;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.check.annotations.Experimental;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.utils.MathUtil;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

@Check
@Experimental
public class ScaffoldA
extends AbstractCheck {
    private double multiplier = Math.pow(2.0, 24.0);
    private float previous;
    private double vl;
    private double streak;

    @Override
    public void handle(PlayerData data, PlayerMoveEvent e) {
        Location to = e.getTo();
        Location from = e.getFrom();
        if (System.currentTimeMillis() - data.user.getLastPlace() > 1000L) {
            this.vl = 0.0;
            return;
        }
        float pitchChange = MathUtil.getDistanceBetweenAngles(to.getPitch(), from.getPitch());
        long a = (long)((double)pitchChange * this.multiplier);
        long b = (long)((double)this.previous * this.multiplier);
        long gcd = this.gcd(a, b);
        float magicVal = pitchChange * 100.0f / this.previous;
        if (magicVal > 24.0f) {
            this.vl = Math.max(0.0, this.vl - 1.0);
        }
        if ((double)pitchChange >= 0.05 && pitchChange <= 20.0f && gcd < 131072L) {
            double d = 0;
            this.vl += 1.0;
            if (d > 1.0) {
                this.streak += 1.0;
            }
            if (this.streak > 6.0) {
                this.log(data, "gcd=" + gcd + " streak=" + this.streak + " vl=" + this.vl + " pC=" + pitchChange + " magicVal=" + magicVal);
            }
        } else {
            this.vl = Math.max(0.0, this.vl - 1.0);
            this.streak = Math.max(0.0, this.streak - 0.25);
        }
        this.debug(data, "gcd=" + gcd + " streak=" + this.streak + " vl=" + this.vl + " pC=" + pitchChange + " magicVal=" + magicVal);
        this.previous = pitchChange;
    }

    private long gcd(long a, long b) {
        if (b <= 16384L) {
            return a;
        }
        return this.gcd(b, a % b);
    }
}

