/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.check.variable;

public class VariableUpdateException
extends RuntimeException {
    public VariableUpdateException() {
        super("Variable failed to load... please check your code");
    }
}

