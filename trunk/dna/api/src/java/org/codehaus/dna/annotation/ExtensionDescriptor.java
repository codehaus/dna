package org.codehaus.dna.annotation;

/**
 * Annotation to declare service that component supports.
 */
public @interface ExtensionDescriptor
{
    /**
     * The name of extension.
     */
    String name();

    /**
     * The compatibility requirements of extension.
     */
    Compatibility compatibility();
}
