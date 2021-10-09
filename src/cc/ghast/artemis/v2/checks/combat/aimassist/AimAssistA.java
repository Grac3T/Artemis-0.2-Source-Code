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
public class AimAssistA
extends AbstractCheck {
    private double vl;
    private double streak;
    private double oldPitch;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInFlyingPacket && ((WrappedInFlyingPacket)packet).isLook() && ((WrappedInFlyingPacket)packet).isPos()) {
            float pitchChange = this.getDistanceBetweenAngles(data.movement.getRotation().getPitch(), data.movement.getLastRotation().getPitch());
            float yawChange = this.getDistanceBetweenAngles(data.movement.getRotation().getYaw(), data.movement.getLastRotation().getYaw());
            double pitchDifference = this.oldPitch - (double)pitchChange;
            if (yawChange >= 2.0f) {
                if ((double)pitchChange + pitchDifference == 0.0 && (double)pitchChange < 0.07 && pitchChange > 0.0f) {
                    double d = 0; // idfk these checks are ass any where
                    this.vl += 1.0;
                    this.streak = d > 1.0 ? (this.streak += 1.0) : Math.max(0.0, this.streak - 0.25);
                    if (this.streak > 1.0) {
                        this.log(data, "Drip's AimAssist");
                        this.streak = 0.0;
                    }
                } else {
                    this.vl = Math.max(0.0, this.vl - 0.5);
                    this.streak = Math.max(0.0, this.streak - 0.25);
                }
            }
            this.oldPitch = pitchChange;
        }
    }

    private float getDistanceBetweenAngles(float angle1, float angle2) {
        float distance = Math.abs(angle1 - angle2) % 360.0f;
        if (distance > 180.0f) {
            distance = 360.0f - distance;
        }
        return distance;
    }
}

