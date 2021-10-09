/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.FieldAccessor;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.Reflection;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.entity.Player;

public class WrappedWatchableObject
extends NMSObject {
    private static String type = NMSObject.Type.WATCHABLE_OBJECT;
    private FieldAccessor<Integer> objectTypeField;
    private FieldAccessor<Integer> dataValueIdField;
    private FieldAccessor<Object> watchedObjectField;
    private FieldAccessor<Boolean> watchedField;
    private int objectType;
    private int dataValueId;
    private Object watchedObject;
    private boolean watched;

    public WrappedWatchableObject(Object object) {
        super(object);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.objectTypeField = WrappedWatchableObject.fetchField(type, Integer.TYPE, 0);
        this.dataValueIdField = WrappedWatchableObject.fetchField(type, Integer.TYPE, 1);
        this.watchedObjectField = WrappedWatchableObject.fetchField(type, Object.class, 0);
        this.watchedField = WrappedWatchableObject.fetchField(type, Boolean.TYPE, 0);
        this.objectType = this.fetch(this.objectTypeField);
        this.dataValueId = this.fetch(this.dataValueIdField);
        this.watchedObject = this.fetch(this.watchedObjectField);
        this.watched = this.fetch(this.watchedField);
    }

    public void setPacket(String packet, int type, int data, Object watchedObject) {
        Class<?> c = Reflection.getClass(Reflection.NMS_PREFIX + "." + packet);
        try {
            Object o = c.getConstructor(Integer.TYPE, Integer.TYPE, Object.class).newInstance(type, data, watchedObject);
            this.setObject(o);
        }
        catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public FieldAccessor<Integer> getObjectTypeField() {
        return this.objectTypeField;
    }

    public FieldAccessor<Integer> getDataValueIdField() {
        return this.dataValueIdField;
    }

    public FieldAccessor<Object> getWatchedObjectField() {
        return this.watchedObjectField;
    }

    public FieldAccessor<Boolean> getWatchedField() {
        return this.watchedField;
    }

    public int getObjectType() {
        return this.objectType;
    }

    public int getDataValueId() {
        return this.dataValueId;
    }

    public Object getWatchedObject() {
        return this.watchedObject;
    }

    public boolean isWatched() {
        return this.watched;
    }

    public void setObjectTypeField(FieldAccessor<Integer> objectTypeField) {
        this.objectTypeField = objectTypeField;
    }

    public void setDataValueIdField(FieldAccessor<Integer> dataValueIdField) {
        this.dataValueIdField = dataValueIdField;
    }

    public void setWatchedObjectField(FieldAccessor<Object> watchedObjectField) {
        this.watchedObjectField = watchedObjectField;
    }

    public void setWatchedField(FieldAccessor<Boolean> watchedField) {
        this.watchedField = watchedField;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public void setDataValueId(int dataValueId) {
        this.dataValueId = dataValueId;
    }

    public void setWatchedObject(Object watchedObject) {
        this.watchedObject = watchedObject;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public WrappedWatchableObject(FieldAccessor<Integer> objectTypeField, FieldAccessor<Integer> dataValueIdField, FieldAccessor<Object> watchedObjectField, FieldAccessor<Boolean> watchedField, int objectType, int dataValueId, Object watchedObject, boolean watched) {
        this.objectTypeField = objectTypeField;
        this.dataValueIdField = dataValueIdField;
        this.watchedObjectField = watchedObjectField;
        this.watchedField = watchedField;
        this.objectType = objectType;
        this.dataValueId = dataValueId;
        this.watchedObject = watchedObject;
        this.watched = watched;
    }
}

