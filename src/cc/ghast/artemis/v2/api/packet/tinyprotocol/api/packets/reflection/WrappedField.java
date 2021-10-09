/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.WrappedClass;
import java.lang.reflect.Field;

public class WrappedField {
    private final WrappedClass parent;
    private final Field field;
    private final Class<?> type;

    public WrappedField(WrappedClass parent, Field field) {
        this.parent = parent;
        this.field = field;
        this.type = field.getType();
        this.field.setAccessible(true);
    }

    public <T> T get(Object parent) {
        try {
            return (T)this.field.get(parent);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void set(Object parent, Object value) {
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(this.field, this.field.getModifiers() & 0xFFFFFFEF);
            this.field.setAccessible(true);
            this.field.set(parent, value);
        }
        catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public WrappedClass getParent() {
        return this.parent;
    }

    public Field getField() {
        return this.field;
    }

    public Class<?> getType() {
        return this.type;
    }
}

