/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.dependency;

import cc.ghast.artemis.v2.dependency.Dependency;
import java.lang.annotation.Annotation;

public abstract class AbstractDependency {
    public final String name = this.getClass().getAnnotation(Dependency.class).name();
    public final String version = this.getClass().getAnnotation(Dependency.class).version();

    public abstract void init();
}

