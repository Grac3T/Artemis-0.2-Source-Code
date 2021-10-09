/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.checks.combat.autoclicker;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInArmAnimationPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.utils.maths.EvictingDeque;
import cc.ghast.artemis.v2.utils.maths.EvictingLinkedList;
import java.util.function.ToDoubleFunction;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

@Check
public class AutoClickerL2
extends AbstractCheck {
    private double ticksArm;
    private double ticksFly;
    private EvictingLinkedList<Double> ticks = new EvictingLinkedList<Double>(20);

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInArmAnimationPacket) {
            this.ticksArm += 1.0;
        }
        if (packet instanceof WrappedInFlyingPacket) {
            double d = this.ticksFly;
            this.ticksFly = d + 1.0;
            if (d >= 10.0) {
                double tpt = this.ticksArm / this.ticksFly;
                if (tpt <= 0.2) {
                    return;
                }
                this.ticks.add(tpt);
                double kurtosis = new Kurtosis().evaluate(this.ticks.stream().mapToDouble(Number::doubleValue).toArray());
                double skew = new Skewness().evaluate(this.ticks.stream().mapToDouble(Number::doubleValue).toArray());
                double variance = new Variance().evaluate(this.ticks.stream().mapToDouble(Number::doubleValue).toArray());
                this.debug(data, "kurtosis=" + kurtosis + " skew=" + skew + " variance=" + variance);
                if (this.ticks.size() >= 20 && kurtosis < 0.0 && tpt > 0.0 && variance < 0.005 && skew > -0.05 && skew < 0.05) {
                    this.log(data, 1, "kurtosis=" + kurtosis + " skew=" + skew + " variance=" + variance);
                }
                this.ticksArm = 0.0;
                this.ticksFly = 0.0;
            }
        }
    }
}

