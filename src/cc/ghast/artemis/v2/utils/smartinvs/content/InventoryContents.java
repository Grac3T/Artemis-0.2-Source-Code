/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 */
package cc.ghast.artemis.v2.utils.smartinvs.content;

import cc.ghast.artemis.v2.utils.smartinvs.ClickableItem;
import cc.ghast.artemis.v2.utils.smartinvs.InventoryManager;
import cc.ghast.artemis.v2.utils.smartinvs.SmartInventory;
import cc.ghast.artemis.v2.utils.smartinvs.content.Pagination;
import cc.ghast.artemis.v2.utils.smartinvs.content.SlotIterator;
import cc.ghast.artemis.v2.utils.smartinvs.content.SlotPos;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public interface InventoryContents {
    public SmartInventory inventory();

    public Pagination pagination();

    public Optional<SlotIterator> iterator(String var1);

    public SlotIterator newIterator(String var1, SlotIterator.Type var2, int var3, int var4);

    public SlotIterator newIterator(SlotIterator.Type var1, int var2, int var3);

    public SlotIterator newIterator(String var1, SlotIterator.Type var2, SlotPos var3);

    public SlotIterator newIterator(SlotIterator.Type var1, SlotPos var2);

    public ClickableItem[][] all();

    public Optional<SlotPos> firstEmpty();

    public Optional<ClickableItem> get(int var1, int var2);

    public Optional<ClickableItem> get(SlotPos var1);

    public InventoryContents set(int var1, int var2, ClickableItem var3);

    public InventoryContents set(SlotPos var1, ClickableItem var2);

    public InventoryContents add(ClickableItem var1);

    public InventoryContents fill(ClickableItem var1);

    public InventoryContents fillRow(int var1, ClickableItem var2);

    public InventoryContents fillColumn(int var1, ClickableItem var2);

    public InventoryContents fillBorders(ClickableItem var1);

    public InventoryContents fillRect(int var1, int var2, int var3, int var4, ClickableItem var5);

    public InventoryContents fillRect(SlotPos var1, SlotPos var2, ClickableItem var3);

    public <T> T property(String var1);

    public <T> T property(String var1, T var2);

    public InventoryContents setProperty(String var1, Object var2);

    public static class Impl
    implements InventoryContents {
        private SmartInventory inv;
        private Player player;
        private ClickableItem[][] contents;
        private Pagination pagination = new Pagination.Impl();
        private Map<String, SlotIterator> iterators = new HashMap<String, SlotIterator>();
        private Map<String, Object> properties = new HashMap<String, Object>();

        public Impl(SmartInventory inv, Player player) {
            this.inv = inv;
            this.player = player;
            this.contents = new ClickableItem[inv.getRows()][inv.getColumns()];
        }

        @Override
        public SmartInventory inventory() {
            return this.inv;
        }

        @Override
        public Pagination pagination() {
            return this.pagination;
        }

        @Override
        public Optional<SlotIterator> iterator(String id) {
            return Optional.ofNullable(this.iterators.get(id));
        }

        @Override
        public SlotIterator newIterator(String id, SlotIterator.Type type, int startRow, int startColumn) {
            SlotIterator.Impl iterator = new SlotIterator.Impl(this, this.inv, type, startRow, startColumn);
            this.iterators.put(id, iterator);
            return iterator;
        }

        @Override
        public SlotIterator newIterator(String id, SlotIterator.Type type, SlotPos startPos) {
            return this.newIterator(id, type, startPos.getRow(), startPos.getColumn());
        }

        @Override
        public SlotIterator newIterator(SlotIterator.Type type, int startRow, int startColumn) {
            return new SlotIterator.Impl(this, this.inv, type, startRow, startColumn);
        }

        @Override
        public SlotIterator newIterator(SlotIterator.Type type, SlotPos startPos) {
            return this.newIterator(type, startPos.getRow(), startPos.getColumn());
        }

        @Override
        public ClickableItem[][] all() {
            return this.contents;
        }

        @Override
        public Optional<SlotPos> firstEmpty() {
            for (int row = 0; row < this.contents.length; ++row) {
                for (int column = 0; column < this.contents[0].length; ++column) {
                    if (this.get(row, column).isPresent()) continue;
                    return Optional.of(new SlotPos(row, column));
                }
            }
            return Optional.empty();
        }

        @Override
        public Optional<ClickableItem> get(int row, int column) {
            if (row >= this.contents.length) {
                return Optional.empty();
            }
            if (column >= this.contents[row].length) {
                return Optional.empty();
            }
            return Optional.ofNullable(this.contents[row][column]);
        }

        @Override
        public Optional<ClickableItem> get(SlotPos slotPos) {
            return this.get(slotPos.getRow(), slotPos.getColumn());
        }

        @Override
        public InventoryContents set(int row, int column, ClickableItem item) {
            if (row >= this.contents.length) {
                return this;
            }
            if (column >= this.contents[row].length) {
                return this;
            }
            this.contents[row][column] = item;
            this.update(row, column, item != null ? item.getItem() : null);
            return this;
        }

        @Override
        public InventoryContents set(SlotPos slotPos, ClickableItem item) {
            return this.set(slotPos.getRow(), slotPos.getColumn(), item);
        }

        @Override
        public InventoryContents add(ClickableItem item) {
            for (int row = 0; row < this.contents.length; ++row) {
                for (int column = 0; column < this.contents[0].length; ++column) {
                    if (this.contents[row][column] != null) continue;
                    this.set(row, column, item);
                    return this;
                }
            }
            return this;
        }

        @Override
        public InventoryContents fill(ClickableItem item) {
            for (int row = 0; row < this.contents.length; ++row) {
                for (int column = 0; column < this.contents[row].length; ++column) {
                    this.set(row, column, item);
                }
            }
            return this;
        }

        @Override
        public InventoryContents fillRow(int row, ClickableItem item) {
            if (row >= this.contents.length) {
                return this;
            }
            for (int column = 0; column < this.contents[row].length; ++column) {
                this.set(row, column, item);
            }
            return this;
        }

        @Override
        public InventoryContents fillColumn(int column, ClickableItem item) {
            for (int row = 0; row < this.contents.length; ++row) {
                this.set(row, column, item);
            }
            return this;
        }

        @Override
        public InventoryContents fillBorders(ClickableItem item) {
            this.fillRect(0, 0, this.inv.getRows() - 1, this.inv.getColumns() - 1, item);
            return this;
        }

        @Override
        public InventoryContents fillRect(int fromRow, int fromColumn, int toRow, int toColumn, ClickableItem item) {
            for (int row = fromRow; row <= toRow; ++row) {
                for (int column = fromColumn; column <= toColumn; ++column) {
                    if (row != fromRow && row != toRow && column != fromColumn && column != toColumn) continue;
                    this.set(row, column, item);
                }
            }
            return this;
        }

        @Override
        public InventoryContents fillRect(SlotPos fromPos, SlotPos toPos, ClickableItem item) {
            return this.fillRect(fromPos.getRow(), fromPos.getColumn(), toPos.getRow(), toPos.getColumn(), item);
        }

        @Override
        public <T> T property(String name) {
            return (T)this.properties.get(name);
        }

        @Override
        public <T> T property(String name, T def) {
            return (T)(this.properties.containsKey(name) ? this.properties.get(name) : def);
        }

        @Override
        public InventoryContents setProperty(String name, Object value) {
            this.properties.put(name, value);
            return this;
        }

        private void update(int row, int column, ItemStack item) {
            if (!this.inv.getManager().getOpenedPlayers(this.inv).contains((Object)this.player)) {
                return;
            }
            Inventory topInventory = this.player.getOpenInventory().getTopInventory();
            topInventory.setItem(this.inv.getColumns() * row + column, item);
        }
    }

}

