/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.impl;

import org.jcontainer.dna.Logger;

/**
 *
 * @version $Revision: 1.1 $ $Date: 2003-07-27 02:01:05 $
 */
public class LogkitLogger
    implements Logger
{
    private final org.apache.log.Logger m_logger;

    public LogkitLogger( final org.apache.log.Logger logger )
    {
        if( null == logger )
        {
            throw new NullPointerException( "logger" );
        }
        m_logger = logger;
    }

    public void trace( final String message )
    {
        m_logger.debug( message );
    }

    public void trace( final String message,
                       final Throwable throwable )
    {
        m_logger.debug( message, throwable );
    }

    public boolean isTraceEnabled()
    {
        return m_logger.isDebugEnabled();
    }

    public void debug( final String message )
    {
        m_logger.debug( message );
    }

    public void debug( final String message,
                       final Throwable throwable )
    {
        m_logger.debug( message, throwable );
    }

    public boolean isDebugEnabled()
    {
        return m_logger.isDebugEnabled();
    }

    public void info( final String message )
    {
        m_logger.info( message );
    }

    public void info( final String message,
                      final Throwable throwable )
    {
        m_logger.info( message, throwable );
    }

    public boolean isInfoEnabled()
    {
        return m_logger.isInfoEnabled();
    }

    public void warn( final String message )
    {
        m_logger.warn( message );
    }

    public void warn( final String message,
                      final Throwable throwable )
    {
        m_logger.warn( message, throwable );
    }

    public boolean isWarnEnabled()
    {
        return m_logger.isWarnEnabled();
    }

    public void error( final String message )
    {
        m_logger.error( message );
    }

    public void error( final String message,
                       final Throwable throwable )
    {
        m_logger.error( message, throwable );
    }

    public boolean isErrorEnabled()
    {
        return m_logger.isErrorEnabled();
    }

    public Logger getChildLogger( final String name )
    {
        return new LogkitLogger( m_logger.getChildLogger( name ) );
    }
}
