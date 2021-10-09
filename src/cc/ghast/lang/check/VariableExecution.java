/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.check;

import cc.ghast.lang.check.variable.ExecutionType;
import cc.ghast.lang.expression.api.ClassType;

public class VariableExecution {
    private final ClassType type;
    private final ExecutionType executionType;
    private final String valueToSet;

    public Object update(Object var, Object value) {
        return null;
    }

    public VariableExecution(ClassType type, ExecutionType executionType, String valueToSet) {
        this.type = type;
        this.executionType = executionType;
        this.valueToSet = valueToSet;
    }

    public ClassType getType() {
        return this.type;
    }

    public ExecutionType getExecutionType() {
        return this.executionType;
    }

    public String getValueToSet() {
        return this.valueToSet;
    }
}

