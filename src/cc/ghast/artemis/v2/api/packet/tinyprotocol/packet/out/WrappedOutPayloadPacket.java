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

public class WrappedOutPayloadPacket
extends NMSObject {
    private static final String packet = "PacketPlayOutCustomPayload";
    private static FieldAccessor<String> fieldTag = WrappedOutPayloadPacket.fetchField("PacketPlayOutCustomPayload", String.class, 0);
    private static FieldAccessor<byte[]> fieldData = WrappedOutPayloadPacket.fetchField("PacketPlayOutCustomPayload", byte[].class, 1);
    private String tag;
    private byte[] data;

    public WrappedOutPayloadPacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.tag = this.fetch(fieldTag);
        this.data = this.fetch(fieldData);
    }

    public String getTag() {
        return this.tag;
    }

    public byte[] getData() {
        return this.data;
    }

    public WrappedOutPayloadPacket() {
    }
}

