package org.codehaus.dna.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate the value that the component exports.
 */
@Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ServiceDescriptorSet
{
    /**
     * The set of value exported by component.
     */
    ServiceDescriptor[] value();
}
