/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.data;

import cc.ghast.artemis.v2.api.check.AbstractCheck;

public class Violation {
    private final AbstractCheck check;
    private final long timestamp;
    private final int count;

    public Violation(AbstractCheck check, long timestamp, int count) {
        this.check = check;
        this.timestamp = timestamp;
        this.count = count;
    }

    public AbstractCheck getCheck() {
        return this.check;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public int getCount() {
        return this.count;
    }
}

