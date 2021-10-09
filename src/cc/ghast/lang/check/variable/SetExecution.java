/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.check.variable;

import cc.ghast.lang.check.VariableExecution;
import cc.ghast.lang.check.variable.ExecutionType;
import cc.ghast.lang.check.variable.VariableUpdateException;
import cc.ghast.lang.expression.api.ClassType;

public class SetExecution
extends VariableExecution {
    public SetExecution(String val) {
        super(ClassType.OBJECT, ExecutionType.SET, val);
    }

    @Override
    public Object update(Object var, Object value) {
        try {
            return value;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new VariableUpdateException();
        }
    }
}

