/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.data;

public class Verbose {
    private final long timestamp;
    private final int count;

    public Verbose(long timestamp, int count) {
        this.timestamp = timestamp;
        this.count = count;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public int getCount() {
        return this.count;
    }
}

