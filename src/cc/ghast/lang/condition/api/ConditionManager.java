/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.condition.api;

import cc.ghast.lang.condition.ArithmeticCondition;
import cc.ghast.lang.condition.InLiquidCondition;
import cc.ghast.lang.condition.InstanceOfCondition;
import cc.ghast.lang.condition.OnGroundCondition;
import cc.ghast.lang.condition.api.Condition;
import cc.ghast.lang.condition.api.ICondition;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

public class ConditionManager {
    private List<ICondition> conditions = Arrays.asList(new ArithmeticCondition(), new InLiquidCondition(), new OnGroundCondition(), new InstanceOfCondition());

    public ConditionManager() {
        for (String s : ArithmeticCondition.class.getAnnotation(Condition.class).use()) {
            System.out.println("Arithmetic condition: " + s);
        }
    }

    public List<ICondition> getConditions() {
        return this.conditions;
    }
}

