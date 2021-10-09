/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.checks.combat.autoclicker;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import cc.ghast.artemis.v2.utils.MathUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;

@Check
public class AutoClickerB
extends AbstractCheck {
    private Map<UUID, List<Long>> previousClicks = new HashMap<UUID, List<Long>>();

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInUseEntityPacket) {
            Player attacker = data.getPlayer();
            if (this.previousClicks.containsKey(attacker.getUniqueId())) {
                List<Long> timestamps = this.previousClicks.get(attacker.getUniqueId());
                timestamps.add(System.currentTimeMillis());
                this.previousClicks.put(attacker.getUniqueId(), timestamps);
                if (MathUtil.getRange(this.previousClicks.get(attacker.getUniqueId())) > 1000L) {
                    long average = this.previousClicks.get(attacker.getUniqueId()).size();
                    if (average > 9L) {
                        List<Long> clicks = this.previousClicks.get(attacker.getUniqueId());
                        ArrayList<Long> intervals = new ArrayList<Long>();
                        for (int i = 0; i < clicks.size() - 1; ++i) {
                            long interval = clicks.get(i + 1) - clicks.get(i);
                            intervals.add(interval);
                        }
                        if (MathUtil.averageLong(intervals) < 10L) {
                            this.log(data, "Experimental");
                        }
                    }
                    this.previousClicks.remove(attacker.getUniqueId());
                }
            } else {
                ArrayList<Long> timestamps = new ArrayList<Long>();
                timestamps.add(System.currentTimeMillis());
                this.previousClicks.put(attacker.getUniqueId(), timestamps);
            }
        }
    }
}

