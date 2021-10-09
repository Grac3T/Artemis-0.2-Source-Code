/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.handshake;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.FieldAccessor;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.Reflection;
import org.bukkit.entity.Player;

public class WrappedHandshakeClientProtocol
extends NMSObject {
    private static final String packet = "PacketHandshakingInSetProtocol";
    private int protocolVersion;
    private String serverAddressOrIp;
    private FieldAccessor<Integer> protocolVersionField = Reflection.getField("PacketHandshakingInSetProtocol", Integer.TYPE, 0);
    private FieldAccessor<String> serverAddressOrIpField = Reflection.getFieldSafe("PacketHandshakingInSetProtocol", String.class, 0);

    public WrappedHandshakeClientProtocol(Object object, Player player) {
        super(object, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.protocolVersion = this.fetch(this.protocolVersionField);
        this.serverAddressOrIp = this.fetch(this.serverAddressOrIpField);
    }

    public int getProtocolVersion() {
        return this.protocolVersion;
    }

    public String getServerAddressOrIp() {
        return this.serverAddressOrIp;
    }
}

