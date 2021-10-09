/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Difficulty
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityRegainHealthEvent
 *  org.bukkit.event.entity.EntityRegainHealthEvent$RegainReason
 *  org.bukkit.plugin.Plugin
 */
package cc.ghast.artemis.v2.checks.player.regen;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.plugin.Plugin;

@Check
public class RegenA
extends AbstractCheck
implements Listener {
    private Map<UUID, Long> lastHeal = new HashMap<UUID, Long>();

    public RegenA() {
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)Artemis.INSTANCE.getPlugin());
    }

    @EventHandler
    public void onPacket(PlayerData data, EntityRegainHealthEvent event) {
        if (!event.getRegainReason().equals((Object)EntityRegainHealthEvent.RegainReason.SATIATED)) {
            return;
        }
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player)event.getEntity();
        UUID uuid = player.getUniqueId();
        if (player.getWorld().getDifficulty().equals((Object)Difficulty.PEACEFUL)) {
            return;
        }
        if (this.lastHeal.containsKey(uuid)) {
            long healOffset = System.currentTimeMillis() - this.lastHeal.get(uuid);
            if (healOffset < 3500L) {
                this.log(data, "Offset: " + healOffset);
            } else {
                this.lastHeal.put(player.getUniqueId(), System.currentTimeMillis());
            }
        } else {
            this.lastHeal.put(player.getUniqueId(), System.currentTimeMillis());
        }
    }
}

