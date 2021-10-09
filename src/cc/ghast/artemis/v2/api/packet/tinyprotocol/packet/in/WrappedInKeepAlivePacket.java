/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.FieldAccessor;
import org.bukkit.entity.Player;

public class WrappedInKeepAlivePacket
extends NMSObject {
    private static final String packet = "PacketPlayInKeepAlive";
    private static FieldAccessor<Integer> fieldLegacy;
    private static FieldAccessor<Long> field;
    private long time;

    public WrappedInKeepAlivePacket(long time) {
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_12)) {
            this.setPacket(packet, (Object)((int)time));
        } else {
            this.setPacket(packet, (Object)time);
        }
    }

    public WrappedInKeepAlivePacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_12)) {
            fieldLegacy = WrappedInKeepAlivePacket.fetchField(packet, Integer.TYPE, 0);
            this.time = this.fetch(fieldLegacy).intValue();
        } else {
            field = WrappedInKeepAlivePacket.fetchField(packet, Long.TYPE, 0);
            this.time = this.fetch(field);
        }
    }

    public long getTime() {
        return this.time;
    }
}

