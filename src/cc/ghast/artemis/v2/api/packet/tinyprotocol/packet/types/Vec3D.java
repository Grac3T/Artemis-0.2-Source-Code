/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.packets.reflection.FieldAccessor;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.BaseBlockPosition;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.packet.types.MathHelper;

public class Vec3D
extends NMSObject {
    private static FieldAccessor<Double> fieldX = Vec3D.fetchField("Vec3D", Double.TYPE, 0);
    private static FieldAccessor<Double> fieldY = Vec3D.fetchField("Vec3D", Double.TYPE, 1);
    private static FieldAccessor<Double> fieldZ = Vec3D.fetchField("Vec3D", Double.TYPE, 2);
    public final double a;
    public final double b;
    public final double c;

    public Vec3D(Object obj) {
        this.setObject(obj);
        this.a = this.fetch(fieldX);
        this.b = this.fetch(fieldY);
        this.c = this.fetch(fieldZ);
    }

    public Vec3D(double var1, double var3, double var5) {
        if (var1 == -0.0) {
            var1 = 0.0;
        }
        if (var3 == -0.0) {
            var3 = 0.0;
        }
        if (var5 == -0.0) {
            var5 = 0.0;
        }
        this.a = var1;
        this.b = var3;
        this.c = var5;
    }

    public Vec3D(BaseBlockPosition var1) {
        this(var1.getX(), var1.getY(), var1.getZ());
    }

    public Vec3D a() {
        double var1 = MathHelper.sqrt(this.a * this.a + this.b * this.b + this.c * this.c);
        return var1 < 1.0E-4 ? new Vec3D(0.0, 0.0, 0.0) : new Vec3D(this.a / var1, this.b / var1, this.c / var1);
    }

    public double b(Vec3D var1) {
        return this.a * var1.a + this.b * var1.b + this.c * var1.c;
    }

    public Vec3D d(Vec3D var1) {
        return this.a(var1.a, var1.b, var1.c);
    }

    public Vec3D a(double var1, double var3, double var5) {
        return this.add(-var1, -var3, -var5);
    }

    public Vec3D e(Vec3D var1) {
        return this.add(var1.a, var1.b, var1.c);
    }

    public Vec3D add(double var1, double var3, double var5) {
        return new Vec3D(this.a + var1, this.b + var3, this.c + var5);
    }

    public double distanceSquared(Vec3D var1) {
        double var2 = var1.a - this.a;
        double var4 = var1.b - this.b;
        double var6 = var1.c - this.c;
        return var2 * var2 + var4 * var4 + var6 * var6;
    }

    public double b() {
        return MathHelper.sqrt(this.a * this.a + this.b * this.b + this.c * this.c);
    }

    public Vec3D a(Vec3D var1, double var2) {
        double var4 = var1.a - this.a;
        double var6 = var1.b - this.b;
        double var8 = var1.c - this.c;
        if (var4 * var4 < 1.0000000116860974E-7) {
            return null;
        }
        double var10 = (var2 - this.a) / var4;
        return var10 >= 0.0 && var10 <= 1.0 ? new Vec3D(this.a + var4 * var10, this.b + var6 * var10, this.c + var8 * var10) : null;
    }

    public Vec3D b(Vec3D var1, double var2) {
        double var4 = var1.a - this.a;
        double var6 = var1.b - this.b;
        double var8 = var1.c - this.c;
        if (var6 * var6 < 1.0000000116860974E-7) {
            return null;
        }
        double var10 = (var2 - this.b) / var6;
        return var10 >= 0.0 && var10 <= 1.0 ? new Vec3D(this.a + var4 * var10, this.b + var6 * var10, this.c + var8 * var10) : null;
    }

    public Vec3D c(Vec3D var1, double var2) {
        double var4 = var1.a - this.a;
        double var6 = var1.b - this.b;
        double var8 = var1.c - this.c;
        if (var8 * var8 < 1.0000000116860974E-7) {
            return null;
        }
        double var10 = (var2 - this.c) / var8;
        return var10 >= 0.0 && var10 <= 1.0 ? new Vec3D(this.a + var4 * var10, this.b + var6 * var10, this.c + var8 * var10) : null;
    }

    public String toString() {
        return "(" + this.a + ", " + this.b + ", " + this.c + ")";
    }

    public Vec3D a(float var1) {
        float var2 = MathHelper.cos(var1);
        float var3 = MathHelper.sin(var1);
        double var4 = this.a;
        double var6 = this.b * (double)var2 + this.c * (double)var3;
        double var8 = this.c * (double)var2 - this.b * (double)var3;
        return new Vec3D(var4, var6, var8);
    }

    public Vec3D b(float var1) {
        float var2 = MathHelper.cos(var1);
        float var3 = MathHelper.sin(var1);
        double var4 = this.a * (double)var2 + this.c * (double)var3;
        double var6 = this.b;
        double var8 = this.c * (double)var2 - this.a * (double)var3;
        return new Vec3D(var4, var6, var8);
    }
}

