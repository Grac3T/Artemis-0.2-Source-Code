/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package cc.ghast.artemis.v2.utils.smartinvs.content;

import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryContents;
import org.bukkit.entity.Player;

public interface InventoryProvider {
    public void init(Player var1, InventoryContents var2);

    public void update(Player var1, InventoryContents var2);
}

