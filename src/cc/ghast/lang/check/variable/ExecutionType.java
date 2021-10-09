/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.check.variable;

public enum ExecutionType {
    ADD("+="),
    DECREASE("--"),
    INCREASE("++"),
    REMOVE("-="),
    SET("$="),
    SWITCH("</>");

    private String id;

    private ExecutionType(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}

