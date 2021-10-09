/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.ClickType
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package cc.ghast.artemis.v2.utils.gui.listener;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.ArtemisPlugin;
import cc.ghast.artemis.v2.utils.gui.Button;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ButtonListener
implements Listener {
    public ButtonListener() {
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)Artemis.INSTANCE.getPlugin());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) {
            return;
        }
        Button button = Button.buttons.parallelStream().filter(b -> b.getItemStack((Player)event.getWhoClicked()).isSimilar(event.getCurrentItem())).findFirst().orElse(null);
        if (button == null) {
            return;
        }
        if (button.cancel()) {
            event.setCancelled(true);
        }
        button.onClick((Player)event.getWhoClicked(), event.getClick());
    }
}

