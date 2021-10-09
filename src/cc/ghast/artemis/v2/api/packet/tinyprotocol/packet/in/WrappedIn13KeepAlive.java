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
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.Reflection;
import org.bukkit.entity.Player;

public class WrappedIn13KeepAlive
extends NMSObject {
    private static final String packet = "PacketPlayInKeepAlive";
    private long ping;
    private FieldAccessor<Long> pingField = Reflection.getFieldSafe("PacketPlayInKeepAlive", Long.TYPE, 0);

    public WrappedIn13KeepAlive(Object object, Player player) {
        super(object, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.ping = this.fetch(this.pingField);
    }

    public long getPing() {
        return this.ping;
    }
}

