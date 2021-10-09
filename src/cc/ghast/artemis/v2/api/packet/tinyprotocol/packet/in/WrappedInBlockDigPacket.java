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
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.BaseBlockPosition;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.EnumDirection;
import org.bukkit.entity.Player;

public class WrappedInBlockDigPacket
extends NMSObject {
    private static final String packet = "PacketPlayInBlockDig";
    private static FieldAccessor<Object> fieldBlockPosition;
    private static FieldAccessor<Integer> fieldPosX;
    private static FieldAccessor<Integer> fieldPosY;
    private static FieldAccessor<Integer> fieldPosZ;
    private static FieldAccessor<Object> fieldDirection;
    private static FieldAccessor<Object> fieldDigType;
    private static FieldAccessor<Integer> face;
    private static FieldAccessor<Integer> intAction;
    private BaseBlockPosition position;
    private EnumDirection direction;
    private EnumPlayerDigType action;

    public WrappedInBlockDigPacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_8)) {
            fieldPosX = WrappedInBlockDigPacket.fetchField(packet, Integer.TYPE, 0);
            fieldPosY = WrappedInBlockDigPacket.fetchField(packet, Integer.TYPE, 1);
            fieldPosZ = WrappedInBlockDigPacket.fetchField(packet, Integer.TYPE, 2);
            face = WrappedInBlockDigPacket.fetchField(packet, Integer.TYPE, 3);
            intAction = WrappedInBlockDigPacket.fetchField(packet, Integer.TYPE, 4);
            this.position = new BaseBlockPosition(this.fetch(fieldPosX), this.fetch(fieldPosY), this.fetch(fieldPosZ));
            this.direction = EnumDirection.values()[Math.min(this.fetch(face), 5)];
            this.action = EnumPlayerDigType.values()[this.fetch(intAction)];
        } else {
            fieldBlockPosition = WrappedInBlockDigPacket.fetchField(packet, Object.class, 0);
            fieldDirection = WrappedInBlockDigPacket.fetchField(packet, Object.class, 1);
            fieldDigType = WrappedInBlockDigPacket.fetchField(packet, Object.class, 2);
            this.position = new BaseBlockPosition(this.fetch(fieldBlockPosition));
            this.direction = EnumDirection.values()[((Enum)this.fetch(fieldDirection)).ordinal()];
            this.action = EnumPlayerDigType.values()[((Enum)this.fetch(fieldDigType)).ordinal()];
        }
    }

    public BaseBlockPosition getPosition() {
        return this.position;
    }

    public EnumDirection getDirection() {
        return this.direction;
    }

    public EnumPlayerDigType getAction() {
        return this.action;
    }

    public static enum EnumPlayerDigType {
        START_DESTROY_BLOCK,
        ABORT_DESTROY_BLOCK,
        STOP_DESTROY_BLOCK,
        DROP_ALL_ITEMS,
        DROP_ITEM,
        RELEASE_USE_ITEM,
        SWAP_HELD_ITEMS;

    }

}

