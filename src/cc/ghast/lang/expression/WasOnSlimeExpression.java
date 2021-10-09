/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.expression;

import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.lang.check.AbstractCustomCheck;
import cc.ghast.lang.expression.api.ClassType;
import cc.ghast.lang.expression.api.Expression;
import cc.ghast.lang.expression.api.IExpression;

@Expression(name="WasOnSlime", use={"wasOnSlime"}, type=ClassType.BOOLEAN)
public class WasOnSlimeExpression
implements IExpression {
    @Override
    public Object value(String value, PlayerData data, AbstractCustomCheck customCheck) {
        return data.wasOnSlime();
    }
}
