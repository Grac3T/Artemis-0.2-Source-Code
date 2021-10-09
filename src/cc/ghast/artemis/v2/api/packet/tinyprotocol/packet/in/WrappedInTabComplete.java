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
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.BaseBlockPosition;
import org.bukkit.entity.Player;

public class WrappedInTabComplete
extends NMSObject {
    private static final String packet = "PacketPlayInTabComplete";
    private static FieldAccessor<String> messageAccessor = WrappedInTabComplete.fetchField("PacketPlayInTabComplete", String.class, 0);
    private static FieldAccessor<Boolean> hasToolTipAccessor = WrappedInTabComplete.fetchField("PacketPlayInTabComplete", Boolean.TYPE, 0);
    private static FieldAccessor<Object> baseBlockPosition = WrappedInTabComplete.fetchField("PacketPlayInTabComplete", Object.class, 0);
    private String message;
    private BaseBlockPosition blockPosition;
    private boolean hasToolTip;

    public WrappedInTabComplete(Object object, Player player) {
        super(object, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.message = this.fetch(messageAccessor);
        if (ProtocolVersion.getGameVersion().isAbove(ProtocolVersion.V1_8_9)) {
            this.hasToolTip = this.fetch(hasToolTipAccessor);
        }
        if (ProtocolVersion.getGameVersion().isAbove(ProtocolVersion.V1_7_10)) {
            // empty if block
        }
    }

    public String getMessage() {
        return this.message;
    }

    public BaseBlockPosition getBlockPosition() {
        return this.blockPosition;
    }

    public boolean isHasToolTip() {
        return this.hasToolTip;
    }
}

