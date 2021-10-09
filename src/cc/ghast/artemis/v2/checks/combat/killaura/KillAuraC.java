/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.checks.combat.killaura;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import cc.ghast.artemis.v2.utils.misc.TimeUtil;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;

@Check
public class KillAuraC
extends AbstractCheck {
    private Map<UUID, Long> LastMS = new HashMap<UUID, Long>();
    private Map<UUID, List<Long>> Clicks = new HashMap<UUID, List<Long>>();
    private Map<UUID, Map.Entry<Integer, Long>> ClickTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();

    @Override
    public void handle(PlayerData data, NMSObject e) {
        if (e instanceof WrappedInUseEntityPacket) {
            Player damager = data.getPlayer();
            int Count = 0;
            long Time = System.currentTimeMillis();
            if (this.ClickTicks.containsKey(damager.getUniqueId())) {
                Count = this.ClickTicks.get(damager.getUniqueId()).getKey();
                Time = this.ClickTicks.get(damager.getUniqueId()).getValue();
            }
            if (this.LastMS.containsKey(damager.getUniqueId())) {
                long MS = TimeUtil.nowlong() - this.LastMS.get(damager.getUniqueId());
                if (MS > 500L || MS < 15L) {
                    this.LastMS.put(damager.getUniqueId(), TimeUtil.nowlong());
                    return;
                }
                if (this.Clicks.containsKey(damager.getUniqueId())) {
                    List<Long> Clicks = this.Clicks.get(damager.getUniqueId());
                    if (Clicks.size() == 10) {
                        this.Clicks.remove(damager.getUniqueId());
                        Collections.sort(Clicks);
                        long Range2 = Clicks.get(Clicks.size() - 1) - Clicks.get(0);
                        if (Range2 < 30L) {
                            ++Count;
                            Time = System.currentTimeMillis();
                        }
                    } else {
                        Clicks.add(MS);
                        this.Clicks.put(damager.getUniqueId(), Clicks);
                    }
                } else {
                    ArrayList<Long> Clicks = new ArrayList<Long>();
                    Clicks.add(MS);
                    this.Clicks.put(damager.getUniqueId(), Clicks);
                }
            }
            if (this.ClickTicks.containsKey(damager.getUniqueId()) && TimeUtil.elapsed(Time, 5000L)) {
                Count = 0;
                Time = TimeUtil.nowlong();
            }
            if (Count > 0) {
                Count = 0;
                this.log(data, "Experimental");
            }
            this.LastMS.put(damager.getUniqueId(), TimeUtil.nowlong());
            this.ClickTicks.put(damager.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
        }
    }
}

