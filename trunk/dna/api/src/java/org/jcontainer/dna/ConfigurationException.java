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
 * @version $Revision: 1.7 $ $Date: 2003-09-02 03:47:16 $
 */
public class ConfigurationException
    extends Exception
{
    private final Throwable m_cause;
    private final String m_path;
    private final String m_location;

    public ConfigurationException( final String message,
                                   final String path,
                                   final String location )
    {
        this( message, path, location, null );
    }

    public ConfigurationException( final String message,
                                   final Throwable cause )
    {
        this( message, null, null, cause );
    }

    public ConfigurationException( final String message,
                                   final String path,
                                   final String location,
                                   final Throwable cause )
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
        final StringBuffer sb = new StringBuffer();

        if( null != m_path && !"".equals( m_path ) )
        {
            sb.append( " - " );
            sb.append( m_path );
        }

        if( null != m_location && !"".equals( m_location ) )
        {
            sb.append( " @ " );
            sb.append( m_location );
        }

        if( 0 != sb.length() )
        {
            return super.toString() + sb;
        }
        else
        {
            return super.toString();
        }
    }
}
