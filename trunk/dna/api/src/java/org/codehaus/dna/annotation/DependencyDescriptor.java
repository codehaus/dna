package org.codehaus.dna.annotation;

/**
 * Annotation indicating a component dependency.
 */
public @interface DependencyDescriptor
{
    /**
     * The dependency key.
     */
    String key() default "";
    /**
     * The dependency qualifier.
     */
    String qualifier() default "";
    /**
     * The type of the dependency.
     */
    Class type();
    /**
     * The flag indicating whether dependency is optional.
     */
    boolean optional() default false;
}
