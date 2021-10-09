/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerMoveEvent
 */
package cc.ghast.artemis.v2.checks.movement.speed;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

@Check
public class SpeedD
extends AbstractCheck {
    private double lastDist;
    private double lastDelta;
    private long lastMove;
    private boolean lastOnGround;
    private int quant;

    @Override
    public void handle(PlayerData data, PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getAllowFlight() || player.isFlying() || player.isInsideVehicle() || player.isSneaking()) {
            return;
        }
        if (!player.getGameMode().equals((Object)GameMode.SURVIVAL) && !player.getGameMode().equals((Object)GameMode.ADVENTURE)) {
            return;
        }
        if (data.hasBeenDamaged()) {
            return;
        }
        if (data.wasOnSlime()) {
            return;
        }
        double distanceX = event.getFrom().getX() - event.getTo().getX();
        double distanceZ = event.getFrom().getZ() - event.getTo().getZ();
        double dist = Math.sqrt(Math.pow(distanceX, 2.0) + Math.pow(distanceZ, 2.0));
        long move = System.currentTimeMillis();
        double deltaDist = Math.abs(this.lastDist - dist);
        double acceleration = Math.abs(deltaDist - this.lastDelta) / Math.abs(deltaDist / this.lastDelta);
        double accel = acceleration * Math.pow(2.0, 24.0);
        this.debug(data, "dist=" + dist + " delta=" + deltaDist + " accel=" + acceleration + " flyTicks=" + data.movement.getFlyTicks());
        this.lastDist = dist;
        this.lastDelta = deltaDist;
        this.lastMove = move;
    }
}

