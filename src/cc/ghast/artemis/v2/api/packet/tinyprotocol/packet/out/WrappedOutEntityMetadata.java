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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.bukkit.entity.Player;

public class WrappedOutEntityMetadata
extends NMSObject {
    private static final String packet = "PacketPlayOutEntityMetadata";
    private static FieldAccessor<Integer> entityidField;
    private static FieldAccessor<List> watchableObjectsField;
    private List<Object> watchableObjects;
    private int entityId;

    public WrappedOutEntityMetadata(Object object, Player player) {
        super(object, player);
    }

    public WrappedOutEntityMetadata(int entityId, List<Object> objects) {
        this.setPacket(packet, entityId, objects);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        watchableObjectsField = WrappedOutEntityMetadata.fetchField(packet, List.class, 0);
        entityidField = WrappedOutEntityMetadata.fetchField(packet, Integer.TYPE, 0);
        this.watchableObjects = new ArrayList<Object>();
        this.entityId = this.fetch(entityidField);
        List list = this.fetch(watchableObjectsField);
        if (list != null) {
            list.forEach(object -> this.watchableObjects.add(object));
        }
    }

    public List<Object> getWatchableObjects() {
        return this.watchableObjects;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setWatchableObjects(List<Object> watchableObjects) {
        this.watchableObjects = watchableObjects;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
}

