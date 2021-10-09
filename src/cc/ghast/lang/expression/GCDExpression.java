/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.expression;

import cc.ghast.artemis.v2.api.check.exceptions.CheckLoadError;
import cc.ghast.artemis.v2.api.data.PlayerData;
import cc.ghast.artemis.v2.utils.MathUtil;
import cc.ghast.lang.ArtemisLang;
import cc.ghast.lang.ArtemisLangInstance;
import cc.ghast.lang.check.AbstractCustomCheck;
import cc.ghast.lang.check.CachedVariable;
import cc.ghast.lang.check.VariableExecution;
import cc.ghast.lang.condition.api.AbstractCachedCondition;
import cc.ghast.lang.condition.api.ArithmeticParsingException;
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
import java.util.function.Supplier;
import java.util.stream.Stream;

@Expression(name="GCD", use={"gcd"}, type=ClassType.NUMERIC)
public class GCDExpression
implements IExpression {
    @Override
    public Object value(String value, PlayerData data, AbstractCustomCheck check) {
        if (value.contains("[") && value.contains("]")) {
            String oneParsed;
            IExpression item;
            String twoParsed;
            String var = value.split("\\[")[1].split("]")[0];
            String one = var.split(",")[0];
            String two = var.split(",")[1];
            if (!MathUtil.isNumeric(one) && check.getVarConditions().entrySet().stream().anyMatch(v -> ((CachedVariable)v.getKey()).getName().equalsIgnoreCase(one))) {
                oneParsed = ((CachedVariable)check.getVarConditions().entrySet().stream().filter(v -> ((CachedVariable)v.getKey()).getName().equalsIgnoreCase(one)).findFirst().orElseThrow(ArithmeticParsingException::new).getKey()).getValue().toString();
            } else if (!MathUtil.isNumeric(one)) {
                item = ArtemisLangInstance.INSTANCE.getApi().getExpressionManager().getExpressions().stream().filter(som -> Arrays.stream(som.getClass().getAnnotation(Expression.class).use()).anyMatch(c -> c.equalsIgnoreCase(one)) && som.getClass().getAnnotation(Expression.class).type().equals((Object)ClassType.NUMERIC)).findFirst().orElse(null);
                if (item == null) {
                    throw new ArithmeticParsingException();
                }
                oneParsed = item.value(one, data, check).toString();
            } else {
                oneParsed = one;
            }
            if (!MathUtil.isNumeric(two) && check.getVarConditions().entrySet().stream().anyMatch(v -> ((CachedVariable)v.getKey()).getName().equalsIgnoreCase(two))) {
                twoParsed = ((CachedVariable)check.getVarConditions().entrySet().stream().filter(v -> ((CachedVariable)v.getKey()).getName().equalsIgnoreCase(two)).findFirst().orElseThrow(ArithmeticParsingException::new).getKey()).getValue().toString();
            } else if (!MathUtil.isNumeric(two)) {
                item = ArtemisLangInstance.INSTANCE.getApi().getExpressionManager().getExpressions().stream().filter(som -> Arrays.stream(som.getClass().getAnnotation(Expression.class).use()).anyMatch(c -> c.equalsIgnoreCase(two)) && som.getClass().getAnnotation(Expression.class).type().equals((Object)ClassType.NUMERIC)).findFirst().orElse(null);
                if (item == null) {
                    throw new ArithmeticParsingException();
                }
                twoParsed = item.value(two, data, check).toString();
            } else {
                twoParsed = two;
            }
            long v2 = Math.round(Double.parseDouble(oneParsed));
            long v22 = Math.round(Double.parseDouble(twoParsed));
            return (double)MathUtil.gcd(v2, v22);
        }
        throw new CheckLoadError();
    }
}

