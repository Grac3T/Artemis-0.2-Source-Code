/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.java.JavaPlugin
 */
package cc.ghast.artemis.v2;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.api.API;
import cc.ghast.lang.ArtemisLangInstance;
import org.bukkit.plugin.java.JavaPlugin;

public class ArtemisPlugin
extends JavaPlugin {
    public void onEnable() {
        ArtemisLangInstance.INSTANCE.enable();
        Artemis.INSTANCE.enable(this);
    }

    public void onDisable() {
        Artemis.INSTANCE.getApi().disinit();
    }
}

