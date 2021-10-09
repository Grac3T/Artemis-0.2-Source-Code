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

public class WrappedInFlyingPacket
extends NMSObject {
    private static final String packet = "PacketPlayInFlying";
    private static FieldAccessor<Double> fieldX = WrappedInFlyingPacket.fetchField("PacketPlayInFlying", Double.TYPE, 0);
    private static FieldAccessor<Double> fieldY = WrappedInFlyingPacket.fetchField("PacketPlayInFlying", Double.TYPE, 1);
    private static FieldAccessor<Double> fieldZ = WrappedInFlyingPacket.fetchField("PacketPlayInFlying", Double.TYPE, 2);
    private static FieldAccessor<Float> fieldYaw = WrappedInFlyingPacket.fetchField("PacketPlayInFlying", Float.TYPE, 0);
    private static FieldAccessor<Float> fieldPitch = WrappedInFlyingPacket.fetchField("PacketPlayInFlying", Float.TYPE, 1);
    private static FieldAccessor<Boolean> fieldGround = WrappedInFlyingPacket.fetchField("PacketPlayInFlying", Boolean.TYPE, 0);
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean look;
    private boolean pos;
    private boolean ground;

    public WrappedInFlyingPacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        String name = this.getPacketName();
        this.pos = name.replace(packet, "").replace("$", "").contains("PacketPlayInPosition");
        this.look = name.contains("Look");
        if (this.pos) {
            this.x = this.fetch(fieldX);
            this.y = this.fetch(fieldY);
            this.z = this.fetch(fieldZ);
        }
        if (this.look) {
            this.yaw = this.fetch(fieldYaw).floatValue();
            this.pitch = this.fetch(fieldPitch).floatValue();
        }
        this.ground = this.fetch(fieldGround);
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

    public boolean isLook() {
        return this.look;
    }

    public boolean isPos() {
        return this.pos;
    }

    public boolean isGround() {
        return this.ground;
    }
}

