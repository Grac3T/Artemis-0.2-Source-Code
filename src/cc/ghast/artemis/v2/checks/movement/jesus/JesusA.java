/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerMoveEvent
 */
package cc.ghast.artemis.v2.checks.movement.jesus;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

@Check
public class JesusA
extends AbstractCheck {
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
        this.debug(data, "liquid=" + data.user.isInLiquid() + " flyTicks=" + data.movement.getFlyTicks());
        if ((data.user.isAboveLiquid() || data.user.isInLiquid()) && data.movement.getFlyTicks() == 0) {
            this.quant += 5;
            if (this.quant > 20) {
                this.log(data, "liquid=" + data.user.isAboveLiquid() + " dist= flyTicks=" + data.movement.getFlyTicks());
            }
        }
    }
}

