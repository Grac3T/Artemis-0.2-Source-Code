/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.utils.smartinvs.content;

public class SlotPos {
    private final int row;
    private final int column;

    public SlotPos(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        SlotPos slotPos = (SlotPos)obj;
        return this.row == slotPos.row && this.column == slotPos.column;
    }

    public int hashCode() {
        int result = this.row;
        result = 31 * result + this.column;
        return result;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public static SlotPos of(int row, int column) {
        return new SlotPos(row, column);
    }
}

