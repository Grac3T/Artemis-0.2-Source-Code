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

public class WrappedOutHeldItemSlot
extends NMSObject {
    private static String packet = "PacketPlayOutHeldItemSlot";
    private FieldAccessor<Integer> slotField = WrappedOutHeldItemSlot.fetchField(packet, Integer.TYPE, 0);
    private int slot;

    public WrappedOutHeldItemSlot(Object object, Player player) {
        super(object, player);
    }

    public WrappedOutHeldItemSlot(int slot) {
        this.slot = slot;
        this.setObject(WrappedOutHeldItemSlot.construct(packet, (Object)slot));
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.slot = this.fetch(this.slotField);
    }

    @Override
    public Object getObject() {
        return super.getObject();
    }

    public FieldAccessor<Integer> getSlotField() {
        return this.slotField;
    }

    public int getSlot() {
        return this.slot;
    }
}

