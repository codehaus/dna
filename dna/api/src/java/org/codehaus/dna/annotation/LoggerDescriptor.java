package org.codehaus.dna.annotation;

/**
 * Annotation to declare service that component supports.
 */
public @interface LoggerDescriptor
{
    /**
     * The name of extension.
     */
    String name() default "";
}
