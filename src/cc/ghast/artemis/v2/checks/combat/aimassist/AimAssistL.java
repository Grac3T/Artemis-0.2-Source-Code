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
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Stream;

@Check
public class AimAssistL
extends AbstractCheck {
    private final Deque<Float> pitchSamples = new LinkedList<Float>();
    private int minimumDuplicates = 9;
    private int minimumStreak = 2;
    private int streak;

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
            if (yawDelta > 1.0f && (double)pitchDelta > 0.0) {
                this.pitchSamples.add(Float.valueOf(pitchDelta));
                if (this.pitchSamples.size() == 120) {
                    long distinct = this.pitchSamples.stream().distinct().count();
                    long duplicates = (long)this.pitchSamples.size() - distinct;
                    if (duplicates <= (long)this.minimumDuplicates) {
                        if (++this.streak >= this.minimumStreak) {
                            this.log(data, "Duplicates-> " + duplicates + " streak ->" + this.streak);
                        }
                    } else {
                        this.streak = 0;
                    }
                    this.pitchSamples.clear();
                }
            }
        }
    }
}

