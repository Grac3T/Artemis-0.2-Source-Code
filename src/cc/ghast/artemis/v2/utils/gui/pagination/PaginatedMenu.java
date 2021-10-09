/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.ClickType
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 */
package cc.ghast.artemis.v2.utils.gui.pagination;

import cc.ghast.artemis.v2.utils.chat.Chat;
import cc.ghast.artemis.v2.utils.gui.AbstractMenu;
import cc.ghast.artemis.v2.utils.gui.Button;
import cc.ghast.artemis.v2.utils.gui.ItemBuilder;
import cc.ghast.artemis.v2.utils.gui.MaterialUtils;
import java.util.Map;
import java.util.WeakHashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class PaginatedMenu
extends AbstractMenu {
    private final String title;
    private final Button[] buttons;
    private final Button[] pageButtons;
    private final double maxPages;
    private final Map<Player, Inventory> inventories;
    private final Button glassPane;
    private int page = 1;

    public PaginatedMenu(String title, Button ... buttons) {
        this.title = title;
        this.buttons = buttons;
        this.pageButtons = this.getPageButtons();
        this.inventories = new WeakHashMap<Player, Inventory>();
        this.glassPane = new Button(this){
            ItemStack itemStack;
            {
                this.itemStack = new ItemBuilder(MaterialUtils.GRAY_STAINED_GLASS_PANE).name(" ").build();
            }

            @Override
            public ItemStack getItemStack(Player player) {
                return this.itemStack;
            }

            @Override
            public void onClick(Player player, ClickType clickType) {
            }

            @Override
            public boolean cancel() {
                return true;
            }
        };
        this.maxPages = Math.ceil((double)buttons.length / 9.0);
    }

    public void createInventories(Player player) {
        int i = 0;
        while ((double)i < this.maxPages) {
            String t = Chat.translate(this.title).replace("%page%", i + 1 + "").replace("%maxpages%", (int)this.maxPages + "");
            if (t.length() > 32) {
                t = t.substring(0, 32);
            }
            Inventory inventory = Bukkit.createInventory(null, (int)27, (String)t);
            inventory.setItem(18, this.glassPane.getItemStack(player));
            inventory.setItem(19, this.glassPane.getItemStack(player));
            inventory.setItem(20, this.glassPane.getItemStack(player));
            inventory.setItem(24, this.glassPane.getItemStack(player));
            inventory.setItem(25, this.glassPane.getItemStack(player));
            inventory.setItem(26, this.glassPane.getItemStack(player));
            inventory.setItem(21, this.pageButtons[0].getItemStack(player));
            inventory.setItem(22, this.pageButtons[1].getItemStack(player));
            inventory.setItem(23, this.pageButtons[2].getItemStack(player));
            this.inventories.put(player, inventory);
            try {
                inventory.setItem(0, this.buttons[i * 9].getItemStack(player));
                inventory.setItem(1, this.buttons[i * 9 + 1].getItemStack(player));
                inventory.setItem(2, this.buttons[i * 9 + 2].getItemStack(player));
                inventory.setItem(3, this.buttons[i * 9 + 3].getItemStack(player));
                inventory.setItem(4, this.buttons[i * 9 + 4].getItemStack(player));
                inventory.setItem(5, this.buttons[i * 9 + 5].getItemStack(player));
                inventory.setItem(6, this.buttons[i * 9 + 6].getItemStack(player));
                inventory.setItem(7, this.buttons[i * 9 + 7].getItemStack(player));
                inventory.setItem(8, this.buttons[i * 9 + 8].getItemStack(player));
            }
            catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                e.printStackTrace();
                return;
            }
            ++i;
        }
    }

    @Override
    public void open(Player player) {
        if (this.inventories.get((Object)player) == null) {
            this.createInventories(player);
        }
        player.openInventory(this.inventories.get((Object)player));
    }

    public void switchPage(boolean forward, Player player) {
        if (forward) {
            this.page = (double)this.page == this.maxPages ? 1 : ++this.page;
            player.closeInventory();
            this.open(player);
        } else {
            this.page = this.page == 1 ? (int)this.maxPages : --this.page;
            player.closeInventory();
            this.open(player);
        }
    }

    public Button[] getPageButtons() {
        Button[] pageButtons = new Button[]{new Button(this){
            ItemStack itemStack;
            {
                this.itemStack = new ItemBuilder(MaterialUtils.ARROW).name("&6<- Previous Page").addLore(" ", "&7Click to go to the previous page").build();
            }

            @Override
            public ItemStack getItemStack(Player player) {
                return this.itemStack;
            }

            @Override
            public void onClick(Player player, ClickType clickType) {
                PaginatedMenu.this.switchPage(false, player);
            }

            @Override
            public boolean cancel() {
                return true;
            }
        }, new Button(this){
            ItemStack itemStack;
            {
                this.itemStack = new ItemBuilder(MaterialUtils.NAME_TAG).name("&6&l\u2732 &cClose Menu &6&l\u2732").addLore(" ", "&7Click to close the current menu.").build();
            }

            @Override
            public ItemStack getItemStack(Player player) {
                return this.itemStack;
            }

            @Override
            public void onClick(Player player, ClickType clickType) {
                player.closeInventory();
                PaginatedMenu.this.page = 1;
            }

            @Override
            public boolean cancel() {
                return true;
            }
        }, new Button(this){
            ItemStack itemStack;
            {
                this.itemStack = new ItemBuilder(MaterialUtils.ARROW).name("&6Next Page ->").addLore(" ", "&7Click to go to the next page").build();
            }

            @Override
            public ItemStack getItemStack(Player player) {
                return this.itemStack;
            }

            @Override
            public void onClick(Player player, ClickType clickType) {
                PaginatedMenu.this.switchPage(true, player);
            }

            @Override
            public boolean cancel() {
                return true;
            }
        }};
        return pageButtons;
    }

    public String getTitle() {
        return this.title;
    }

}

