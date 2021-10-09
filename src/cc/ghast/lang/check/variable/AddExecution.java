/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.check.variable;

import cc.ghast.lang.check.VariableExecution;
import cc.ghast.lang.check.variable.ExecutionType;
import cc.ghast.lang.check.variable.VariableUpdateException;
import cc.ghast.lang.expression.api.ClassType;

public class AddExecution
extends VariableExecution {
    public AddExecution(String val) {
        super(ClassType.NUMERIC, ExecutionType.ADD, val);
    }

    @Override
    public Object update(Object var, Object value) {
        try {
            double one = Double.parseDouble(var.toString());
            double two = Double.parseDouble(value.toString());
            return one + two;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new VariableUpdateException();
        }
    }
}

