/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection;

public interface FieldAccessor<T> {
    public T get(Object var1);

    public void set(Object var1, Object var2);

    public boolean hasField(Object var1);
}

