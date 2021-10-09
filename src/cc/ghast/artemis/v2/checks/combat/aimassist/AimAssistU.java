/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.combat.aimassist;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.utils.MathUtil;
import cc.ghast.artemis.v2.utils.location.Position;

@Check
public class AimAssistU
extends AbstractCheck {
    private double multiplier = Math.pow(2.0, 24.0);
    private float previous;
    private double vl;
    private double streak;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInFlyingPacket && ((WrappedInFlyingPacket)packet).isLook()) {
            if (data.movement.getLocation() == null || data.movement.getLastLocation() == null) {
                this.debug(data, "lastRotation is null");
            }
            Position to = data.movement.getLocation();
            Position from = data.movement.getLastLocation();
            if (System.currentTimeMillis() - data.combat.getLastAttack() >= 2000L) {
                this.vl = 0.0;
                this.streak = 0.0;
                this.debug(data, "[FAILED] LastAttack=" + data.combat.getLastAttack());
                return;
            }
            if (data.movement.getTeleportTicks() > 0 || data.movement.getRespawnTicks() > 0 || data.movement.getStandTicks() > 0) {
                this.vl = 0.0;
                this.debug(data, "[FAILED] tpTicks=" + data.movement.getTeleportTicks() + " rpTicks=" + data.movement.getRespawnTicks() + " stdTicks=" + data.movement.getStandTicks());
                return;
            }
            float pitchChange = MathUtil.getDistanceBetweenAngles(to.getPitch(), from.getPitch());
            long a = (long)((double)pitchChange * this.multiplier);
            long b = (long)((double)this.previous * this.multiplier);
            long gcd = this.gcd(a, b);
            float sexy = pitchChange * 100.0f / this.previous;
            if (sexy > 60.0f) {
                this.vl = Math.max(0.0, this.vl - 1.0);
                this.streak = Math.max(0.0, this.streak - 0.125);
            }
            if ((double)pitchChange > 0.5 && pitchChange <= 20.0f && gcd < 131072L) {
                if(++this.vl > 1.0D) {
                    ++this.streak;
                }

                if(this.streak > 6.0D) {
                    this.log(data, 2, new String[0]);
                }
            } else {
                this.vl = Math.max(0.0, this.vl - 1.0);
            }
            this.debug(data, "[COMPLETE] pC=" + pitchChange + " gcd=" + gcd + " streak=" + this.streak);
            this.previous = pitchChange;
        }
    }

    private long gcd(long a, long b) {
        if (b <= 16384L) {
            return a;
        }
        return this.gcd(b, a % b);
    }
}

