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
import cc.ghast.artemis.v2.api.theme.AbstractTheme;
import cc.ghast.artemis.v2.commands.gui.gui.CheckListGUI;
import cc.ghast.artemis.v2.commands.gui.gui.CheckSubListGUI;
import cc.ghast.artemis.v2.commands.gui.gui.ChecksGUI;
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
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CheckListController
implements InventoryProvider {
    private String type;

    public CheckListController(String type) {
        this.type = type;
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();
        Configuration config = ConfigManager.getChecks();
        ConfigurationSection section = config.getConfig().getConfigurationSection(this.type.toLowerCase());
        Set<String> vars = section.getKeys(false);
        ArrayList items = new ArrayList();
        String main = Artemis.INSTANCE.getApi().getThemeManager().getCurrentTheme().getMainColor();
        String side = Artemis.INSTANCE.getApi().getThemeManager().getCurrentTheme().getSecondaryColor();
        vars.forEach(check -> {
            ClickableItem stack = ClickableItem.of(new ItemBuilder(MaterialUtils.BOOK).name(main + this.type + side + ":" + main + check).build(), e -> CheckSubListGUI.openInventory(player, this.type.toLowerCase(), check.toLowerCase()));
            items.add(stack);
        });

        pagination.setItems(new ClickableItem[items.size()]);
        pagination.setItemsPerPage(27);
        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0));
        contents.set(3, 3, ClickableItem.of(new ItemBuilder(MaterialUtils.ARROW).name("&cPrevious Page").build(), e -> CheckListGUI.openInventory(player, pagination.previous().getPage(), this.type)));
        contents.set(3, 4, ClickableItem.of(new ItemBuilder(MaterialUtils.BARRIER.getItemStack()).name("&4&lEXIT").build(), e -> ChecksGUI.inv.open(player)));
        contents.set(3, 5, ClickableItem.of(new ItemBuilder(MaterialUtils.ARROW).name("&aNext Page").build(), e -> CheckListGUI.openInventory(player, pagination.next().getPage(), this.type)));
    }

    @Override
    public void update(Player player, InventoryContents contents) {
    }
}

