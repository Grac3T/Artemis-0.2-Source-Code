/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.condition;

import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.NMSObject;
import cc.ghast.artemis.v2.utils.MathUtil;
import cc.ghast.lang.ArtemisLang;
import cc.ghast.lang.ArtemisLangInstance;
import cc.ghast.lang.check.AbstractCustomCheck;
import cc.ghast.lang.check.CachedVariable;
import cc.ghast.lang.check.VariableExecution;
import cc.ghast.lang.condition.api.AbstractCachedCondition;
import cc.ghast.lang.condition.api.ArithmeticParsingException;
import cc.ghast.lang.condition.api.Condition;
import cc.ghast.lang.condition.api.ICondition;
import cc.ghast.lang.expression.api.ClassType;
import cc.ghast.lang.expression.api.Expression;
import cc.ghast.lang.expression.api.ExpressionManager;
import cc.ghast.lang.expression.api.IExpression;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Condition(name="Arithmetics", use={"==", "!=", "<", ">", "<=", ">="})
public class ArithmeticCondition
implements ICondition {
    @Override
    public boolean value(String value, PlayerData data, AbstractCustomCheck check, NMSObject packet) {
        try {
            value = value.split("\\(")[1].split("\\)")[0];
            String one = value.split("[=!><]")[0].replace(" ", "");
            String two = value.split("[=!><]")[2].replace(" ", "");
            Object v1 = this.getValue(one, check, data);
            Object v2 = this.getValue(two, check, data);
            if (value.contains("==")) {
                return v1.equals(v2);
            }
            if (value.contains("!=")) {
                return !v1.equals(v2);
            }
            if (value.contains("<") && !value.contains("=")) {
                return (Double)v1 < (Double)v2;
            }
            if (value.contains(">") && !value.contains("=")) {
                return (Double)v1 > (Double)v2;
            }
            if (value.contains("<=")) {
                return (Double)v1 <= (Double)v2;
            }
            if (value.contains(">=")) {
                return (Double)v1 >= (Double)v2;
            }
            throw new ArithmeticParsingException();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ArithmeticParsingException();
        }
    }

    private Object getValue(String var, AbstractCustomCheck check, PlayerData data) {
        if (MathUtil.isNumeric(var)) {
            return Double.parseDouble(var);
        }
        if (MathUtil.isBoolean(var)) {
            return Boolean.parseBoolean(var);
        }
        Map.Entry entry = check.getVarConditions().entrySet().stream().filter(v -> ((CachedVariable)v.getKey()).getName().equalsIgnoreCase(var)).findFirst().orElse(null);
        if (entry != null) {
            Object entryKey = ((CachedVariable)entry.getKey()).getValue();
            return this.getValue(entryKey.toString(), check, data);
        }
        IExpression item = ArtemisLangInstance.INSTANCE.getApi().getExpressionManager().getExpressions().stream().filter(som -> Arrays.stream(som.getClass().getAnnotation(Expression.class).use()).anyMatch(c -> c.equalsIgnoreCase(var)) && som.getClass().getAnnotation(Expression.class).type().equals((Object)ClassType.NUMERIC)).findFirst().orElse(null);
        if (item == null) {
            throw new ArithmeticParsingException();
        }
        return this.getValue(item.value(var, data, check).toString(), check, data);
    }
}

