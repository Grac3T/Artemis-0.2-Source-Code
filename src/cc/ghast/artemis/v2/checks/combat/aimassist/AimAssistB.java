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
import cc.ghast.artemis.v2.utils.location.Rotation;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

@Check
public class AimAssistB
extends AbstractCheck
implements Listener {
    private Map<Player, Float> yaw = new WeakHashMap<Player, Float>();
    private Map<Player, Long> lastAttack = new WeakHashMap<Player, Long>();

    public AimAssistB() {
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)Artemis.INSTANCE.getPlugin());
    }

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        Player player;
        if (packet instanceof WrappedInFlyingPacket && ((WrappedInFlyingPacket)packet).isLook() && ((WrappedInFlyingPacket)packet).isPos() && this.lastAttack.containsKey((Object)(player = data.getPlayer())) && System.currentTimeMillis() - this.lastAttack.get((Object)player) >= TimeUnit.SECONDS.toMillis(8L)) {
            float yawMovement = Math.abs(data.movement.getRotation().getYaw() - data.movement.getLastRotation().getYaw());
            if (yawMovement - (float)((int)yawMovement) != 0.0f) {
                this.log(data, String.valueOf(yawMovement));
            }
            this.yaw.put(player, Float.valueOf(yawMovement));
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            this.lastAttack.put((Player)event.getDamager(), System.currentTimeMillis());
        }
    }
}

