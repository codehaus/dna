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
 * @version $Revision: 1.4 $ $Date: 2003-08-30 02:03:50 $
 */
public class ConfigurationException
    extends Exception
{
    private final Throwable m_cause;
    private final String m_path;
    private final String m_location;

    public ConfigurationException( final String message )
    {
        this( message, null, null );
    }

    public ConfigurationException( final String message,
                                   final String location )
    {
        this( message, null, location );
    }

    public ConfigurationException( final String message,
                                   final Throwable cause )
    {
        this( message, cause, null );
    }

    public ConfigurationException( final String message,
                                   final Throwable cause,
                                   final String location )
    {
        this( message, cause, null, location );
    }

    public ConfigurationException( final String message,
                                   final Throwable cause,
                                   final String path,
                                   final String location )
    {
        super( message );
        m_cause = cause;
        m_path = path;
        m_location = location;
    }

    public String getPath()
    {
        return m_path;
    }

    public String getLocation()
    {
        return m_location;
    }

    public Throwable getCause()
    {
        return m_cause;
    }

    public String toString()
    {
        if( null != m_location && !"".equals( m_location ) )
        {
            return super.toString() + " @ " + m_location;
        }
        else
        {
            return super.toString();
        }
    }
}
