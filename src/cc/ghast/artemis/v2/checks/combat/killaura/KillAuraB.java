/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.checks.combat.killaura;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@Check
public class KillAuraB
extends AbstractCheck {
    private int verbose;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInUseEntityPacket) {
            Player player = data.getPlayer();
            Entity entity = ((WrappedInUseEntityPacket)packet).getEntity();
            if (entity != null && !player.hasLineOfSight(entity)) {
                if (this.verbose++ > 3) {
                    this.log(data, "vb=" + this.verbose);
                }
            } else {
                --this.verbose;
            }
        }
    }
}

