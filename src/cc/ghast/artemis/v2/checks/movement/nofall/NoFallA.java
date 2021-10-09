/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.checks.movement.nofall;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.utils.location.Position;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@Check(maxVls=5)
public class NoFallA
extends AbstractCheck {
    private double vl;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        Player player = data.getPlayer();
        Position from = data.getMovement().getLocation();
        Position to = data.getMovement().getLastLocation();
        if (!(data.isLagging() || player.getNoDamageTicks() != 0 || player.getVehicle() != null || player.isDead() || player.getGameMode().equals((Object)GameMode.CREATIVE))) {
            double yDiff = from.getY() - to.getY();
            if (player.isOnGround() && yDiff > 0.8) {
                if (vl++ > 4.0) {
                    this.log(data, "ydiff=" + yDiff);
                }
            }
        }
    }
}

