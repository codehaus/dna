/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna;

/**
 *
 * @version $Revision: 1.1 $ $Date: 2003-07-25 11:34:35 $
 */
public class ParameterException
    extends Exception
{
    private final Throwable m_cause;
    private final String m_key;

    public ParameterException( final String message,
                               final String key )
    {
        this( message, null, key );
    }

    public ParameterException( final String message,
                               final Throwable cause,
                               final String key )
    {
        super( message );
        m_cause = cause;
        m_key = key;
    }

    public String getKey()
    {
        return m_key;
    }

    public Throwable getCause()
    {
        return m_cause;
    }
}
