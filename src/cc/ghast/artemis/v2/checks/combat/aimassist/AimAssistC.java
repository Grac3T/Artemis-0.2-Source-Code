/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.plugin.Plugin
 */
package cc.ghast.artemis.v2.checks.combat.aimassist;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import cc.ghast.artemis.v2.utils.location.Position;
import cc.ghast.artemis.v2.utils.location.Rotation;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

@Check
public class AimAssistC
extends AbstractCheck
implements Listener {
    private float yaw = 0.0f;
    private long lastAttack = 0L;

    public AimAssistC() {
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)Artemis.INSTANCE.getPlugin());
    }

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        Player player = data.getPlayer();
        if (packet instanceof WrappedInFlyingPacket && ((WrappedInFlyingPacket)packet).isPos() && ((WrappedInFlyingPacket)packet).isLook() && this.lastAttack != 0L && System.currentTimeMillis() - this.lastAttack >= TimeUnit.SECONDS.toMillis(8L)) {
            float yawMovement = Math.abs(data.movement.getRotation().getPitch() - data.movement.getLastLocation().getPitch());
            if (yawMovement - (float)((int)yawMovement) != 0.0f) {
                this.log(data, String.valueOf(yawMovement));
            }
            this.yaw = yawMovement;
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            this.lastAttack = System.currentTimeMillis();
        }
    }
}

