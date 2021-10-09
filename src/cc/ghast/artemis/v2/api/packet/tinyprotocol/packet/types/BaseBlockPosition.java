/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.FieldAccessor;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.MathHelper;

public class BaseBlockPosition
extends NMSObject {
    public static final BaseBlockPosition ZERO = new BaseBlockPosition(0, 0, 0);
    private static FieldAccessor<Integer> fieldX = BaseBlockPosition.fetchField("BaseBlockPosition", Integer.TYPE, 0);
    private static FieldAccessor<Integer> fieldY = BaseBlockPosition.fetchField("BaseBlockPosition", Integer.TYPE, 1);
    private static FieldAccessor<Integer> fieldZ = BaseBlockPosition.fetchField("BaseBlockPosition", Integer.TYPE, 2);
    private int a;
    private int c;
    private int d;

    public BaseBlockPosition(Object obj) {
        this.setObject(obj);
        this.a = this.fetch(fieldX);
        this.c = this.fetch(fieldY);
        this.d = this.fetch(fieldZ);
    }

    public BaseBlockPosition(int var1, int var2, int var3) {
        this.a = var1;
        this.c = var2;
        this.d = var3;
    }

    public BaseBlockPosition(double var1, double var3, double var5) {
        this(MathHelper.floor(var1), MathHelper.floor(var3), MathHelper.floor(var5));
    }

    public boolean equals(Object var1) {
        if (this == var1) {
            return true;
        }
        if (!(var1 instanceof BaseBlockPosition)) {
            return false;
        }
        BaseBlockPosition var2 = (BaseBlockPosition)var1;
        if (this.getX() != var2.getX()) {
            return false;
        }
        if (this.getY() != var2.getY()) {
            return false;
        }
        return this.getZ() == var2.getZ();
    }

    public int hashCode() {
        return (this.getY() + this.getZ() * 31) * 31 + this.getX();
    }

    public int g(BaseBlockPosition var1) {
        if (this.getY() == var1.getY()) {
            return this.getZ() == var1.getZ() ? this.getX() - var1.getX() : this.getZ() - var1.getZ();
        }
        return this.getY() - var1.getY();
    }

    public int getX() {
        return this.a;
    }

    public int getY() {
        return this.c;
    }

    public int getZ() {
        return this.d;
    }

    public BaseBlockPosition d(BaseBlockPosition var1) {
        return new BaseBlockPosition(this.getY() * var1.getZ() - this.getZ() * var1.getY(), this.getZ() * var1.getX() - this.getX() * var1.getZ(), this.getX() * var1.getY() - this.getY() * var1.getX());
    }

    public double c(double var1, double var3, double var5) {
        double var7 = (double)this.getX() - var1;
        double var9 = (double)this.getY() - var3;
        double var11 = (double)this.getZ() - var5;
        return var7 * var7 + var9 * var9 + var11 * var11;
    }

    public double d(double var1, double var3, double var5) {
        double var7 = (double)this.getX() + 0.5 - var1;
        double var9 = (double)this.getY() + 0.5 - var3;
        double var11 = (double)this.getZ() + 0.5 - var5;
        return var7 * var7 + var9 * var9 + var11 * var11;
    }

    public double i(BaseBlockPosition var1) {
        return this.c(var1.getX(), var1.getY(), var1.getZ());
    }
}

