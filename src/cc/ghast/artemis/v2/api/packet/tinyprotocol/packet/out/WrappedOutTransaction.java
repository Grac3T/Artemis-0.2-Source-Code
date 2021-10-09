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
import java.util.Objects;
import org.bukkit.entity.Player;

public class WrappedOutTransaction
extends NMSObject {
    private static final String packet = "PacketPlayOutTransaction";
    private static FieldAccessor<Integer> fieldId = WrappedOutTransaction.fetchField("PacketPlayOutTransaction", Integer.TYPE, 0);
    private static FieldAccessor<Short> fieldAction = WrappedOutTransaction.fetchField("PacketPlayOutTransaction", Short.TYPE, 0);
    private static FieldAccessor<Boolean> fieldAccepted = WrappedOutTransaction.fetchField("PacketPlayOutTransaction", Boolean.TYPE, 0);
    final String fnwo = "%%__NONCE__%%";
    private int id;
    private short action;
    private boolean accept;

    public WrappedOutTransaction(int id, short action, boolean accept) {
        this.setPacket(packet, id, action, accept);
    }

    public WrappedOutTransaction(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.id = this.fetch(fieldId);
        this.action = this.fetch(fieldAction);
        this.accept = this.fetch(fieldAccepted);
    }

    public String getFnwo() {
        Objects.requireNonNull(this);
        return "%%__NONCE__%%";
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

