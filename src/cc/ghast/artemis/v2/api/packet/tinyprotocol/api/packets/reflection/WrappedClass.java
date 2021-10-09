/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.WrappedConstructor;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.WrappedField;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.WrappedMethod;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Predicate;

public class WrappedClass {
    private final Class parent;

    public WrappedClass(Class parent) {
        this.parent = parent;
    }

    public WrappedField getFieldByName(String name) {
        AccessibleObject tempField = null;
        for (Field field : this.parent.getDeclaredFields()) {
            if (!field.getName().equals(name)) continue;
            tempField = field;
            break;
        }
        if (tempField != null) {
            tempField.setAccessible(true);
            return new WrappedField(this, (Field)tempField);
        }
        return null;
    }

    public WrappedConstructor getConstructor(Class ... types) {
        try {
            return new WrappedConstructor(this, this.parent.getDeclaredConstructor(types));
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public WrappedConstructor getConstructor() {
        if (Arrays.stream(this.parent.getConstructors()).anyMatch(cons -> cons.getParameterCount() == 0)) {
            return new WrappedConstructor(this);
        }
        return null;
    }

    public WrappedConstructor getConstructorAtIndex(int index) {
        return new WrappedConstructor(this, this.parent.getConstructors()[index]);
    }

    private WrappedField getFieldByType(Class<?> type) {
        WrappedField tempField = null;
        for (Field field : this.parent.getDeclaredFields()) {
            if (!field.getType().equals(type)) continue;
            tempField = new WrappedField(this, field);
            break;
        }
        return tempField;
    }

    public WrappedField getFirstFieldByType(Class<?> type) {
        return this.getFieldByType(type);
    }

    public WrappedMethod getMethod(String name, Class ... parameters) {
        boolean same;
        int x;
        for (Method method : this.parent.getDeclaredMethods()) {
            if (!method.getName().equals(name) || parameters.length != method.getParameterTypes().length) continue;
            same = true;
            for (x = 0; x < method.getParameterTypes().length; ++x) {
                if (method.getParameterTypes()[x] == parameters[x]) continue;
                same = false;
                break;
            }
            if (!same) continue;
            return new WrappedMethod(this, method);
        }
        for (Method method : this.parent.getMethods()) {
            if (!method.getName().equals(name) || parameters.length != method.getParameterTypes().length) continue;
            same = true;
            for (x = 0; x < method.getParameterTypes().length; ++x) {
                if (method.getParameterTypes()[x] == parameters[x]) continue;
                same = false;
                break;
            }
            if (!same) continue;
            return new WrappedMethod(this, method);
        }
        return null;
    }

    public Enum getEnum(String name) {
        return Enum.valueOf(this.parent, name);
    }

    public Class getParent() {
        return this.parent;
    }
}

