/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerMoveEvent
 */
package cc.ghast.artemis.v2.checks.combat.velocity;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.utils.MathUtil;
import cc.ghast.artemis.v2.utils.location.Velocity;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

@Check
public class VelocityB
extends AbstractCheck {
    private double vl;

    @Override
    public void handle(PlayerData data, PlayerMoveEvent packet) {
        Player player = data.getPlayer();
        Location to = packet.getTo();
        Location from = packet.getFrom();
        if (data.movement.getVelocity() == null) {
            return;
        }
        if (player.isOnGround() && data.movement.isWasOnGround() && data.movement.getVelocity().getVertical() > 0.0 && to.getY() > from.getY()) {
            double deltaY = to.getY() - from.getY();
            double verticalVelocity = data.movement.getVelocity().getVertical();
            double percentage = (verticalVelocity - deltaY) / verticalVelocity * 100.0;
            if (percentage > 1.1) {
                this.log(data, "Velocity A", "pct=" + MathUtil.round(percentage, 3));
            }
        }
    }
}

