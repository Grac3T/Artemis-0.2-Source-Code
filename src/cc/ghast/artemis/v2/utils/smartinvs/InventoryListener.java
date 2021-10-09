/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.utils.smartinvs;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.function.Consumer;

public class InventoryListener<T> {
    private Class<T> type;
    private Consumer<T> consumer;

    public InventoryListener(Class<T> type, Consumer<T> consumer) {
        this.type = type;
        this.consumer = consumer;
    }

    public void accept(Object t) {
        this.consumer.accept((T) t);
    }

    public Class<T> getType() {
        return this.type;
    }
}

