/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.combat.aimassist;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.utils.location.Rotation;

@Check
public class AimAssistK
extends AbstractCheck {
    private float lasYawDelta;
    private float lasPitchDelta;
    private int threshold;
    private double vl;
    private double streak;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInFlyingPacket && ((WrappedInFlyingPacket)packet).isLook() && ((WrappedInFlyingPacket)packet).isPos()) {
            if (data.movement.getRotation() == null || data.movement.getLastRotation() == null) {
                return;
            }
            Rotation to = data.movement.getRotation();
            Rotation from = data.movement.getLastRotation();
            float yawDelta = Math.abs(to.getYaw() - from.getYaw());
            float pitchDelta = Math.abs(to.getPitch() - from.getPitch());
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
            float magicVal = pitchDelta * 100.0f / this.lasPitchDelta;
            if (magicVal > 60.0f) {
                this.vl = Math.max(0.0, this.vl - 1.0);
                this.streak = Math.max(0.0, this.streak - 0.125);
            }
            if ((double)yawDelta > 0.0 && (double)pitchDelta > 0.0) {
                int roundedYaw = Math.round(yawDelta);
                int previousRoundedYaw = Math.round(this.lasYawDelta);
                float yawDeltaChange = Math.abs(yawDelta - this.lasYawDelta);
                if (roundedYaw == previousRoundedYaw && (double)yawDelta > 0.01 && yawDelta > 1.5f && (double)yawDeltaChange > 0.001 && (double)pitchDelta > 0.5 && pitchDelta <= 20.0f) {
                    if (vl++ > 1.0) {
                        // this is all skidded and I can't be asked to keep fixing his bullshit
                        if (this.streak++ > 6.0) {
                            this.log(data, "YDelta -> " + roundedYaw + " Change -> " + yawDeltaChange);
                        }
                    }
                } else {
                    this.vl = Math.max(0.0, this.vl - 1.0);
                }
            }
            this.debug(data, "[COMPLETE] pC=" + pitchDelta + " yD=" + yawDelta + " streak=" + this.streak);
            this.lasYawDelta = yawDelta;
            this.lasPitchDelta = pitchDelta;
        }
    }
}

