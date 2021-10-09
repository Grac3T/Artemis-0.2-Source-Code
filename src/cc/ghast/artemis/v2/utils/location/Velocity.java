/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.utils.location;

public class Velocity {
    private double x;
    private double y;
    private double z;
    private double horizontal;
    private double vertical;

    public Velocity(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.horizontal = Math.sqrt(x * x + z * z);
        this.vertical = Math.abs(y);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public double getHorizontal() {
        return this.horizontal;
    }

    public double getVertical() {
        return this.vertical;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setHorizontal(double horizontal) {
        this.horizontal = horizontal;
    }

    public void setVertical(double vertical) {
        this.vertical = vertical;
    }
}

