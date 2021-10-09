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

public class BansChecksController
implements InventoryProvider {
    private final String uuid;

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();
        Configuration config = ConfigManager.getBans();
        Set<String> flags = config.getConfig().getConfigurationSection(this.uuid + ".flag").getKeys(false);
        ClickableItem[] items = new ClickableItem[flags.size()];
        int i = 0;
        for (String s : flags) {
            items[i] = ClickableItem.empty(new ItemBuilder(MaterialUtils.PAPER.getItemStack()).name("&b" + s).addLore("&b&lViolations&7: &b" + config.getInt(this.uuid + ".flag." + s + ".count"), "&b&lTimestamp&7: &b" + config.getInt(this.uuid + ".flag." + s + ".timestamp")).build());
            ++i;
        }
        pagination.setItems(items);
        pagination.setItemsPerPage(27);
        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0));
        contents.set(3, 3, ClickableItem.of(new ItemStack(Material.ARROW), e -> BansChecksGUI.returnCheckGui(this.uuid).open(player, pagination.previous().getPage())));
        contents.set(3, 5, ClickableItem.of(new ItemStack(Material.ARROW), e -> BansChecksGUI.returnCheckGui(this.uuid).open(player, pagination.next().getPage())));
        contents.set(3, 4, ClickableItem.of(new ItemBuilder(MaterialUtils.BARRIER.getItemStack()).name("&4&lEXIT").build(), e -> BansGUI.inv.open(player, pagination.getPage())));
    }

    @Override
    public void update(Player player, InventoryContents contents) {
    }

    public BansChecksController(String uuid) {
        this.uuid = uuid;
    }
}

