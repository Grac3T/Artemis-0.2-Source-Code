/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.expression.api;

import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.lang.check.AbstractCustomCheck;

public interface IExpression {
    public Object value(String var1, PlayerData var2, AbstractCustomCheck var3);
}

