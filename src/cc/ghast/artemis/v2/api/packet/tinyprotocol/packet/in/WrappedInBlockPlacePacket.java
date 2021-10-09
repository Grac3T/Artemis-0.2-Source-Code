/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.FieldAccessor;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.BaseBlockPosition;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.EnumDirection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WrappedInBlockPlacePacket
extends NMSObject {
    private static final String packet = "PacketPlayInBlockPlace";
    private static FieldAccessor<Integer> fieldFace;
    private static FieldAccessor<Enum> fieldFace1_9;
    private static FieldAccessor<Object> fieldBlockPosition;
    private static FieldAccessor<Object> fieldItemStack;
    private static FieldAccessor<Integer> fieldPosX;
    private static FieldAccessor<Integer> fieldPosY;
    private static FieldAccessor<Integer> fieldPosZ;
    private static FieldAccessor<Float> fieldVecX;
    private static FieldAccessor<Float> fieldVecY;
    private static FieldAccessor<Float> fieldVecZ;
    private EnumDirection face;
    private ItemStack itemStack;
    private BaseBlockPosition position;
    private float vecX;
    private float vecY;
    private float vecZ;

    public WrappedInBlockPlacePacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_8)) {
            fieldPosX = WrappedInBlockPlacePacket.fetchField(packet, Integer.TYPE, 0);
            fieldPosY = WrappedInBlockPlacePacket.fetchField(packet, Integer.TYPE, 1);
            fieldPosZ = WrappedInBlockPlacePacket.fetchField(packet, Integer.TYPE, 2);
            fieldFace = WrappedInBlockPlacePacket.fetchField(packet, Integer.TYPE, 3);
            fieldItemStack = WrappedInBlockPlacePacket.fetchField(packet, Object.class, 0);
            fieldVecX = WrappedInBlockPlacePacket.fetchField(packet, Float.TYPE, 0);
            fieldVecY = WrappedInBlockPlacePacket.fetchField(packet, Float.TYPE, 1);
            fieldVecZ = WrappedInBlockPlacePacket.fetchField(packet, Float.TYPE, 2);
            this.position = new BaseBlockPosition(this.fetch(fieldPosX), this.fetch(fieldPosY), this.fetch(fieldPosZ));
            this.face = EnumDirection.values()[Math.min(this.fetch(fieldFace), 5)];
            this.itemStack = WrappedInBlockPlacePacket.toBukkitStack(this.fetch(fieldItemStack));
            this.vecX = this.fetch(fieldVecX).floatValue();
            this.vecY = this.fetch(fieldVecY).floatValue();
            this.vecZ = this.fetch(fieldVecZ).floatValue();
        } else if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_9)) {
            fieldBlockPosition = WrappedInBlockPlacePacket.fetchField(packet, Object.class, 1);
            fieldFace = WrappedInBlockPlacePacket.fetchField(packet, Integer.TYPE, 0);
            fieldItemStack = WrappedInBlockPlacePacket.fetchField(packet, Object.class, 2);
            fieldVecX = WrappedInBlockPlacePacket.fetchField(packet, Float.TYPE, 0);
            fieldVecY = WrappedInBlockPlacePacket.fetchField(packet, Float.TYPE, 1);
            fieldVecZ = WrappedInBlockPlacePacket.fetchField(packet, Float.TYPE, 2);
            this.position = new BaseBlockPosition(this.fetch(fieldBlockPosition));
            this.face = EnumDirection.values()[Math.min(this.fetch(fieldFace), 5)];
            this.itemStack = WrappedInBlockPlacePacket.toBukkitStack(this.fetch(fieldItemStack));
            this.vecX = this.fetch(fieldVecX).floatValue();
            this.vecY = this.fetch(fieldVecY).floatValue();
            this.vecZ = this.fetch(fieldVecZ).floatValue();
        } else if (!this.getObject().toString().contains("BlockPlace")) {
            fieldBlockPosition = WrappedInBlockPlacePacket.fetchField("PacketPlayInUseItem", Object.class, 0);
            fieldFace1_9 = WrappedInBlockPlacePacket.fetchField("PacketPlayInUseItem", Enum.class, 1);
            fieldVecX = WrappedInBlockPlacePacket.fetchField("PacketPlayInUseItem", Float.TYPE, 0);
            fieldVecY = WrappedInBlockPlacePacket.fetchField("PacketPlayInUseItem", Float.TYPE, 1);
            fieldVecZ = WrappedInBlockPlacePacket.fetchField("PacketPlayInUseItem", Float.TYPE, 2);
            this.position = new BaseBlockPosition(this.fetch(fieldBlockPosition));
            this.face = EnumDirection.values()[this.fetch(fieldFace1_9).ordinal()];
            this.vecX = this.fetch(fieldVecX).floatValue();
            this.vecY = this.fetch(fieldVecY).floatValue();
            this.vecZ = this.fetch(fieldVecZ).floatValue();
        }
    }

    public EnumDirection getFace() {
        return this.face;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public BaseBlockPosition getPosition() {
        return this.position;
    }

    public float getVecX() {
        return this.vecX;
    }

    public float getVecY() {
        return this.vecY;
    }

    public float getVecZ() {
        return this.vecZ;
    }
}

