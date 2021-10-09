/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.utils.misc;

public class TimerUtils {
    private long lastMS = 0L;

    public static boolean elapsed(long from, long required) {
        return System.currentTimeMillis() - from > required;
    }

    public static long nowlong() {
        return System.currentTimeMillis();
    }

    public boolean isDelayComplete(long delay) {
        return System.currentTimeMillis() - this.lastMS >= delay;
    }

    public boolean hasReached(long milliseconds) {
        return this.getCurrentMS() - this.lastMS >= milliseconds;
    }

    public void setLastMS() {
        this.lastMS = System.currentTimeMillis();
    }

    public int convertToMS(int perSecond) {
        return 1000 / perSecond;
    }

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public long getLastMS() {
        return this.lastMS;
    }

    public void setLastMS(long lastMS) {
        this.lastMS = lastMS;
    }

    public void reset() {
        this.lastMS = this.getCurrentMS();
    }
}

