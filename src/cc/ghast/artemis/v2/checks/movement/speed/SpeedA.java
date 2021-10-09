/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerMoveEvent
 */
package cc.ghast.artemis.v2.checks.movement.speed;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

@Check
public class SpeedA
extends AbstractCheck
implements Listener {
    private double lastDist;
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
        double dist = distanceX * distanceX + distanceZ * distanceZ;
        double lastDist = 0.0;
        boolean onGround = player.isOnGround();
        lastDist = this.lastDist;
        boolean lastOnGround = this.lastOnGround;
        float friction = 0.91f;
        double shiftedLastDist = lastDist * (double)friction;
        double expected = dist - shiftedLastDist;
        double result = expected * 137.0;
        if (!onGround && !lastOnGround && result >= 1.2) {
            if (this.quant < 20) {
                this.log(data, Double.toString(result));
            } else {
                this.quant -= 20;
            }
        }
        this.lastOnGround = onGround;
        this.lastDist = dist;
        ++this.quant;
    }
}

