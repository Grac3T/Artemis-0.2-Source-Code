/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.Packet;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.FieldAccessor;
import org.bukkit.entity.Player;

public class WrappedInVelocityPacket
extends Packet {
    private static final String packet = "PacketPlayInFlying";
    private static FieldAccessor<Integer> fieldX = WrappedInVelocityPacket.fetchField("PacketPlayInFlying", Integer.TYPE, 0);
    private static FieldAccessor<Integer> fieldY = WrappedInVelocityPacket.fetchField("PacketPlayInFlying", Integer.TYPE, 1);
    private static FieldAccessor<Integer> fieldZ = WrappedInVelocityPacket.fetchField("PacketPlayInFlying", Integer.TYPE, 2);
    private double x;
    private double y;
    private double z;

    public WrappedInVelocityPacket(Object packet) {
        super(packet);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.x = fieldX.get(this.getPacket()).intValue();
        this.y = fieldY.get(this.getPacket()).intValue();
        this.z = fieldZ.get(this.getPacket()).intValue();
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

