/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.event.player.PlayerMoveEvent
 */
package cc.ghast.artemis.v2.checks.movement.abnormal;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.utils.MathUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

@Check
public class AbnormalMovementA
extends AbstractCheck {
    private double vl;
    private double multiplier = Math.pow(2.0, 24.0);
    private float previous;
    private List<Float> previousYaws = new ArrayList<Float>();
    private float previousPitchChange = 0.0f;
    private float previousYawChange = 0.0f;
    private boolean flagged;
    private int count;

    @Override
    public void handle(PlayerData data, PlayerMoveEvent e) {
        Location to = e.getTo();
        Location from = e.getFrom();
        float pitchChange = Math.abs(to.getPitch() - from.getPitch());
        float yawChange = Math.abs(to.getYaw() - from.getYaw());
        float pitchChangeDelta = Math.abs(pitchChange - this.previousPitchChange);
        float yawChangeDelta = Math.abs(yawChange - this.previousYawChange);
        float pitchYawCoef = Math.abs(pitchChange % yawChange);
        float previousPitchYawCoef = Math.abs(this.previousPitchChange % this.previousYawChange);
        float pycDelta = Math.abs(pitchYawCoef - previousPitchYawCoef);
        long a = (long)((double)pitchChange * this.multiplier);
        long b = (long)((double)this.previousPitchChange * this.multiplier);
        long pGCD = MathUtil.gcd(16384L, a, b);
        long c = (long)((double)yawChange * this.multiplier);
        long d = (long)((double)this.previousYawChange * this.multiplier);
        long yGCD = MathUtil.gcd(16384L, c, d);
        long g = (long)((double)pitchYawCoef * this.multiplier);
        long h = (long)((double)previousPitchYawCoef * this.multiplier);
        long coefGCD = MathUtil.gcd(16384L, g, h);
        if ((double)yawChange > 0.9 && (double)pitchChange < 15.0 && (double)pitchChangeDelta > 0.5 && yGCD < 131072L && pGCD < 131072L && pycDelta > 1.0f) {
            if (this.count++ < 20) {
                this.log(data, "pycDelta=" + pycDelta + " P GCD=" + pGCD + " Y GCD -> " + yGCD);
                this.flagged = true;
            } else {
                this.count -= 5;
            }
        }
        this.debug(data, (this.flagged ? "[FLAGGED]" : "") + "yC=" + yawChange + " pY=" + pitchChange + " PY=" + pitchYawCoef + " Y Delta=" + yawChangeDelta + " P Delta=" + pitchChangeDelta + " pycDelta=" + pycDelta + " P GCD=" + pGCD + " Y GCD=" + yGCD + " Coef GCD=" + coefGCD);
        this.previous = pitchChange;
        this.previousPitchChange = pitchChange;
        this.previousYawChange = yawChange;
        this.flagged = false;
    }
}

