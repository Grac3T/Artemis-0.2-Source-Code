/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 */
package cc.ghast.artemis.v2.commands.gui.controllers;

import cc.ghast.artemis.v2.commands.gui.gui.BansChecksGUI;
import cc.ghast.artemis.v2.commands.gui.gui.BansGUI;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.utils.configuration.Configuration;
import cc.ghast.artemis.v2.utils.gui.ItemBuilder;
import cc.ghast.artemis.v2.utils.gui.MaterialUtils;
import cc.ghast.artemis.v2.utils.smartinvs.ClickableItem;
import cc.ghast.artemis.v2.utils.smartinvs.SmartInventory;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryContents;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryProvider;
import cc.ghast.artemis.v2.utils.smartinvs.content.Pagination;
import cc.ghast.artemis.v2.utils.smartinvs.content.SlotIterator;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BansController
implements InventoryProvider {
    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();
        Configuration config = ConfigManager.getBans();
        Set<String> keys = config.getConfig().getConfigurationSection("").getKeys(false);
        ClickableItem[] items = new ClickableItem[keys.size()];
        int i = 0;
        for (String s : keys) {
            items[i] = ClickableItem.of(new ItemBuilder(MaterialUtils.BOOK.getItemStack()).name("&b" + config.getString(s + ".name")).addLore("&bUUID &7: &b" + s, "&bDate &7: &b" + config.getString(s + ".date"), "&bFlagged Checks &7: &b" + config.getInt(s + ".total-flags"), "&bStatus &7: &b" + (config.getBoolean(s + ".active") ? "&a&lACTIVE" : "&c&lINACTIVE")).build(), e -> BansChecksGUI.openInventory(player, s));
            ++i;
        }
        pagination.setItems(items);
        pagination.setItemsPerPage(27);
        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0));
        contents.set(3, 3, ClickableItem.of(new ItemStack(Material.ARROW), e -> BansGUI.inv.open(player, pagination.previous().getPage())));
        contents.set(3, 5, ClickableItem.of(new ItemStack(Material.ARROW), e -> BansGUI.inv.open(player, pagination.next().getPage())));
    }

    @Override
    public void update(Player player, InventoryContents contents) {
    }
}

