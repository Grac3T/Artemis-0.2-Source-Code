/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.BaseBlockPosition;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.MathHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class EnumDirection {
    public static final /* enum */ EnumDirection DOWN;
    public static final /* enum */ EnumDirection UP;
    public static final /* enum */ EnumDirection NORTH;
    public static final /* enum */ EnumDirection SOUTH;
    public static final /* enum */ EnumDirection WEST;
    public static final /* enum */ EnumDirection EAST;
    private static final EnumDirection[] n;
    private static final EnumDirection[] o;
    private static final Map<String, EnumDirection> p;
    private final int h;
    private final int i;
    private final String j;
    private final EnumAxis k;
    private final EnumAxisDirection l;
    private final BaseBlockPosition m;
    private static final /* synthetic */ EnumDirection[] $VALUES;

    public static EnumDirection[] values() {
        return (EnumDirection[])$VALUES.clone();
    }

//    public static EnumDirection valueOf(String name) {
//        return Enum.valueOf(EnumDirection.class, name);
//    }

    private EnumDirection(int order, int offset, String direction, EnumAxisDirection axisDirection, EnumAxis axis, BaseBlockPosition offsetPosition) {
        this.i = offset;
        this.h = order;
        this.j = direction;
        this.k = axis;
        this.l = axisDirection;
        this.m = offsetPosition;
    }

    public static EnumDirection fromType1(int var0) {
        return n[MathHelper.a(var0 % n.length)];
    }

    public static EnumDirection fromType2(int var0) {
        return o[MathHelper.a(var0 % o.length)];
    }

    public static EnumDirection fromAngle(double var0) {
        return EnumDirection.fromType2(MathHelper.floor(var0 / 90.0 + 0.5) & 3);
    }

    public static EnumDirection a(Random var0) {
        return EnumDirection.values()[var0.nextInt(EnumDirection.values().length)];
    }

    public static EnumDirection a(EnumAxisDirection var0, EnumAxis var1) {
        for (EnumDirection var5 : EnumDirection.values()) {
            if (var5.c() != var0 || var5.k() != var1) continue;
            return var5;
        }
        throw new IllegalArgumentException("No such direction: " + (Object)((Object)var0) + " " + (Object)((Object)var1));
    }

    public int b() {
        return this.i;
    }

    public EnumAxisDirection c() {
        return this.l;
    }

    public EnumDirection opposite() {
        return EnumDirection.fromType1(this.h);
    }

    public int getAdjacentX() {
        return this.k == EnumAxis.X ? this.l.a() : 0;
    }

    public int getAdjacentY() {
        return this.k == EnumAxis.Y ? this.l.a() : 0;
    }

    public int getAdjacentZ() {
        return this.k == EnumAxis.Z ? this.l.a() : 0;
    }

    public String j() {
        return this.j;
    }

    public EnumAxis k() {
        return this.k;
    }

    public String toString() {
        return this.j;
    }

    public String getName() {
        return this.j;
    }

    static {
        EnumDirection[] var0;
        DOWN = new EnumDirection(1, -1, "down", EnumAxisDirection.NEGATIVE, EnumAxis.Y, new BaseBlockPosition(0, -1, 0));
        UP = new EnumDirection(0, -1, "up", EnumAxisDirection.POSITIVE, EnumAxis.Y, new BaseBlockPosition(0, 1, 0));
        NORTH = new EnumDirection(3, 2, "north", EnumAxisDirection.NEGATIVE, EnumAxis.Z, new BaseBlockPosition(0, 0, -1));
        SOUTH = new EnumDirection(2, 0, "south", EnumAxisDirection.POSITIVE, EnumAxis.Z, new BaseBlockPosition(0, 0, 1));
        WEST = new EnumDirection(5, 1, "west", EnumAxisDirection.NEGATIVE, EnumAxis.X, new BaseBlockPosition(-1, 0, 0));
        EAST = new EnumDirection(4, 3, "east", EnumAxisDirection.POSITIVE, EnumAxis.X, new BaseBlockPosition(1, 0, 0));
        $VALUES = new EnumDirection[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
        n = new EnumDirection[6];
        o = new EnumDirection[4];
        p = new HashMap<String, EnumDirection>();
        EnumDirection[] arrenumDirection = var0 = EnumDirection.values();
        int n = arrenumDirection.length;
        for (int i = 0; i < n; ++i) {
//            EnumDirection var3 = var0[i];
//            EnumDirection.n[var3.ordinal()] = var3 = arrenumDirection[i];
//            if (var3.k().c()) {
//                EnumDirection.o[var3.i] = var3;
//            }
//            p.put(var3.j().toLowerCase(), var3);
        }
    }

    public static enum EnumAxis {
        X("x", EnumDirectionLimit.HORIZONTAL),
        Y("y", EnumDirectionLimit.VERTICAL),
        Z("z", EnumDirectionLimit.HORIZONTAL);

        private static final Map<String, EnumAxis> d;
        private final String e;
        private final EnumDirectionLimit f;

        private EnumAxis(String var3, EnumDirectionLimit var4) {
            this.e = var3;
            this.f = var4;
        }

        public String a() {
            return this.e;
        }

        public boolean b() {
            return this.f == EnumDirectionLimit.VERTICAL;
        }

        public boolean c() {
            return this.f == EnumDirectionLimit.HORIZONTAL;
        }

        public String toString() {
            return this.e;
        }

        public boolean a(EnumDirection var1) {
            return var1 != null && var1.k() == this;
        }

        public EnumDirectionLimit d() {
            return this.f;
        }

        public String getName() {
            return this.e;
        }

        static {
            d = new HashMap<String, EnumAxis>();
            for (EnumAxis var3 : EnumAxis.values()) {
                d.put(var3.a().toLowerCase(), var3);
            }
        }
    }

    public static enum EnumAxisDirection {
        POSITIVE(1, "Towards positive"),
        NEGATIVE(-1, "Towards negative");

        private final int c;
        private final String d;

        private EnumAxisDirection(int var3, String var4) {
            this.c = var3;
            this.d = var4;
        }

        public int a() {
            return this.c;
        }

        public String toString() {
            return this.d;
        }
    }

    public static enum EnumDirectionLimit {
        HORIZONTAL,
        VERTICAL;


        public boolean a(EnumDirection var1) {
            return var1 != null && var1.k().d() == this;
        }
    }

}

