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
public class ReachC
extends AbstractCheck {
    private double lastMovement;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInUseEntityPacket) {
            double expected;
            Player attacker = data.getPlayer();
            Entity entity = ((WrappedInUseEntityPacket)packet).getEntity();
            WrappedInUseEntityPacket.EnumEntityUseAction action = ((WrappedInUseEntityPacket)packet).getAction();
            if (attacker.getGameMode() == GameMode.CREATIVE || action != WrappedInUseEntityPacket.EnumEntityUseAction.ATTACK) {
                return;
            }
            if (!(entity instanceof Player)) {
                return;
            }
            Player attacked = (Player)entity;
            PlayerData attackedData = Artemis.INSTANCE.getApi().getPlayerDataManager().getData(attacked);
            if (attackedData.getPlayer() == null) {
                return;
            }
            Location location = attackedData != null ? attackedData.movement.getLastLocation().getBukkitLocation() : entity.getLocation();
            Location loc2 = data.movement.getLastLocation() != null ? data.movement.getLastLocation().getBukkitLocation() : attacker.getLocation();
            double d = expected = data.hasJumped() || (double)System.currentTimeMillis() - this.lastMovement < 200.0 || !data.hasAttacked() ? 3.3 : 3.1;
            if (attacker.getMaximumNoDamageTicks() < 16) {
                expected += 2.0;
            }
        }
    }

    @Override
    public void handle(PlayerData data, PlayerMoveEvent e) {
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

