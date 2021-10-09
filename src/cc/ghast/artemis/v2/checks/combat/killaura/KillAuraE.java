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
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInArmAnimationPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import java.util.HashMap;
import org.bukkit.entity.Player;

@Check
public class KillAuraE
extends AbstractCheck {
    private HashMap<Player, Long> lastHit = new HashMap();

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        Player player;
        if (packet instanceof WrappedInUseEntityPacket) {
            this.lastHit.put(data.getPlayer(), System.currentTimeMillis());
        } else if (packet instanceof WrappedInArmAnimationPacket && this.lastHit.containsKey((Object)(player = data.getPlayer())) && System.currentTimeMillis() - this.lastHit.get((Object)player) < 150L && this.lastHit.get((Object)player) < System.currentTimeMillis()) {
            this.log(data, Long.toString(System.currentTimeMillis() - this.lastHit.get((Object)player)));
        }
    }
}

