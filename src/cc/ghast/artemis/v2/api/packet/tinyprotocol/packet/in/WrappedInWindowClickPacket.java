/*
 * Decompiled with CFR 0.148.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.in;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.FieldAccessor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WrappedInWindowClickPacket
extends NMSObject {
    private static final String packet = "PacketPlayInWindowClick";
    private static FieldAccessor<Integer> fieldId = WrappedInWindowClickPacket.fetchField("PacketPlayInWindowClick", Integer.TYPE, 0);
    private static FieldAccessor<Integer> fieldSlot = WrappedInWindowClickPacket.fetchField("PacketPlayInWindowClick", Integer.TYPE, 1);
    private static FieldAccessor<Integer> fieldButton = WrappedInWindowClickPacket.fetchField("PacketPlayInWindowClick", Integer.TYPE, 2);
    private static FieldAccessor<Short> fieldAction = WrappedInWindowClickPacket.fetchField("PacketPlayInWindowClick", Short.TYPE, 0);
    private static FieldAccessor<Object> fieldItemStack = WrappedInWindowClickPacket.fetchField("PacketPlayInWindowClick", NMSObject.Type.ITEMSTACK, 0);
    private int id;
    private short slot;
    private byte button;
    private short counter;
    private ClickType action;
    private ItemStack item;
    private byte mode;

    public WrappedInWindowClickPacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        this.id = this.fetch(fieldId);
        this.slot = this.fetch(fieldSlot).shortValue();
        byte button = this.fetch(fieldButton).byteValue();
        this.counter = this.fetch(fieldAction);
        this.item = WrappedInWindowClickPacket.toBukkitStack(this.fetch(fieldItemStack));
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_9)) {
            FieldAccessor<Integer> fieldShift = WrappedInWindowClickPacket.fetchField(packet, Integer.TYPE, 3);
            this.mode = this.fetch(fieldShift).byteValue();
        } else {
            FieldAccessor<Enum> fieldShift = WrappedInWindowClickPacket.fetchField(packet, Enum.class, 0);
            this.mode = (byte)this.fetch(fieldShift).ordinal();
        }
        if (this.slot == -1) {
            this.action = button == 0 ? ClickType.WINDOW_BORDER_LEFT : ClickType.WINDOW_BORDER_RIGHT;
        } else if (this.mode == 0) {
            if (button == 0) {
                this.action = ClickType.LEFT;
            } else if (button == 1) {
                this.action = ClickType.RIGHT;
            }
        } else if (this.mode == 1) {
            if (button == 0) {
                this.action = ClickType.SHIFT_LEFT;
            } else if (button == 1) {
                this.action = ClickType.SHIFT_RIGHT;
            }
        } else if (this.mode == 2) {
            if (button >= 0 && button < 9) {
                this.action = ClickType.NUMBER_KEY;
            }
        } else if (this.mode == 3) {
            this.action = button == 2 ? ClickType.MIDDLE : ClickType.UNKNOWN;
        } else if (this.mode == 4) {
            if (this.slot >= 0) {
                if (button == 0) {
                    this.action = ClickType.DROP;
                } else if (button == 1) {
                    this.action = ClickType.CONTROL_DROP;
                }
            } else {
                this.action = ClickType.LEFT;
                if (button == 1) {
                    this.action = ClickType.RIGHT;
                }
            }
        } else if (this.mode == 5) {
            this.action = ClickType.DRAG;
        } else if (this.mode == 6) {
            this.action = ClickType.DOUBLE_CLICK;
        }
    }

    public int getId() {
        return this.id;
    }

    public short getSlot() {
        return this.slot;
    }

    public byte getButton() {
        return this.button;
    }

    public short getCounter() {
        return this.counter;
    }

    public ClickType getAction() {
        return this.action;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public byte getMode() {
        return this.mode;
    }

    public static enum ClickType {
        LEFT,
        SHIFT_LEFT,
        RIGHT,
        SHIFT_RIGHT,
        WINDOW_BORDER_LEFT,
        WINDOW_BORDER_RIGHT,
        MIDDLE,
        NUMBER_KEY,
        DOUBLE_CLICK,
        DROP,
        CONTROL_DROP,
        CREATIVE,
        DRAG,
        UNKNOWN;


        public boolean isKeyboardClick() {
            return this == NUMBER_KEY || this == DROP || this == CONTROL_DROP;
        }

        public boolean isCreativeAction() {
            return this == MIDDLE || this == CREATIVE;
        }

        public boolean isRightClick() {
            return this == RIGHT || this == SHIFT_RIGHT;
        }

        public boolean isLeftClick() {
            return this == LEFT || this == SHIFT_LEFT || this == DOUBLE_CLICK || this == CREATIVE;
        }

        public boolean isShiftClick() {
            return this == SHIFT_LEFT || this == SHIFT_RIGHT || this == CONTROL_DROP;
        }
    }

}

