/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.check.variable;

import cc.ghast.lang.check.VariableExecution;
import cc.ghast.lang.check.variable.ExecutionType;
import cc.ghast.lang.check.variable.VariableUpdateException;
import cc.ghast.lang.expression.api.ClassType;

public class IncreaseExecution
extends VariableExecution {
    public IncreaseExecution() {
        super(ClassType.NUMERIC, ExecutionType.INCREASE, null);
    }

    @Override
    public Object update(Object var, Object value) {
        try {
            double one;
            double d = one = Double.parseDouble(var.toString());
            one = d + 1.0;
            return d;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new VariableUpdateException();
        }
    }
}

