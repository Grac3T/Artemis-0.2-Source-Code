/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.utils.entity;

import java.io.Serializable;

public class MovingStats
implements Serializable {
    private final double[] elements;
    private int currentElement;
    private int windowCount;
    private double variance;

    public MovingStats(int size) {
        this.elements = new double[size];
        this.variance = (double)size * 2.5;
        int len = this.elements.length;
        for (int i = 0; i < len; ++i) {
            this.elements[i] = (double)size * 2.5 / (double)size;
        }
    }

    public void add(double sum) {
        this.variance -= this.elements[this.currentElement];
        this.variance += (sum /= (double)this.elements.length);
        this.elements[this.currentElement] = sum;
        this.currentElement = (this.currentElement + 1) % this.elements.length;
    }

    public double getStdDev(double required) {
        double stdDev = Math.sqrt(this.variance);
        if (stdDev < required) {
            if (++this.windowCount > this.elements.length) {
                return stdDev;
            }
        } else {
            if (this.windowCount > 0) {
                this.windowCount = 0;
            }
            return required;
        }
        return Double.NaN;
    }
}

