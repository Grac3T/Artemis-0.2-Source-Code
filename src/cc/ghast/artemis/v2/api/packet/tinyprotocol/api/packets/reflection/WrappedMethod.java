/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.WrappedClass;
import com.google.common.collect.Sets;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class WrappedMethod {
    private final WrappedClass parent;
    private final Method method;
    private final Set<Class> parameters;

    public WrappedMethod(WrappedClass parent, Method method) {
        this.parent = parent;
        this.method = method;
        this.parameters = Sets.newHashSet(method.getParameterTypes());
    }

    public <T> T invoke(Object object, Object ... args) {
        try {
            return (T)this.method.invoke(object, args);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public WrappedClass getParent() {
        return this.parent;
    }

    public Method getMethod() {
        return this.method;
    }

    public Set<Class> getParameters() {
        return this.parameters;
    }
}

