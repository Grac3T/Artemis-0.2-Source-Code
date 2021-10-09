/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.checks.combat.reach;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import cc.ghast.artemis.v2.lag.LagCore;
import cc.ghast.artemis.v2.utils.location.Position;
import cc.ghast.artemis.v2.utils.misc.PositionUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;

@Check
public class ReachD
extends AbstractCheck {
    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInUseEntityPacket && ((WrappedInUseEntityPacket)packet).entity instanceof Player && ((WrappedInUseEntityPacket)packet).action.equals((Object)WrappedInUseEntityPacket.EnumEntityUseAction.ATTACK)) {
            Player player = (Player)((WrappedInUseEntityPacket)packet).entity;
            PlayerData data1 = Artemis.INSTANCE.getApi().getPlayerDataManager().getData(player);
            if (player.getGameMode() == GameMode.CREATIVE) return;

            ArrayList<Position> p1pos = new ArrayList<Position>(data.movement.getPositions());
            ArrayList<Position> p2pos = new ArrayList<Position>(data1.movement.getPositions());
            if (p1pos.size() >= 25 && p2pos.size() >= 25) {
                Position nearestAttacker = PositionUtil.getNearestPost(p1pos, System.currentTimeMillis() - (150L + (long)LagCore.getPing(data.getPlayer())));
                Position nearestAttacked = PositionUtil.getNearestPost(p2pos, System.currentTimeMillis() - (150L + (long)LagCore.getPing(data.getPlayer())));
                double distance = nearestAttacked.getBukkitLocation().distance(nearestAttacker.getBukkitLocation());
                double center1X = data.movement.getLocation().getCenterX();
                double center1Z = data.movement.getLocation().getCenterZ();
                double center2X = data1.movement.getLastLocation().getCenterX();
                double center2Z = data1.movement.getLastLocation().getCenterZ();
                double center = Math.sqrt(Math.pow(center2X - center1X, 2.0) + Math.pow(center2Z - center1Z, 2.0));
                if (center > 5.0 && LagCore.getPing(player) < 150 && !data.isLagging()) {
                    this.log(data, "opponent=" + player.getName() + " reachedDebug=" + center);
                }
                this.debug(data, "opponent=" + player.getName() + " reachedDebug=" + center);
            } else {
                this.debug(data, "opponent=" + player.getName() + " p1pos=" + p1pos.size() + " p2pos=" + p2pos.size());
            }
        }
    }
}

