/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna;

/**
 * The ConfigurationException is used to signal a problem
 * with the configuration object. The configuration object
 * may have malformed data (ie expected an integer but got
 * a string), have missing data (ie no attribute with specified
 * name) or may fail to be valid via some other mechanism.
 *
 * @version $Revision: 1.10 $ $Date: 2003-09-23 02:15:56 $
 */
public class ConfigurationException
    extends Exception
{
    /**
     * The exception that caused this exception if any.
     */
    private final Throwable m_cause;

    /**
     * The xpath to the configuration element that
     * caused the exception. This may be null or empty
     * if not relevent or not known.
     */
    private final String m_path;

    /**
     * A string describing the location of the configuration
     * element that caused the exception. This may be null
     * or empty if not relevent or not known. The location is
     * usally formatted according to <tt>uri[:line number[:column number]]</tt>.
     * Note that the line and column numbers may not be present.
     */
    private final String m_location;

    /**
     * Create configuration exception with specified message,
     * path and location.
     *
     * @param message the message
     * @param path the path
     * @param location the location
     */
    public ConfigurationException( final String message,
                                   final String path,
                                   final String location )
    {
        this( message, path, location, null );
    }

    /**
     * Create configuration exception with specified
     * message and cause.
     *
     * @param message the message
     * @param cause the cause
     */
    public ConfigurationException( final String message,
                                   final Throwable cause )
    {
        this( message, null, null, cause );
    }

    /**
     * Create configuration exception with specified message,
     * path, location and cause.
     *
     * @param message the message
     * @param path the path
     * @param location the location
     * @param cause the cause
     */
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

    /**
     * The xpath to the configuration element that
     * caused the exception. This may be null or empty
     * if not relevent or not known.
     *
     * @return the xpath to element that caused exception
     */
    public String getPath()
    {
        return m_path;
    }

    /**
     * Return a string describing the location of the configuration
     * element that caused the exception. This may be null
     * or empty if not relevent or not known. The location is usally
     * formatted according to <tt>uri[:line number[:column number]]</tt>.
     * Note that the line and column numbers may not be present.
     *
     * @return the location where exception occured
     */
    public String getLocation()
    {
        return m_location;
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

    /**
     * Return the string representation of exception.
     *
     * @return the string representation of exception.
     */
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
