/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.check;

import cc.ghast.lang.check.VariableExecution;
import cc.ghast.lang.expression.api.ClassType;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Variable {
    private String name;
    private Object value;
    private final ClassType type;
    private final Map<List<String>, VariableExecution> conditions;

    public Variable(String name, Object value, ClassType type, List<String> conditions, VariableExecution execution) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.conditions = new ConcurrentHashMap<List<String>, VariableExecution>();
        this.conditions.put(conditions, execution);
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }

    public ClassType getType() {
        return this.type;
    }

    public Map<List<String>, VariableExecution> getConditions() {
        return this.conditions;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

