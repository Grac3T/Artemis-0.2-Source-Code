/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.checks.misc.badpackets;

import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInFlyingPacket;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@Check
public class BadPacketsC
extends AbstractCheck {
    private int streak;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (data.getPlayer().getVehicle() == null && packet instanceof WrappedInFlyingPacket) {
            if (((WrappedInFlyingPacket)packet).isPos()) {
                this.streak = 0;
            } else if (++this.streak > 20) {
                this.log(data, 1, new String[0]);
            }
        }
    }
}

