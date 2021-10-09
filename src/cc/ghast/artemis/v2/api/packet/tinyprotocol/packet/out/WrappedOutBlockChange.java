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
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.BaseBlockPosition;
import org.bukkit.entity.Player;

public class WrappedOutBlockChange
extends NMSObject {
    private static final String packet = "PacketPlayOutBlockChange";
    private static FieldAccessor<Integer> legacyX = WrappedOutBlockChange.fetchField("PacketPlayOutBlockChange", Integer.TYPE, 0);
    private static FieldAccessor<Integer> legacyY = WrappedOutBlockChange.fetchField("PacketPlayOutBlockChange", Integer.TYPE, 1);
    private static FieldAccessor<Integer> legacyZ = WrappedOutBlockChange.fetchField("PacketPlayOutBlockChange", Integer.TYPE, 2);
    private static FieldAccessor<Object> blockPos = WrappedOutBlockChange.fetchField("PacketPlayOutBlockChange", Object.class, 0);
    private BaseBlockPosition position;

    public WrappedOutBlockChange(Object packet) {
        super(packet);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.position = ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_8_5) ? new BaseBlockPosition(this.fetch(legacyX), this.fetch(legacyY), this.fetch(legacyZ)) : new BaseBlockPosition(this.fetch(blockPos));
    }

    public BaseBlockPosition getPosition() {
        return this.position;
    }
}

