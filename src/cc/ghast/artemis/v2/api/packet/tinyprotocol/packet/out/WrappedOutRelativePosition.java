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

public class WrappedOutRelativePosition
extends NMSObject {
    private static final String packet = "PacketPlayOutEntity";
    private static FieldAccessor<Integer> fieldId = WrappedOutRelativePosition.fetchField("PacketPlayOutEntity", Integer.TYPE, 0);
    private static FieldAccessor<Byte> fieldX = WrappedOutRelativePosition.fetchField("PacketPlayOutEntity", Byte.TYPE, 0);
    private static FieldAccessor<Byte> fieldY = WrappedOutRelativePosition.fetchField("PacketPlayOutEntity", Byte.TYPE, 1);
    private static FieldAccessor<Byte> fieldZ = WrappedOutRelativePosition.fetchField("PacketPlayOutEntity", Byte.TYPE, 2);
    private static FieldAccessor<Byte> fieldYaw = WrappedOutRelativePosition.fetchField("PacketPlayOutEntity", Byte.TYPE, 0);
    private static FieldAccessor<Byte> fieldPitch = WrappedOutRelativePosition.fetchField("PacketPlayOutEntity", Byte.TYPE, 1);
    private static FieldAccessor<Boolean> fieldGround = WrappedOutRelativePosition.fetchField("PacketPlayOutEntity", Boolean.TYPE, 0);
    private int id;
    private byte x;
    private byte y;
    private byte z;
    private byte yaw;
    private byte pitch;
    private boolean look;
    private boolean pos;
    private boolean ground;

    public WrappedOutRelativePosition(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        String name = this.getPacketName();
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_8)) {
            this.pos = name.equals("PacketPlayOutEntityMove") || name.equals("PacketPlayOutEntityMoveLook");
            this.look = name.equals("PacketPlayOutEntityLook") || name.equals("PacketPlayOutEntityMoveLook");
        } else {
            this.pos = name.equals("PacketPlayOutEntity$PacketPlayOutEntityMove") || name.equals("PacketPlayOutEntity$PacketPlayOutEntityMoveLook");
            this.look = name.equals("PacketPlayOutEntity$PacketPlayOutEntityLook") || name.equals("PacketPlayOutEntity$PacketPlayOutEntityMoveLook");
        }
        this.id = this.fetch(fieldId);
        this.x = this.fetch(fieldX);
        this.y = this.fetch(fieldY);
        this.z = this.fetch(fieldZ);
        this.yaw = this.fetch(fieldYaw);
        this.pitch = this.fetch(fieldPitch);
        this.ground = this.fetch(fieldGround);
    }

    public int getId() {
        return this.id;
    }

    public byte getX() {
        return this.x;
    }

    public byte getY() {
        return this.y;
    }

    public byte getZ() {
        return this.z;
    }

    public byte getYaw() {
        return this.yaw;
    }

    public byte getPitch() {
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

