/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package cc.ghast.artemis.v2.lag;

import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.utils.misc.ReflectionUtil;
import java.lang.reflect.Field;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class LagCore
implements Listener {
    public ArtemisPlugin artemis;
    private double tps;

    public LagCore(ArtemisPlugin artemis) {
        this.artemis = artemis;
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.artemis, new Runnable(){
            long sec;
            long currentSec;
            int ticks;

            @Override
            public void run() {
                this.sec = System.currentTimeMillis() / 1000L;
                if (this.currentSec == this.sec) {
                    ++this.ticks;
                } else {
                    this.currentSec = this.sec;
                    LagCore.this.tps = LagCore.this.tps == 0.0 ? (double)this.ticks : (LagCore.this.tps + (double)this.ticks) / 2.0;
                    this.ticks = 0;
                }
            }
        }, 0L, 1L);
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)artemis);
    }

    public double getTPS() {
        return this.tps + 1.0 > 20.0 ? 20.0 : this.tps + 1.0;
    }

    public static int getPing(Player player) {
        Object handle = ReflectionUtil.getPlayerHandle(player);
        if (handle != null) {
            Field ping = ReflectionUtil.getField("ping", handle.getClass());
            try {
                return ping.getInt(handle);
            }
            catch (Exception e) {
                return -1;
            }
        }
        return -1;
    }

}

