/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.event.player.PlayerMoveEvent
 */
package cc.ghast.artemis.v2.checks.combat.killaura;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.utils.MathUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

@Check
public class KillAuraF
extends AbstractCheck {
    private double vl;
    private double multiplier = Math.pow(2.0, 24.0);
    private float previous;
    private List<Float> previousYaws = new ArrayList<Float>();

    @Override
    public void handle(PlayerData data, PlayerMoveEvent e) {
        Location to = e.getTo();
        Location from = e.getFrom();
        float pitchChange = Math.abs(to.getPitch() - from.getPitch());
        float yawChange = Math.abs(to.getYaw() - from.getYaw());
        long a = (long)((double)pitchChange * this.multiplier);
        long b = (long)((double)this.previous * this.multiplier);
        long gcd = MathUtil.gcd(16384L, a, b);
        if ((double)yawChange > 0.9 && (double)pitchChange < 15.0 && gcd < 131072L) {
            if ((double)yawChange < 9.7 && (double)pitchChange > 0.05) {
                this.previousYaws.add(yawChange);
                if (vl++ > 17.0) {
                    if (MathUtil.averageFloat(this.previousYaws) > 0.0f) {
                        this.log(data, "Yaw Change -> " + yawChange + " Pitch Change -> " + pitchChange + " GCD -> " + gcd + " F-Average -> " + MathUtil.averageFloat(this.previousYaws));
                    } else {
                        this.vl = 0.0;
                        this.previousYaws.clear();
                    }
                }
            }
        } else if (this.vl > 0.0) {
            this.vl -= 1.0;
        }
        this.debug(data, "Yaw Change -> " + yawChange + " Pitch Change -> " + pitchChange + " GCD -> " + gcd + " F-Average -> " + MathUtil.averageFloat(this.previousYaws));
        this.previous = pitchChange;
    }
}

