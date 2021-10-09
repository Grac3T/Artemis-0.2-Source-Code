/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.dependency;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface Dependency {
    public String name();

    public String version();
}

