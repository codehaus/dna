package org.codehaus.dna.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to declare configuration that the component uses.
 */
@Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ConfigurationDescriptor
{
    /**
     * The location of the schema.
     */
    String location();

    /**
     * The type of the schema.
     */
    String type() default "";
}
