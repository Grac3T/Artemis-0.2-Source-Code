/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.util.Vector
 */
package cc.ghast.artemis.v2.checks.combat.killaura;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;

public class KillAuraD
extends AbstractCheck
implements Listener {
    private HashMap<Player, Long> lastHit = new HashMap();

    public KillAuraD() {
        Artemis.INSTANCE.getPlugin().getServer().getPluginManager().registerEvents((Listener)this, (Plugin)Artemis.INSTANCE.getPlugin());
    }

    public void check(Player damager, Player damaged) {
        double angle = Math.atan2(damager.getLocation().getX() - damaged.getLocation().getX(), damager.getLocation().getZ() - damaged.getLocation().getZ());
        angle = -(angle / 3.141592653589793) * 360.0 / 2.0 + 180.0;
        double dirX = damager.getLocation().getDirection().getX();
        double dirY = damager.getLocation().getDirection().getY();
        double dirZ = damager.getLocation().getDirection().getZ();
        double dirAngle = Math.atan2(dirX - damaged.getLocation().getX(), damager.getLocation().getZ() - damaged.getLocation().getZ());
    }
}

