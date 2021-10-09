/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.expression.api;

public enum ClassType {
    OBJECT("obj"),
    NUMERIC("int"),
    STRING("str"),
    BOOLEAN("bool");

    private String id;

    private ClassType(String identifiers) {
        this.id = identifiers;
    }

    public String getId() {
        return this.id;
    }
}

