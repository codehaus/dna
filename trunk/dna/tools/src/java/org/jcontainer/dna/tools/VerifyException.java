/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.tools;

/**
 * Exception to indicate error verifying a Component.
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.1 $ $Date: 2003-10-06 14:25:56 $
 */
public final class VerifyException
    extends Exception
{
    private final Throwable m_cause;

    /**
     * Construct a new <code>VerifyException</code> instance.
     *
     * @param message The detail message for this exception.
     */
    public VerifyException( final String message )
    {
        this( message, null );
    }

    /**
     * Construct a new <code>VerifyException</code> instance.
     *
     * @param message The detail message for this exception.
     * @param cause the root cause of the exception
     */
    public VerifyException( final String message, final Throwable cause )
    {
        super( message );
        m_cause = cause;
    }

    /**
     * Retrieve cause of exception.
     *
     * @return the exception that caused this exception.
     */
    public Throwable getCause()
    {
        return m_cause;
    }
}
