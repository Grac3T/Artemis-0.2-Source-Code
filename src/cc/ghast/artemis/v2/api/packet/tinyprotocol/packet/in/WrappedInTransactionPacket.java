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

public class WrappedInTransactionPacket
extends NMSObject {
    private static final String packet = "PacketPlayInTransaction";
    private static FieldAccessor<Integer> fieldId = WrappedInTransactionPacket.fetchField("PacketPlayInTransaction", Integer.TYPE, 0);
    private static FieldAccessor<Short> fieldAction = WrappedInTransactionPacket.fetchField("PacketPlayInTransaction", Short.TYPE, 0);
    private static FieldAccessor<Boolean> fieldAccepted = WrappedInTransactionPacket.fetchField("PacketPlayInTransaction", Boolean.TYPE, 0);
    private int id;
    private short action;
    private boolean accept;

    public WrappedInTransactionPacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.id = this.fetch(fieldId);
        this.action = this.fetch(fieldAction);
        this.accept = this.fetch(fieldAccepted);
    }

    public int getId() {
        return this.id;
    }

    public short getAction() {
        return this.action;
    }

    public boolean isAccept() {
        return this.accept;
    }
}

