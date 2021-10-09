/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.lang.condition.api;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface Condition {
    public String name();

    public String[] use();
}

