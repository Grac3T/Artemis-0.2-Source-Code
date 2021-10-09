/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.WrappedClass;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class WrappedConstructor {
    private final WrappedClass parent;
    private Constructor constructor;

    public <T> T newInstance(Object ... args) {
        try {
            this.constructor.setAccessible(true);
            return (T) this.constructor.newInstance(args);
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T newInstance() {
        try {
            this.constructor.setAccessible(true);
            return (T) this.constructor.newInstance(new Object[0]);
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public WrappedConstructor(WrappedClass parent) {
        this.parent = parent;
    }

    public WrappedConstructor(WrappedClass parent, Constructor constructor) {
        this.parent = parent;
        this.constructor = constructor;
    }

    public WrappedClass getParent() {
        return this.parent;
    }

    public Constructor getConstructor() {
        return this.constructor;
    }
}

