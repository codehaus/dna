/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna;

/**
 * The MissingResourceException is used to signal a problem
 * retrieving a resource from the ResourceLocator object.
 *
 * @version $Revision: 1.1 $ $Date: 2004-04-18 20:13:47 $
 */
public class MissingResourceException
    extends Exception
{
    /**
     * The exception that caused this exception if any.
     */
    private final Throwable m_cause;

    /**
     * The resource key that caused the problem.
     */
    private final String m_key;

    /**
     * Create a MissingResourceException with specified message
     * and key.
     *
     * @param message the message
     * @param key the key
     */
    public MissingResourceException( final String message,
                                     final String key )
    {
        this( message, key, null );
    }

    /**
     * Create a MissingResourceException with specified
     * message, key and cause.
     *
     * @param message the message
     * @param key the key
     * @param cause the cause
     */
    public MissingResourceException( final String message,
                                     final String key,
                                     final Throwable cause )
    {
        super( message );
        m_key = key;
        m_cause = cause;
    }

    /**
     * Return the resource key that caused the problem.
     *
     * @return the resource key that caused the problem.
     */
    public String getKey()
    {
        return m_key;
    }

    /**
     * Return the exception that caused this exception if any.
     *
     * @return the exception that caused this exception if any.
     */
    public Throwable getCause()
    {
        return m_cause;
    }
}
