/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.impl;

import org.jcontainer.dna.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.Level;

/**
 *
 * @version $Revision: 1.2 $ $Date: 2003-07-27 02:10:01 $
 */
public class Log4jLogger
    implements Logger
{
    /**
     * Constant for name of class to use when recording caller
     * of log method.
     */
    private static final String FQCN = Log4jLogger.class.getName();

    private final org.apache.log4j.Logger m_logger;

    public Log4jLogger( final org.apache.log4j.Logger logger )
    {
        if( null == logger )
        {
            throw new NullPointerException( "logger" );
        }
        m_logger = logger;
    }

    public void trace( final String message )
    {
        m_logger.log( FQCN, Level.DEBUG, message, null );
    }

    public void trace( final String message,
                       final Throwable throwable )
    {
        m_logger.log( FQCN, Level.DEBUG, message, throwable );
    }

    public boolean isTraceEnabled()
    {
        return m_logger.isDebugEnabled();
    }

    public void debug( final String message )
    {
        m_logger.log( FQCN, Level.DEBUG, message, null );
    }

    public void debug( final String message,
                       final Throwable throwable )
    {
        m_logger.log( FQCN, Level.DEBUG, message, throwable );
    }

    public boolean isDebugEnabled()
    {
        return m_logger.isDebugEnabled();
    }

    public void info( final String message )
    {
        m_logger.log( FQCN, Level.INFO, message, null );
    }

    public void info( final String message,
                      final Throwable throwable )
    {
        m_logger.log( FQCN, Level.INFO, message, throwable );
    }

    public boolean isInfoEnabled()
    {
        return m_logger.isInfoEnabled();
    }

    public void warn( final String message )
    {
        m_logger.log( FQCN, Level.WARN, message, null );
    }

    public void warn( final String message,
                      final Throwable throwable )
    {
        m_logger.log( FQCN, Level.WARN, message, throwable );
    }

    public boolean isWarnEnabled()
    {
        return m_logger.isEnabledFor( Priority.WARN );
    }

    public void error( final String message )
    {
        m_logger.log( FQCN, Level.ERROR, message, null );
    }

    public void error( final String message,
                       final Throwable throwable )
    {
        m_logger.log( FQCN, Level.ERROR, message, throwable );
    }

    public boolean isErrorEnabled()
    {
        return m_logger.isEnabledFor( Priority.ERROR );
    }

    public Logger getChildLogger( final String name )
    {

        return new Log4jLogger( org.apache.log4j.Logger.
                                getLogger( m_logger.getName() + "." + name ) );
    }
}
