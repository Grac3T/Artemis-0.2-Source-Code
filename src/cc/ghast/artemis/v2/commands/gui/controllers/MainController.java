/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.ItemStack
 */
package cc.ghast.artemis.v2.commands.gui.controllers;

import cc.ghast.artemis.v2.Artemis;
import cc.ghast.artemis.v2.api.API;
import cc.ghast.artemis.v2.api.check.AbstractCheck;
import cc.ghast.artemis.v2.api.check.CheckManager;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.theme.AbstractTheme;
import cc.ghast.artemis.v2.commands.gui.gui.BansGUI;
import cc.ghast.artemis.v2.commands.gui.gui.ChecksGUI;
import cc.ghast.artemis.v2.managers.ConfigManager;
import cc.ghast.artemis.v2.managers.PlayerDataManager;
import cc.ghast.artemis.v2.managers.ThemeManager;
import cc.ghast.artemis.v2.utils.gui.ItemBuilder;
import cc.ghast.artemis.v2.utils.gui.MaterialUtils;
import cc.ghast.artemis.v2.utils.smartinvs.ClickableItem;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryContents;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryProvider;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MainController
implements InventoryProvider {
    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fill(ClickableItem.empty(new ItemBuilder(MaterialUtils.GRAY_STAINED_GLASS_PANE).name("&4").build()));
        contents.set(1, 2, ClickableItem.of(new ItemBuilder(MaterialUtils.MINECART).name("&aArtemis Anti-Cheat &b&lChecks").addLore("&7Clicking this item will allow you to modify", "&7the checks currently enabled and", "&7disabled on Artemis Anti-Cheat!", "&7", "&aPlayer Checks \u2714", "&aCombat Checks \u2714", "&aMovement Checks \u2714", "&7", "&a\u276f " + Artemis.INSTANCE.getApi().getPlayerDataManager().getData(player).getCheckManager().getAbstractChecks().size() + " Checks Enabled").build(), e -> ChecksGUI.openInventory(player)));
        contents.set(1, 3, ClickableItem.of(new ItemBuilder(MaterialUtils.TNT).name("&aArtemis Anti-Cheat &b&lPunishments").addLore("&7Clicking this item will allow you to see", "&7the full list of punishments", "&7on the Artemis AntiCheat!", "&7", "&aExample punishments:", "&7Example - KillAura V17", "&7Example2 - Speed V12", "&7Example3 - AimAssist V15").build(), e -> BansGUI.openInventory(player)));
        contents.set(1, 4, ClickableItem.of(new ItemBuilder(MaterialUtils.ANVIL).name("&aArtemis AntiCheat &b&lOptions").addLore("&7Clicking this item allows you to modify", "&7the full list of options", "&7on Artemis Anti-Cheat!").build(), e -> {}));
        contents.set(1, 5, ClickableItem.of(new ItemBuilder(MaterialUtils.ITEM_FRAME).name("&aArtemis AntiCheat &b&lTheme").addLore("&7Clicking this item allows you to rotate", "&7the full customizability options", "&7on Artemis Anti-Cheat!", "&7", "&a\u276f Current: " + Artemis.INSTANCE.getApi().getThemeManager().getCurrentTheme().getId(), "&7", "&bExample: " + Artemis.INSTANCE.getApi().getThemeManager().getCurrentTheme().getPrefix()).build(), e -> {}));
        contents.set(1, 6, ClickableItem.of(new ItemBuilder(MaterialUtils.ANVIL).name("&aArtemis Anticheat &b&l1.0 B1!").addLore("&7You are running the latest", "&7version of Artemis Anticheat \u2714", "&4", "&bLicense&7: &c" + (ConfigManager.getSettings().getBoolean("license.hide_in_gui") ? "Hidden" : ConfigManager.getSettings().getString("license.key")), "&bRegistered to: &c" + ConfigManager.getSettings().getString("license.username")).build(), e -> {}));
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        contents.set(1, 2, ClickableItem.of(new ItemBuilder(MaterialUtils.MINECART).name("&aArtemis Anti-Cheat &b&lChecks").addLore("&7Clicking this item will allow you to modify", "&7the checks currently enabled and", "&7disabled on Artemis Anti-Cheat!", "&7", "&aPlayer Checks \u2714", "&aCombat Checks \u2714", "&aMovement Checks \u2714", "&7", "&a\u276f " + Artemis.INSTANCE.getApi().getPlayerDataManager().getData(player).getCheckManager().getAbstractChecks().size() + " Checks Enabled").build(), e -> ChecksGUI.openInventory(player)));
    }
}

