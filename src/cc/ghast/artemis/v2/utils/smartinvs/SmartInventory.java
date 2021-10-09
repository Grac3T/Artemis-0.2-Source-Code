/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 */
package cc.ghast.artemis.v2.utils.smartinvs;

import cc.ghast.artemis.v2.utils.smartinvs.InventoryListener;
import cc.ghast.artemis.v2.utils.smartinvs.InventoryManager;
import cc.ghast.artemis.v2.utils.smartinvs.SmartInvsAPI;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryContents;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryProvider;
import cc.ghast.artemis.v2.utils.smartinvs.content.Pagination;
import cc.ghast.artemis.v2.utils.smartinvs.opener.InventoryOpener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class SmartInventory {
    private String id;
    private String title;
    private InventoryType type;
    private int rows;
    private int columns;
    private boolean closeable;
    private InventoryProvider provider;
    private SmartInventory parent;
    private List<InventoryListener<? extends Event>> listeners;
    private InventoryManager manager;

    private SmartInventory(InventoryManager manager) {
        this.manager = manager;
    }

    public Inventory open(Player player) {
        return this.open(player, 0);
    }

    public Inventory open(Player player, int page) {
        Optional<SmartInventory> oldInv = this.manager.getInventory(player);
        oldInv.ifPresent(inv -> {
            inv.getListeners().stream().filter(listener -> listener.getType() == InventoryCloseEvent.class).forEach(listener -> listener.accept(new InventoryCloseEvent(player.getOpenInventory())));
            this.manager.setInventory(player, null);
        });
        InventoryContents.Impl contents = new InventoryContents.Impl(this, player);
        contents.pagination().page(page);
        this.manager.setContents(player, contents);
        this.provider.init(player, contents);
        InventoryOpener opener = this.manager.findOpener(this.type).orElseThrow(() -> new IllegalStateException("No opener found for the inventory type " + this.type.name()));
        Inventory handle = opener.open(this, player);
        this.manager.setInventory(player, this);
        return handle;
    }

    public void close(Player player) {
        this.listeners.stream().filter(listener -> listener.getType() == InventoryCloseEvent.class).forEach(listener -> listener.accept(new InventoryCloseEvent(player.getOpenInventory())));
        this.manager.setInventory(player, null);
        player.closeInventory();
        this.manager.setContents(player, null);
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public InventoryType getType() {
        return this.type;
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public boolean isCloseable() {
        return this.closeable;
    }

    public void setCloseable(boolean closeable) {
        this.closeable = closeable;
    }

    public InventoryProvider getProvider() {
        return this.provider;
    }

    public Optional<SmartInventory> getParent() {
        return Optional.ofNullable(this.parent);
    }

    public InventoryManager getManager() {
        return this.manager;
    }

    List<InventoryListener<? extends Event>> getListeners() {
        return this.listeners;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id = "unknown";
        private String title = "";
        private InventoryType type = InventoryType.CHEST;
        private int rows = 6;
        private int columns = 9;
        private boolean closeable = true;
        private InventoryManager manager;
        private InventoryProvider provider;
        private SmartInventory parent;
        private List<InventoryListener<? extends Event>> listeners = new ArrayList<InventoryListener<? extends Event>>();

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder type(InventoryType type) {
            this.type = type;
            return this;
        }

        public Builder size(int rows, int columns) {
            this.rows = rows;
            this.columns = columns;
            return this;
        }

        public Builder closeable(boolean closeable) {
            this.closeable = closeable;
            return this;
        }

        public Builder provider(InventoryProvider provider) {
            this.provider = provider;
            return this;
        }

        public Builder parent(SmartInventory parent) {
            this.parent = parent;
            return this;
        }

        public Builder listener(InventoryListener<? extends Event> listener) {
            this.listeners.add(listener);
            return this;
        }

        public Builder manager(InventoryManager manager) {
            this.manager = manager;
            return this;
        }

        public SmartInventory build() {
            InventoryManager manager;
            if (this.provider == null) {
                throw new IllegalStateException("The provider of the SmartInventory.Builder must be set.");
            }
            InventoryManager inventoryManager = manager = this.manager != null ? this.manager : SmartInvsAPI.manager();
            if (manager == null) {
                throw new IllegalStateException("The managers of the SmartInventory.Builder must be set, or the SmartInvs should be loaded as a plugin.");
            }
            SmartInventory inv = new SmartInventory(manager);
            inv.id = this.id;
            inv.title = this.title;
            inv.type = this.type;
            inv.rows = this.rows;
            inv.columns = this.columns;
            inv.closeable = this.closeable;
            inv.provider = this.provider;
            inv.parent = this.parent;
            inv.listeners = this.listeners;
            return inv;
        }
    }

}

