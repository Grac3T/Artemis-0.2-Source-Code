/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.checks.combat.killaura;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.api.API;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.annotations.Check;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInArmAnimationPacket;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import cc.ghast.artemis.v2.lag.LagCore;
import org.bukkit.entity.Player;

@Check(invalidVersions={ProtocolVersion.V1_9})
public class KillAuraA
extends AbstractCheck {
    private long lastSwing;

    @Override
    public void handle(PlayerData data, NMSObject packet) {
        if (packet instanceof WrappedInArmAnimationPacket) {
            this.lastSwing = System.currentTimeMillis();
        } else if (packet instanceof WrappedInUseEntityPacket) {
            long delayCap;
            if (!((WrappedInUseEntityPacket)packet).action.equals((Object)WrappedInUseEntityPacket.EnumEntityUseAction.ATTACK)) {
                return;
            }
            long swingDelay = System.currentTimeMillis() - this.lastSwing;
            if (swingDelay > (delayCap = this.calcMillis(data.getPlayer())) && Artemis.INSTANCE.getApi().getLagCore().getTPS() > 17.0) {
                this.log(data, swingDelay + " -> " + delayCap);
            }
        }
    }

    private long calcMillis(Player player) {
        return (long)LagCore.getPing(player) + 100L;
    }
}

