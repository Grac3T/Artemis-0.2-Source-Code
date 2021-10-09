/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.check;

public class CachedVariable {
    private final String name;
    private Object value;

    public CachedVariable(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

