/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.util.Vector
 */
package cc.ghast.artemis.v2.checks.combat.aimassist;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.utils.location.Rotation;
import org.bukkit.util.Vector;

@Check
public class AimAssistI
extends AbstractCheck {
    private int current = 0;
    private double multiplier = Math.pow(2.0, 24.0);
    private float lastPitch = -1.0f;
    private float lastYawDelta;
    private float lastPitchDelta;
    private long[] gcdLog = new long[10];

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInFlyingPacket && ((WrappedInFlyingPacket)packet).isLook() && ((WrappedInFlyingPacket)packet).isPos()) {
            long deviation;
            if (data.movement.getRotation() == null || data.movement.getLastRotation() == null) {
                return;
            }
            Rotation to = data.movement.getRotation();
            Rotation from = data.movement.getLastRotation();
            float yawDelta = Math.abs(to.getYaw() - from.getYaw());
            float pitchDelta = Math.abs(to.getPitch() - from.getPitch());
            float yawDifference = Math.abs(yawDelta - this.lastYawDelta);
            Vector first = new Vector(yawDelta, 0.0f, pitchDelta);
            Vector second = new Vector(this.lastYawDelta, 0.0f, this.lastPitchDelta);
            double angle = Math.pow(first.angle(second) * 180.0f, 2.0);
            boolean flagged = false;
            this.gcdLog[this.current % this.gcdLog.length] = deviation = this.getDeviation(pitchDelta);
            ++this.current;
            if (to.getPitch() > -20.0f && to.getPitch() < 20.0f && pitchDelta > 0.0f && yawDelta > 1.0f && yawDelta < 10.0f && this.lastYawDelta <= yawDelta && yawDifference != 0.0f && yawDifference < 1.0f && angle > 2500.0) {
                if (this.current > this.gcdLog.length) {
                    long maxDeviation = 0L;
                    for (long l : this.gcdLog) {
                        if (deviation == 0L || l == 0L) continue;
                        maxDeviation = Math.max(Math.max(l, deviation) % Math.min(l, deviation), maxDeviation);
                    }
                    if ((double)maxDeviation > 0.0) {
                        flagged = true;
                        this.log(data, "Deviation -> " + maxDeviation);
                        this.reset();
                    }
                }
                if ((double)deviation > 0.0) {
                    flagged = true;
                    this.log(data, "Extremely Experimental");
                    this.reset();
                }
            }
            this.lastPitchDelta = pitchDelta;
            this.lastYawDelta = yawDelta;
        }
    }

    private long getDeviation(float pitchChange) {
        long value;
        long current;
        long last;
        if (this.lastPitch != -1.0f && (value = this.convert(current = (long)((double)pitchChange * this.multiplier), last = (long)((double)this.lastPitch * this.multiplier))) < 131072L) {
            return value;
        }
        this.lastPitch = pitchChange;
        return -1L;
    }

    public void reset() {
        this.lastPitch = -1.0f;
    }

    private long convert(long current, long last) {
        if (last <= 16384L) {
            return current;
        }
        return this.convert(last, current % last);
    }
}

