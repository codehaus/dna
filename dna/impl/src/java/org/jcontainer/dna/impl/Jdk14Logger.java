/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.impl;

import org.jcontainer.dna.Logger;
import java.util.logging.Level;

/**
 *
 * @version $Revision: 1.1 $ $Date: 2003-07-26 04:06:14 $
 */
public class Jdk14Logger
    implements Logger
{
    private final java.util.logging.Logger m_logger;

    public Jdk14Logger( final java.util.logging.Logger logger )
    {
        if( null == logger )
        {
            throw new NullPointerException( "logger" );
        }
        m_logger = logger;
    }

    public void trace( final String message )
    {
        m_logger.log( Level.FINEST, message );
    }

    public void trace( final String message,
                       final Throwable throwable )
    {
        m_logger.log( Level.FINEST, message, throwable );
    }

    public boolean isTraceEnabled()
    {
        return m_logger.isLoggable( Level.FINEST );
    }

    public void debug( final String message )
    {
        m_logger.log( Level.FINE, message );
    }

    public void debug( final String message,
                       final Throwable throwable )
    {
        m_logger.log( Level.FINE, message, throwable );
    }

    public boolean isDebugEnabled()
    {
        return m_logger.isLoggable( Level.FINE );
    }

    public void info( final String message )
    {
        m_logger.log( Level.INFO, message );
    }

    public void info( final String message,
                      final Throwable throwable )
    {
        m_logger.log( Level.INFO, message, throwable );
    }

    public boolean isInfoEnabled()
    {
        return m_logger.isLoggable( Level.INFO );
    }

    public void warn( final String message )
    {
        m_logger.log( Level.WARNING, message );
    }

    public void warn( final String message,
                      final Throwable throwable )
    {
        m_logger.log( Level.WARNING, message, throwable );
    }

    public boolean isWarnEnabled()
    {
        return m_logger.isLoggable( Level.WARNING );
    }

    public void error( final String message )
    {
        m_logger.log( Level.SEVERE, message );
    }

    public void error( final String message,
                       final Throwable throwable )
    {
        m_logger.log( Level.SEVERE, message, throwable );
    }

    public boolean isErrorEnabled()
    {
        return m_logger.isLoggable( Level.SEVERE );
    }

    public Logger getChildLogger( final String name )
    {
        final String childName = m_logger.getName() + "." + name;
        return new Jdk14Logger( java.util.logging.
                                Logger.getLogger( childName ) );
    }
}
