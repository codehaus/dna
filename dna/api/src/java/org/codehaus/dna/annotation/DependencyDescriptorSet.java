package org.codehaus.dna.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate the set of value required by component.
 */
@Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface DependencyDescriptorSet
{
    /**
     * The set of value required by component.
     */
    DependencyDescriptor[] value();
}
