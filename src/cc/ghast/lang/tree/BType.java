/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.tree;

public enum BType {
    IF("if"),
    VARIABLE("int", "bool", "str"),
    LOG("log", "verbose", "violate");

    public String[] scan;

    private BType(String ... scan) {
        this.scan = scan;
    }
}

