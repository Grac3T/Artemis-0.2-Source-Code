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

public class WrappedInCloseWindowPacket
extends NMSObject {
    private static final String packet = "PacketPlayInCloseWindow";
    private static FieldAccessor<Integer> fieldId = WrappedInCloseWindowPacket.fetchField("PacketPlayInCloseWindow", Integer.TYPE, 0);
    private int id;

    public WrappedInCloseWindowPacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.id = this.fetch(fieldId);
    }

    public int getId() {
        return this.id;
    }
}

