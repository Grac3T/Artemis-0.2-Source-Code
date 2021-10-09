/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.ItemStack
 */
package cc.ghast.artemis.v2.commands.gui.controllers;

import cc.ghast.artemis.v2.api.check.enums.Category;
import cc.ghast.artemis.v2.api.check.enums.Type;
import cc.ghast.artemis.v2.api.check.exceptions.CheckNotFoundException;
import cc.ghast.artemis.v2.commands.gui.gui.CheckInfoGUI;
import cc.ghast.artemis.v2.commands.gui.gui.CheckSubListGUI;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.utils.chat.Chat;
import cc.ghast.artemis.v2.utils.gui.ItemBuilder;
import cc.ghast.artemis.v2.utils.gui.MaterialUtils;
import cc.ghast.artemis.v2.utils.smartinvs.ClickableItem;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryContents;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryProvider;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CheckInfoController
implements InventoryProvider {
    public final String check;
    private final Type type;
    private final String var;
    private boolean enabled;
    private int maxVls;

    public CheckInfoController(String check) {
        this.check = check;
        this.type = Arrays.stream(Type.values()).filter(type -> check.toUpperCase().contains(type.name())).findFirst().orElseThrow(CheckNotFoundException::new);
        this.var = check.toUpperCase().replace(this.type.name(), "");
        this.enabled = ConfigManager.getChecks().getBoolean(this.type.getCategory().name().toLowerCase() + "." + this.type.name().toLowerCase() + "." + this.var + ".enabled");
        this.maxVls = ConfigManager.getChecks().getInt(this.type.getCategory().name().toLowerCase() + "." + this.type.name().toLowerCase() + "." + this.var + ".max-vls");
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fillBorders(ClickableItem.empty(new ItemBuilder(MaterialUtils.GRAY_STAINED_GLASS_PANE.getItemStack()).name("&b").build()));
        contents.set(1, 3, ClickableItem.of(this.enabled ? new ItemBuilder(MaterialUtils.LIME_DYE.getItemStack()).name("&aACTIVE").build() : new ItemBuilder(MaterialUtils.GRAY_DYE.getItemStack()).name("&cINACTIVE").build(), e -> {
            ConfigManager.getChecks().set(this.type.getCategory().name().toLowerCase() + "." + this.type.name().toLowerCase() + "." + this.var + ".enabled", !this.enabled);
            ConfigManager.getChecks().save();
            ConfigManager.reload();
            player.sendMessage(Chat.translate(ConfigManager.getSettings().getString("general.prefix") + "&b" + " toggled check " + (!this.enabled ? "&a&lEnabled" : "&c&lDisabled")));
            CheckInfoGUI.openInventory(player, this.check);
        }));
        contents.set(1, 4, ClickableItem.empty(new ItemBuilder(MaterialUtils.PAPER.getItemStack()).name("&b" + this.type.name() + "&7" + " (" + "&b" + this.var + "&7" + ")").addLore(Chat.translate("&bCheck type&7: &b" + this.type.name()), Chat.translate("&bCheck variable&7: &b" + this.var), Chat.translate("&bMax violations&7: &b" + this.maxVls), Chat.translate("&bStatus&7: " + (this.enabled ? "&a&lEnabled" : "&c&lDisabled"))).build()));
        contents.set(1, 8, ClickableItem.of(new ItemBuilder(MaterialUtils.BARRIER.getItemStack()).name("&4&lEXIT").build(), e -> CheckSubListGUI.openInventory(player, this.type.getCategory().name(), this.type.name())));
    }

    @Override
    public void update(Player player, InventoryContents contents) {
    }
}

