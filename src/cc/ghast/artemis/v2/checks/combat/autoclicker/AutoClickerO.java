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
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

@Check
public class AutoClickerO
extends AbstractCheck {
    private EvictingLinkedList<Double> rates = new EvictingLinkedList<Double>(100);
    private double ticks;
    private int verbose;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInArmAnimationPacket) {
            if (data.user.isDigging() || data.user.isPlaced() || !TimeUtil.hasExpired(data.user.getLastDig(), 3L)) {
                return;
            }
            this.rates.add(this.ticks);
            this.ticks = 0.0;
            if (this.rates.size() >= 100) {
                DescriptiveStatistics stats = new DescriptiveStatistics(this.rates.stream().mapToDouble(Number::doubleValue).toArray());
                double kurtosis = stats.getKurtosis();
                double sum = stats.getSum();
                this.debug(data, "k=" + kurtosis + " vb=" + this.verbose + " sum=" + sum);
                if (kurtosis < -0.05 && sum > 200.0) {
                    if (this.verbose++ > 3) {
                        this.log(data, "k=" + kurtosis + " vb=" + this.verbose + " sum=" + sum);
                    }
                } else if (this.verbose > 0) {
                    --this.verbose;
                }
            }
        } else if (packet instanceof WrappedInFlyingPacket) {
            if (data.user.isDigging() || data.user.isPlaced() || !TimeUtil.hasExpired(data.user.getLastDig(), 3L)) {
                return;
            }
            this.ticks += 1.0;
        }
    }
}

