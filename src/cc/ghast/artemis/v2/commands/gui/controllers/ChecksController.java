/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 */
package cc.ghast.artemis.v2.commands.gui.controllers;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.api.API;
import cc.ghast.artemis.v2.api.check.enums.Category;
import cc.ghast.artemis.v2.api.theme.AbstractTheme;
import cc.ghast.artemis.v2.commands.gui.gui.CheckListGUI;
import cc.ghast.artemis.v2.commands.gui.gui.ChecksGUI;
import cc.ghast.artemis.v2.commands.gui.gui.MainGUI;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.managers.ThemeManager;
import cc.ghast.artemis.v2.utils.configuration.Configuration;
import cc.ghast.artemis.v2.utils.gui.ItemBuilder;
import cc.ghast.artemis.v2.utils.gui.MaterialUtils;
import cc.ghast.artemis.v2.utils.smartinvs.ClickableItem;
import cc.ghast.artemis.v2.utils.smartinvs.SmartInventory;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryContents;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryProvider;
import cc.ghast.artemis.v2.utils.smartinvs.content.Pagination;
import cc.ghast.artemis.v2.utils.smartinvs.content.SlotIterator;
import java.util.ArrayList;
import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChecksController
implements InventoryProvider {
    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();
        contents.fillRow(0, ClickableItem.empty(new ItemBuilder(MaterialUtils.GRAY_STAINED_GLASS_PANE).name("&7").build()));
        contents.fillRow(2, ClickableItem.empty(new ItemBuilder(MaterialUtils.GRAY_STAINED_GLASS_PANE).name("&7").build()));
        Configuration config = ConfigManager.getChecks();
        ConfigurationSection section = config.getConfig().getConfigurationSection("");
        Set types = section.getKeys(false);
        ArrayList<ClickableItem> items = new ArrayList<ClickableItem>();
        String main = Artemis.INSTANCE.getApi().getThemeManager().getCurrentTheme().getMainColor();
        String side = Artemis.INSTANCE.getApi().getThemeManager().getCurrentTheme().getSecondaryColor();
        for (Category type : Category.values()) {
            Set checks = section.getConfigurationSection(type.name().toLowerCase()).getKeys(false);
            ClickableItem stack = ClickableItem.of(new ItemBuilder(MaterialUtils.BOOKSHELF.getItemStack()).name(main + type.name()).addLore(type.getDescription()).addLore("&7", "&aThere are currently " + checks.size() + " " + type.name() + " checks").build(), e -> CheckListGUI.openInventory(player, type.name()));
            items.add(stack);
        }
        pagination.setItems(items.toArray(new ClickableItem[items.size()]));
        contents.set(2, 3, ClickableItem.of(new ItemBuilder(MaterialUtils.ARROW).name("&cPrevious Page").build(), e -> ChecksGUI.inv.open(player, pagination.previous().getPage())));
        contents.set(2, 4, ClickableItem.of(new ItemBuilder(MaterialUtils.BARRIER.getItemStack()).name("&4&lEXIT").build(), e -> MainGUI.inv.open(player)));
        contents.set(2, 5, ClickableItem.of(new ItemBuilder(MaterialUtils.ARROW).name("&aNext Page").build(), e -> ChecksGUI.inv.open(player, pagination.next().getPage())));
        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 2));
        pagination.setItemsPerPage(9);
    }

    @Override
    public void update(Player player, InventoryContents contents) {
    }
}

