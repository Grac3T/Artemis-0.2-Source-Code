/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 */
package cc.ghast.artemis.v2.managers;

import cc.ghast.artemis.v2.api.manager.Manager;
import cc.ghast.artemis.v2.dependency.ViaVersionDependency;
import org.bukkit.Bukkit;

public class DependencyManager
extends Manager {
    private ViaVersionDependency viaVersionDependency;

    @Override
    public void init() {
        if (Bukkit.getPluginManager().isPluginEnabled("ViaVersion")) {
            this.viaVersionDependency = new ViaVersionDependency();
            this.viaVersionDependency.init();
        } else {
            this.viaVersionDependency = null;
        }
    }

    @Override
    public void disinit() {
    }

    public ViaVersionDependency getViaVersionDependency() {
        return this.viaVersionDependency;
    }
}

