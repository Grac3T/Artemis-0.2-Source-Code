/*
 * Decompiled with CFR 0.148.
 */
package cc.ghast.artemis.v2.api.check.annotations;

import cc.ghast.artemis.v2.api.packet.tinyprotocol.api.ProtocolVersion;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface Check {
    public int maxVls() default 15;

    public ProtocolVersion[] invalidVersions() default {};
}

