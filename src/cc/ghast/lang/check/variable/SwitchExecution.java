/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.check.variable;

import cc.ghast.lang.check.VariableExecution;
import cc.ghast.lang.check.variable.ExecutionType;
import cc.ghast.lang.check.variable.VariableUpdateException;
import cc.ghast.lang.expression.api.ClassType;

public class SwitchExecution
extends VariableExecution {
    public SwitchExecution() {
        super(ClassType.BOOLEAN, ExecutionType.SWITCH, null);
    }

    @Override
    public Object update(Object var, Object value) {
        try {
            boolean one = Boolean.parseBoolean(var.toString());
            return !one;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new VariableUpdateException();
        }
    }
}

