package dev.callmeecho.cabinetapi.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModCondition {
    String value() default "";
    String versionPredicate() default "";
    boolean negated() default false;
}
