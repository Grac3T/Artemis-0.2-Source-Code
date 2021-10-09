/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.expression.api;

import cc.ghast.lang.expression.CurrentLocationExpression;
import cc.ghast.lang.expression.CurrentRotationExpression;
import cc.ghast.lang.expression.GCDExpression;
import cc.ghast.lang.expression.PingExpression;
import cc.ghast.lang.expression.PreviousLocationExpression;
import cc.ghast.lang.expression.PreviousRotationExpression;
import cc.ghast.lang.expression.api.IExpression;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ExpressionManager {
    private final List<IExpression> expressions = new ArrayList<IExpression>();

    public ExpressionManager() {
        this.expressions.addAll(Arrays.asList(new CurrentLocationExpression(), new CurrentRotationExpression(), new PingExpression(), new PreviousLocationExpression(), new PreviousRotationExpression(), new GCDExpression()));
    }

    public List<IExpression> getExpressions() {
        return this.expressions;
    }
}

