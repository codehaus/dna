package org.codehaus.dna.annotation;

/**
 * Indicates the Compatibility of the extension. 
 */
public enum Compatibility
{
    /** Indicates Extension is required by the component. */
    REQUIRED,

    /** Indicates Extension is supported by the component. */
    SUPPORTS,

    /** Indicates Extension is not incompatible by the component. */
    INCOMPATIBLE
}
