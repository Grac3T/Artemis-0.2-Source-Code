/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.tree;

import cc.ghast.lang.tree.BType;

public interface IBranch {
    public IBranch[] subBranch();

    public String value();

    public BType type();

    public int num();
}

