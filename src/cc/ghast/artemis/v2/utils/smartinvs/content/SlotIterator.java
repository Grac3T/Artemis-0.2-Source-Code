/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.utils.smartinvs.content;

import cc.ghast.artemis.v2.utils.smartinvs.ClickableItem;
import cc.ghast.artemis.v2.utils.smartinvs.SmartInventory;
import cc.ghast.artemis.v2.utils.smartinvs.content.InventoryContents;
import cc.ghast.artemis.v2.utils.smartinvs.content.SlotPos;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public interface SlotIterator {
    public Optional<ClickableItem> get();

    public SlotIterator set(ClickableItem var1);

    public SlotIterator previous();

    public SlotIterator next();

    public SlotIterator blacklist(int var1, int var2);

    public SlotIterator blacklist(SlotPos var1);

    public int row();

    public SlotIterator row(int var1);

    public int column();

    public SlotIterator column(int var1);

    public boolean started();

    public boolean ended();

    public boolean doesAllowOverride();

    public SlotIterator allowOverride(boolean var1);

    public static class Impl
    implements SlotIterator {
        private InventoryContents contents;
        private SmartInventory inv;
        private Type type;
        private boolean started = false;
        private boolean allowOverride = true;
        private int row;
        private int column;
        private Set<SlotPos> blacklisted = new HashSet<SlotPos>();

        public Impl(InventoryContents contents, SmartInventory inv, Type type, int startRow, int startColumn) {
            this.contents = contents;
            this.inv = inv;
            this.type = type;
            this.row = startRow;
            this.column = startColumn;
        }

        public Impl(InventoryContents contents, SmartInventory inv, Type type) {
            this(contents, inv, type, 0, 0);
        }

        @Override
        public Optional<ClickableItem> get() {
            return this.contents.get(this.row, this.column);
        }

        @Override
        public SlotIterator set(ClickableItem item) {
            if (this.canPlace()) {
                this.contents.set(this.row, this.column, item);
            }
            return this;
        }

        @Override
        public SlotIterator previous() {
            if (this.row == 0 && this.column == 0) {
                this.started = true;
                return this;
            }
            do {
                if (!this.started) {
                    this.started = true;
                    continue;
                }
                switch (this.type) {
                    case HORIZONTAL: {
                        --this.column;
                        if (this.column != 0) break;
                        this.column = this.inv.getColumns() - 1;
                        --this.row;
                        break;
                    }
                    case VERTICAL: {
                        --this.row;
                        if (this.row != 0) break;
                        this.row = this.inv.getRows() - 1;
                        --this.column;
                    }
                }
            } while (!this.canPlace() && (this.row != 0 || this.column != 0));
            return this;
        }

        @Override
        public SlotIterator next() {
            if (this.ended()) {
                this.started = true;
                return this;
            }
            do {
                if (!this.started) {
                    this.started = true;
                    continue;
                }
                switch (this.type) {
                    case HORIZONTAL: {
                        ++this.column;
                        this.column %= this.inv.getColumns();
                        if (this.column != 0) break;
                        ++this.row;
                        break;
                    }
                    case VERTICAL: {
                        ++this.row;
                        this.row %= this.inv.getRows();
                        if (this.row != 0) break;
                        ++this.column;
                    }
                }
            } while (!this.canPlace() && !this.ended());
            return this;
        }

        @Override
        public SlotIterator blacklist(int row, int column) {
            this.blacklisted.add(SlotPos.of(row, column));
            return this;
        }

        @Override
        public SlotIterator blacklist(SlotPos slotPos) {
            return this.blacklist(slotPos.getRow(), slotPos.getColumn());
        }

        @Override
        public int row() {
            return this.row;
        }

        @Override
        public SlotIterator row(int row) {
            this.row = row;
            return this;
        }

        @Override
        public int column() {
            return this.column;
        }

        @Override
        public SlotIterator column(int column) {
            this.column = column;
            return this;
        }

        @Override
        public boolean started() {
            return this.started;
        }

        @Override
        public boolean ended() {
            return this.row == this.inv.getRows() - 1 && this.column == this.inv.getColumns() - 1;
        }

        @Override
        public boolean doesAllowOverride() {
            return this.allowOverride;
        }

        @Override
        public SlotIterator allowOverride(boolean override) {
            this.allowOverride = override;
            return this;
        }

        private boolean canPlace() {
            return !this.blacklisted.contains(SlotPos.of(this.row, this.column)) && (this.allowOverride || !this.get().isPresent());
        }
    }

    public static enum Type {
        HORIZONTAL,
        VERTICAL;

    }

}

