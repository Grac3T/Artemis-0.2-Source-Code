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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.bukkit.entity.Player;

public class WrappedOutPositionPacket
extends NMSObject {
    private static final String packet = "PacketPlayOutPosition";
    private static FieldAccessor<Double> fieldX = WrappedOutPositionPacket.fetchField("PacketPlayOutPosition", Double.TYPE, 0);
    private static FieldAccessor<Double> fieldY = WrappedOutPositionPacket.fetchField("PacketPlayOutPosition", Double.TYPE, 1);
    private static FieldAccessor<Double> fieldZ = WrappedOutPositionPacket.fetchField("PacketPlayOutPosition", Double.TYPE, 2);
    private static FieldAccessor<Float> fieldYaw = WrappedOutPositionPacket.fetchField("PacketPlayOutPosition", Float.TYPE, 0);
    private static FieldAccessor<Float> fieldPitch = WrappedOutPositionPacket.fetchField("PacketPlayOutPosition", Float.TYPE, 1);
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public WrappedOutPositionPacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.x = this.fetch(fieldX);
        this.y = this.fetch(fieldY);
        this.z = this.fetch(fieldZ);
        this.yaw = this.fetch(fieldYaw).floatValue();
        this.pitch = this.fetch(fieldPitch).floatValue();
    }

    private List<Integer> toOrdinal(Set<Enum> enums) {
        ArrayList<Integer> ordinals = new ArrayList<Integer>();
        enums.forEach(e -> ordinals.add(e.ordinal()));
        return ordinals;
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

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public WrappedOutPositionPacket() {
    }
}

