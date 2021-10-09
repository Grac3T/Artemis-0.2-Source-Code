/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.condition.api;

import cc.ghast.lang.ArtemisLang;
import cc.ghast.lang.ArtemisLangInstance;
import cc.ghast.lang.condition.api.Condition;
import cc.ghast.lang.condition.api.ConditionManager;
import cc.ghast.lang.condition.api.ICondition;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class ConditionParser {
    public static ICondition parse(String string) {
        if (string.contains("if") && string.contains("(") && string.contains(")")) {
            String condition = string.split("\\(")[1].split("\\)")[0];
            for (ICondition cond : ArtemisLangInstance.INSTANCE.getApi().getConditionManager().getConditions()) {
                if (!Arrays.stream(cond.getClass().getAnnotation(Condition.class).use()).anyMatch(condition::contains)) continue;
                return cond;
            }
        }
        return null;
    }
}

