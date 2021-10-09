/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2;

import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.api.API;

public enum Artemis {
    INSTANCE;

    private ArtemisPlugin plugin;
    private API api;

    public void enable(ArtemisPlugin plugin) {
        this.plugin = plugin;
        this.api = new API(plugin);
    }

    public ArtemisPlugin getPlugin() {
        return this.plugin;
    }

    public API getApi() {
        return this.api;
    }
}

