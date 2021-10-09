/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.tree;

import cc.ghast.lang.tree.AbstractBranch;
import cc.ghast.lang.tree.BType;
import cc.ghast.lang.tree.IBranch;
import cc.ghast.lang.tree.ITree;
import java.util.ArrayList;
import java.util.List;

public class Root
implements ITree {
    private final String value;
    private List<AbstractBranch> subBranch;
    private final BType type;

    public Root(String value, BType type, IBranch ... branches) {
        this.value = value;
        this.subBranch = new ArrayList<AbstractBranch>();
        this.type = type;
    }

    @Override
    public ITree previous() {
        return this;
    }

    public void addBranch(AbstractBranch branch) {
        this.subBranch.add(branch);
    }

    public String getValue() {
        return this.value;
    }

    public List<AbstractBranch> getSubBranch() {
        return this.subBranch;
    }

    public BType getType() {
        return this.type;
    }
}

