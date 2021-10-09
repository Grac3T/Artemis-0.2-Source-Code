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

public class WrappedInHeldItemSlotPacket
extends NMSObject {
    private static final String packet = "PacketPlayInHeldItemSlot";
    private static FieldAccessor<Integer> fieldHeldSlot = WrappedInHeldItemSlotPacket.fetchField("PacketPlayInHeldItemSlot", Integer.TYPE, 0);
    private int slot;

    public WrappedInHeldItemSlotPacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.slot = this.fetch(fieldHeldSlot);
    }

    public int getSlot() {
        return this.slot;
    }
}

