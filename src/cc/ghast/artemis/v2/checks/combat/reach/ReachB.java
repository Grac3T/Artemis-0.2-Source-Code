/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerMoveEvent
 */
package cc.ghast.artemis.v2.checks.combat.reach;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.api.API;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.check.annotations.Experimental;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import cc.ghast.artemis.v2.managers.PlayerDataManager;
import cc.ghast.artemis.v2.utils.location.Position;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

@Check
@Experimental
public class ReachB
extends AbstractCheck {
    private double lastMovement;

    @Override
    public void handle(PlayerData playerData, NMSObject packet) {
        if (packet instanceof WrappedInUseEntityPacket) {
            Player attacker = playerData.getPlayer();
            Entity entity = ((WrappedInUseEntityPacket)packet).getEntity();
            if (attacker.getGameMode() == GameMode.CREATIVE || ((WrappedInUseEntityPacket)packet).getAction() != WrappedInUseEntityPacket.EnumEntityUseAction.ATTACK) {
                return;
            }
            boolean isPlayer = entity instanceof Player;
            PlayerData data = isPlayer ? Artemis.INSTANCE.getApi().getPlayerDataManager().getData((Player)entity) : null;
            Location location = isPlayer && data != null ? data.movement.getLastLocation().getBukkitLocation() : entity.getLocation();
            Location loc2 = playerData.movement.getLastLocation() != null ? playerData.movement.getLastLocation().getBukkitLocation() : attacker.getLocation();
            double range = this.calcDistance(loc2, location == null ? entity.getLocation() : location);
            double expected = playerData.hasJumped() || (double)System.currentTimeMillis() - this.lastMovement < 200.0 || !playerData.hasAttacked() ? (isPlayer ? 4.3 : 4.5) : (expected = isPlayer ? 3.5 : 3.6);
            if (attacker.getMaximumNoDamageTicks() < 16) {
                expected += 2.0;
            }
            if (range >= expected) {
                this.log(playerData, "Pingless - Range: " + range + " -> " + expected);
                this.debug(playerData, "[FLAGGED] Distance ->" + range + " Expected -> " + expected);
            } else {
                this.debug(playerData, "Distance ->" + range + " Expected -> " + expected);
            }
        }
    }

    @Override
    public void handle(PlayerData playerData, PlayerMoveEvent e) {
        if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()) {
            this.lastMovement = System.currentTimeMillis();
        }
    }

    private double calcDistance(Location location, Location loc) {
        double disX = Math.abs(location.getX() - loc.getX());
        double disZ = Math.abs(location.getZ() - loc.getZ());
        return Math.sqrt(disX * disX + disZ * disZ);
    }
}

