package org.codehaus.dna.annotation;

/**
 * Annotation to declare service that component supports.
 */
public @interface ServiceDescriptor
{
    /**
     * The type of service.
     */
    Class type();
}
