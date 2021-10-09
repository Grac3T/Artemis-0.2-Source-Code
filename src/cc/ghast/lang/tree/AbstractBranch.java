/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.tree;

import cc.ghast.lang.tree.BType;
import cc.ghast.lang.tree.IBranch;
import cc.ghast.lang.tree.ITree;
import cc.ghast.lang.tree.Root;
import java.util.ArrayList;
import java.util.List;

public class AbstractBranch
implements IBranch,
ITree {
    private final String value;
    private final List<IBranch> subBranch;
    private final Root root;
    private final BType type;
    private final ITree previousBranch;

    public AbstractBranch(String value, Root root, BType type, ITree previousBranch, IBranch ... lastBranch) {
        this.value = value;
        this.subBranch = new ArrayList<IBranch>();
        this.root = root;
        this.type = type;
        this.previousBranch = previousBranch;
    }

    public void addBranch(IBranch branch) {
        this.subBranch.add(branch);
    }

    @Override
    public int num() {
        return this.root.getSubBranch().size();
    }

    @Override
    public String value() {
        return this.value;
    }

    @Override
    public BType type() {
        return this.type;
    }

    @Override
    public IBranch[] subBranch() {
        return null;
    }

    @Override
    public ITree previous() {
        return this.previousBranch;
    }

    public String getValue() {
        return this.value;
    }

    public List<IBranch> getSubBranch() {
        return this.subBranch;
    }

    public Root getRoot() {
        return this.root;
    }

    public BType getType() {
        return this.type;
    }

    public ITree getPreviousBranch() {
        return this.previousBranch;
    }
}

