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

public class WrappedOutVelocityPacket
extends NMSObject {
    private static final String packet = "PacketPlayOutEntityVelocity";
    private static FieldAccessor<Integer> fieldId = WrappedOutVelocityPacket.fetchField("PacketPlayOutEntityVelocity", Integer.TYPE, 0);
    private static FieldAccessor<Integer> fieldX = WrappedOutVelocityPacket.fetchField("PacketPlayOutEntityVelocity", Integer.TYPE, 1);
    private static FieldAccessor<Integer> fieldY = WrappedOutVelocityPacket.fetchField("PacketPlayOutEntityVelocity", Integer.TYPE, 2);
    private static FieldAccessor<Integer> fieldZ = WrappedOutVelocityPacket.fetchField("PacketPlayOutEntityVelocity", Integer.TYPE, 3);
    private int id;
    private double x;
    private double y;
    private double z;

    public WrappedOutVelocityPacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.id = this.fetch(fieldId);
        this.x = (double)this.fetch(fieldX).intValue() / 8000.0;
        this.y = (double)this.fetch(fieldY).intValue() / 8000.0;
        this.z = (double)this.fetch(fieldZ).intValue() / 8000.0;
    }

    public int getId() {
        return this.id;
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

