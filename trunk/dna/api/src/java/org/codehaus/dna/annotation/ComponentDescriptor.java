package org.codehaus.dna.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate class is a dna component.
 */
@Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ComponentDescriptor
{
}
