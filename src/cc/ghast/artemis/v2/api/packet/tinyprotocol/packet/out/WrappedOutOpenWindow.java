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
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.WrappedChatMessage;
import org.bukkit.entity.Player;

public class WrappedOutOpenWindow
extends NMSObject {
    private static String packet = "PacketPlayOutOpenWindow";
    private static FieldAccessor<Integer> idField = WrappedOutOpenWindow.fetchField(packet, Integer.TYPE, 0);
    private static FieldAccessor<String> nameField = WrappedOutOpenWindow.fetchField(packet, String.class, 0);
    private static FieldAccessor<Object> chatCompField = WrappedOutOpenWindow.fetchField(packet, Object.class, 2);
    private static FieldAccessor<Integer> inventorySize = WrappedOutOpenWindow.fetchField(packet, Integer.TYPE, 1);
    private int id;
    private String name;
    private WrappedChatMessage chatComponent;
    private int size;

    public WrappedOutOpenWindow(Object object, Player player) {
        super(object, player);
    }

    public WrappedOutOpenWindow(int id, String name, WrappedChatMessage msg, int size) {
        this.setPacket(packet, id, name, msg.getObject(), size);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.id = this.fetch(idField);
        this.name = this.fetch(nameField);
        this.chatComponent = new WrappedChatMessage(this.fetch(chatCompField));
        this.size = this.fetch(inventorySize);
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public WrappedChatMessage getChatComponent() {
        return this.chatComponent;
    }

    public int getSize() {
        return this.size;
    }
}

