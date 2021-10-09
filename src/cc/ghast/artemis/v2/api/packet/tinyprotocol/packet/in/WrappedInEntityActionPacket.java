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

public class WrappedInEntityActionPacket
extends NMSObject {
    private static final String packet = "PacketPlayInEntityAction";
    private static FieldAccessor<Integer> fieldAction1_7;
    private static FieldAccessor<Enum> fieldAction1_8;
    private EnumPlayerAction action;

    public WrappedInEntityActionPacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_8)) {
            fieldAction1_7 = WrappedInEntityActionPacket.fetchField(packet, Integer.TYPE, 0);
            this.action = EnumPlayerAction.values()[Math.min(8, this.fetch(fieldAction1_7))];
        } else {
            fieldAction1_8 = WrappedInEntityActionPacket.fetchField(packet, Enum.class, 0);
            this.action = EnumPlayerAction.values()[this.fetch(fieldAction1_8).ordinal()];
        }
    }

    public EnumPlayerAction getAction() {
        return this.action;
    }

    public static enum EnumPlayerAction {
        START_SNEAKING,
        STOP_SNEAKING,
        STOP_SLEEPING,
        START_SPRINTING,
        STOP_SPRINTING,
        START_RIDING_JUMP,
        STOP_RIDING_JUMP,
        OPEN_INVENTORY,
        START_FALL_FLYING;

    }

}

