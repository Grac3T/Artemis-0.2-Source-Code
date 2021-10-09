/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.EvictingQueue
 */
package cc.ghast.artemis.v2.checks.combat.autoclicker;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.check.annotations.Experimental;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInArmAnimationPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInBlockDigPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInBlockPlacePacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.utils.maths.EvictingDeque;
import cc.ghast.artemis.v2.utils.maths.EvictingLinkedList;
import cc.ghast.artemis.v2.utils.misc.TimeUtil;
import com.google.common.collect.EvictingQueue;
import java.util.Queue;
import java.util.function.ToDoubleFunction;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

@Check
@Experimental
public class AutoClickerK
extends AbstractCheck {
    private Queue<Long> armTicks = EvictingQueue.create((int)50);
    private long previousMillis;
    private int streak;
    private double previousKurtosis;
    private double previousVariance;
    private EvictingLinkedList<Long> ticks = new EvictingLinkedList<Long>(50);
    private double ticksArm;
    private double ticksFly;
    private double lastSkew;
    private EvictingLinkedList<Double> ticksS = new EvictingLinkedList<Double>(20);

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInArmAnimationPacket) {
            this.ticksArm += 1.0;
            this.armTicks.add(System.currentTimeMillis() - this.previousMillis);
            this.ticks.add(System.currentTimeMillis());
            if (this.armTicks.size() >= 20) {
                double skewness = Math.abs(new Skewness().evaluate(this.armTicks.stream().mapToDouble(Number::doubleValue).toArray()));
                double kurtosis = new Kurtosis().evaluate(this.armTicks.stream().mapToDouble(Number::doubleValue).toArray());
                double variance = new Variance().evaluate(this.armTicks.stream().mapToDouble(Number::doubleValue).toArray());
                double kurtosisDelta = Math.abs(kurtosis - this.previousKurtosis);
                double varianceDelta = Math.abs(variance - this.previousVariance);
                if (variance > 200.0 && variance < 1000.0 && skewness > 0.1 && skewness < 1.0 && kurtosisDelta < 0.2 && TimeUtil.convert(Math.abs(this.ticks.getFirst() - this.ticks.getLast()), 1, TimeUtil.TimeUnits.SECONDS) < 7.14) {
                    if (this.lastSkew < -0.4 && this.streak++ > 5) {
                        this.log(data, "skew=" + skewness + " kurtDelta=" + kurtosisDelta + " varDelta=" + variance + " time=" + TimeUtil.convert(Math.abs(this.ticks.getFirst() - this.ticks.getLast()), 1, TimeUtil.TimeUnits.SECONDS));
                    }
                } else if (this.streak > 0) {
                    --this.streak;
                }
                this.debug(data, "skew=" + skewness + " kurtDelta=" + kurtosisDelta + " varDelta=" + variance + " time=" + TimeUtil.convert(Math.abs(this.ticks.getFirst() - this.ticks.getLast()), 1, TimeUtil.TimeUnits.SECONDS) + " streak=" + this.streak);
                this.previousKurtosis = kurtosis;
                this.previousVariance = variance;
            }
            this.previousMillis = System.currentTimeMillis();
        }
        if (packet instanceof WrappedInBlockDigPacket || packet instanceof WrappedInBlockPlacePacket) {
            this.armTicks.clear();
        }
        if (packet instanceof WrappedInFlyingPacket) {
            double d = this.ticksFly;
            this.ticksFly = d + 1.0;
            if (d >= 20.0) {
                double tpt = this.ticksArm / this.ticksFly;
                this.ticksS.add(tpt);
                if (this.ticksS.size() >= 19) {
                    this.lastSkew = new Skewness().evaluate(this.ticksS.stream().mapToDouble(Number::doubleValue).toArray());
                }
                this.ticksArm = 0.0;
                this.ticksFly = 0.0;
            }
        }
    }
}

