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

public class WrappedInClientCommandPacket
extends NMSObject {
    private static final String packet = "PacketPlayInClientCommand";
    private static FieldAccessor<Enum> fieldCommand = WrappedInClientCommandPacket.fetchField("PacketPlayInClientCommand", Enum.class, 0);
    EnumClientCommand command;

    public WrappedInClientCommandPacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.command = EnumClientCommand.values()[this.fetch(fieldCommand).ordinal()];
    }

    public EnumClientCommand getCommand() {
        return this.command;
    }

    public static enum EnumClientCommand {
        PERFORM_RESPAWN,
        REQUEST_STATS,
        OPEN_INVENTORY_ACHIEVEMENT;

    }

}

