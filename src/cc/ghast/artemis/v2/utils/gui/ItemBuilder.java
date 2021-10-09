/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.material.MaterialData
 */
package cc.ghast.artemis.v2.utils.gui;

import cc.ghast.artemis.v2.utils.chat.Chat;
import cc.ghast.artemis.v2.utils.gui.MaterialUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class ItemBuilder {
    private final ItemStack base;
    private ItemMeta baseMeta;

    public ItemBuilder(ItemStack base) {
        this.base = base;
        this.baseMeta = base.getItemMeta();
    }

    public ItemBuilder(MaterialUtils material) {
        this(new ItemStack(material.getMaterial()));
    }

    public ItemBuilder type(Material type) {
        this.base.setItemMeta(this.baseMeta);
        this.base.setType(type);
        this.baseMeta = this.base.getItemMeta();
        return this;
    }

    public ItemBuilder data(short data) {
        this.base.setDurability(data);
        return this;
    }

    public ItemBuilder data(MaterialData data) {
        this.base.setData(data);
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.base.setAmount(amount);
        return this;
    }

    public ItemBuilder name(String name) {
        this.baseMeta.setDisplayName(Chat.translate(name));
        return this;
    }

    public ItemBuilder lore(String ... lores) {
        this.baseMeta.setLore(Stream.of(lores).map(Chat::translate).collect(Collectors.toList()));
        return this;
    }

    public ItemBuilder addLore(String ... lores) {
        List lore = Optional.ofNullable(this.baseMeta.getLore()).orElseGet(ArrayList::new);
        lore.addAll(Stream.of(lores).map(Chat::translate).collect(Collectors.toList()));
        this.baseMeta.setLore(lore);
        return this;
    }

    public ItemStack build() {
        this.base.setItemMeta(this.baseMeta);
        return this.base.clone();
    }
}

