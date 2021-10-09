/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.checks.combat.reach;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.api.API;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import cc.ghast.artemis.v2.lag.LagCore;
import cc.ghast.artemis.v2.managers.PlayerDataManager;
import cc.ghast.artemis.v2.utils.boundingbox.BoundingBox;
import cc.ghast.artemis.v2.utils.location.Position;
import cc.ghast.artemis.v2.utils.maths.EvictingDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.ToDoubleFunction;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@Check
public class ReachE
extends AbstractCheck {
    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInUseEntityPacket && ((WrappedInUseEntityPacket)packet).entity instanceof Player && ((WrappedInUseEntityPacket)packet).action.equals((Object)WrappedInUseEntityPacket.EnumEntityUseAction.ATTACK)) {
            Player player = (Player)((WrappedInUseEntityPacket)packet).entity;
            PlayerData data1 = Artemis.INSTANCE.getApi().getPlayerDataManager().getData(player);
            if (player.getGameMode() == GameMode.CREATIVE) return;

            ArrayList<Position> p1pos = new ArrayList<>(data.movement.getPositions());
            ArrayList<Position> p2pos = new ArrayList<>(data1.movement.getPositions());
            if (p1pos.size() >= 25 && p2pos.size() >= 25) {
                double smallestDistance = Double.MAX_VALUE;
                ArrayList<Double> distances = new ArrayList<>();
                for (int i = 0; i < 25; ++i) {
                    Position one = p1pos.get(i);
                    Position two = p2pos.get(i);
                    BoundingBox boxAtt = new BoundingBox(one, System.currentTimeMillis());
                    BoundingBox boxAttk = new BoundingBox(two, System.currentTimeMillis());
                    double distance = boxAtt.distanceXZ(boxAttk);
                    distances.add(distance);
                    if (!(distance < smallestDistance)) continue;
                    smallestDistance = distance;
                }

                double mean = new Mean().evaluate(distances.stream().mapToDouble(Number::doubleValue).toArray());

                if (mean > 4.1 && LagCore.getPing(player) < 150 && !data.isLagging()) {
                    this.log(data, "opponent=" + player.getName() + " mean=" + mean + " smallest=" + smallestDistance);
                }
                this.debug(data, "opponent=" + player.getName() + " mean=" + mean + " smallest=" + smallestDistance);
            } else {
                this.debug(data, "opponent=" + player.getName() + " p1pos=" + p1pos.size() + " p2pos=" + p2pos.size());
            }
        }
    }
}

