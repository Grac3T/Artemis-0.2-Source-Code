/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang;

import cc.ghast.lang.condition.api.ConditionManager;
import cc.ghast.lang.expression.api.ExpressionManager;

public class ArtemisLang {
    private ConditionManager conditionManager = new ConditionManager();
    private ExpressionManager expressionManager = new ExpressionManager();

    public ConditionManager getConditionManager() {
        return this.conditionManager;
    }

    public ExpressionManager getExpressionManager() {
        return this.expressionManager;
    }
}

