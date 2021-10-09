/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.ClickType
 *  org.bukkit.inventory.ItemStack
 */
package cc.ghast.artemis.v2.utils.gui;

import cc.ghast.artemis.v2.utils.gui.AbstractMenu;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class Button {
    public static Set<Button> buttons = new HashSet<Button>();
    private AbstractMenu menu;

    public Button(@Nullable AbstractMenu menu) {
        this.menu = menu;
        buttons.add(this);
    }

    public abstract ItemStack getItemStack(Player var1);

    public abstract void onClick(Player var1, ClickType var2);

    public abstract boolean cancel();

    public AbstractMenu getMenu() {
        return this.menu;
    }
}

