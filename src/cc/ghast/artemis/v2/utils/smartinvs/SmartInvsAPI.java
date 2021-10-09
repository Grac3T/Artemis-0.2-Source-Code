/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.java.JavaPlugin
 */
package cc.ghast.artemis.v2.utils.smartinvs;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.api.manager.Manager;
import cc.ghast.artemis.v2.utils.smartinvs.InventoryManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SmartInvsAPI
extends Manager {
    private static SmartInvsAPI instance;
    private static InventoryManager invManager;

    @Override
    public void init() {
        instance = this;
        invManager = new InventoryManager(Artemis.INSTANCE.getPlugin());
        invManager.init();
    }

    @Override
    public void disinit() {
    }

    public static InventoryManager manager() {
        return invManager;
    }

    public static SmartInvsAPI instance() {
        return instance;
    }
}

