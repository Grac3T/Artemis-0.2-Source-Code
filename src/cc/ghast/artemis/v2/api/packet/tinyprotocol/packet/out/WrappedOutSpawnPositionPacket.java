/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.out;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.FieldAccessor;
import org.bukkit.entity.Player;

public class WrappedOutSpawnPositionPacket
extends NMSObject {
    private static final String packet = "PacketPlayOutPosition";
    private static FieldAccessor<Integer> fieldX = WrappedOutSpawnPositionPacket.fetchField("PacketPlayOutPosition", Integer.TYPE, 1);
    private static FieldAccessor<Integer> fieldY = WrappedOutSpawnPositionPacket.fetchField("PacketPlayOutPosition", Integer.TYPE, 2);
    private static FieldAccessor<Integer> fieldZ = WrappedOutSpawnPositionPacket.fetchField("PacketPlayOutPosition", Integer.TYPE, 3);
    private double x;
    private double y;
    private double z;

    public WrappedOutSpawnPositionPacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.x = this.fetch(fieldX).intValue();
        this.y = this.fetch(fieldY).intValue();
        this.z = this.fetch(fieldZ).intValue();
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }
}

