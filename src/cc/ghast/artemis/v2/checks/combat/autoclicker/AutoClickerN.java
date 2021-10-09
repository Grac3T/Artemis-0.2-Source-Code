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
import cc.ghast.artemis.v2.utils.misc.TimeUtil;
import java.util.function.ToDoubleFunction;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;

@Check
public class AutoClickerN
extends AbstractCheck {
    private EvictingLinkedList<Double> rates = new EvictingLinkedList<Double>(50);
    private double swings;
    private double ticks;
    private int verbose;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInArmAnimationPacket) {
            if (data.user.isDigging() || data.user.isPlaced() || !TimeUtil.hasExpired(data.user.getLastDig(), 3L)) {
                return;
            }
            this.swings += 1.0;
            if (this.ticks > 10.0) {
                double swingRate = this.ticks / this.swings;
                this.rates.add(swingRate);
                double skewness = new Skewness().evaluate(this.rates.stream().mapToDouble(Number::doubleValue).toArray());
                double kurtosis = new Kurtosis().evaluate(this.rates.stream().mapToDouble(Number::doubleValue).toArray());
                if (this.rates.size() < 2) {
                    return;
                }
                double covariance = new Covariance().covariance(this.rates.stream().mapToDouble(Number::doubleValue).toArray(), this.rates.stream().mapToDouble(Number::doubleValue).toArray(), true);
                this.debug(data, "s=" + skewness + " sM=" + kurtosis + " cV=" + covariance);
                if (swingRate < 1.1) {
                    if (this.verbose++ > 2) {
                        this.log(data, swingRate + "");
                    }
                } else if (this.verbose != 0) {
                    --this.verbose;
                }
            }
            if (this.swings > 100.0) {
                this.swings = 0.0;
                this.ticks = 0.0;
            }
        } else if (packet instanceof WrappedInFlyingPacket) {
            if (data.user.isDigging() || data.user.isPlaced() || !TimeUtil.hasExpired(data.user.getLastDig(), 3L)) {
                return;
            }
            this.ticks += 1.0;
        }
    }
}

