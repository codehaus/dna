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
 * @version $Revision: 1.2 $ $Date: 2003-08-07 09:11:08 $
 */
public class ConsoleLogger
    implements Logger
{
    public static final int LEVEL_ALL = 0;
    public static final int LEVEL_TRACE = 1;
    public static final int LEVEL_DEBUG = 2;
    public static final int LEVEL_INFO = 3;
    public static final int LEVEL_WARN = 4;
    public static final int LEVEL_ERROR = 5;
    public static final int LEVEL_NONE = 6;

    private static final String LEVEL_TRACE_STR = "TRACE";
    private static final String LEVEL_DEBUG_STR = "DEBUG";
    private static final String LEVEL_INFO_STR = "INFO";
    private static final String LEVEL_WARN_STR = "WARN";
    private static final String LEVEL_ERROR_STR = "ERROR";

    private final int m_level;

    public ConsoleLogger()
    {
        this( LEVEL_ALL );
    }

    public ConsoleLogger( final int level )
    {
        m_level = level;
    }

    public void trace( final String message )
    {
        trace( message, null );
    }

    public void trace( final String message,
                       final Throwable throwable )
    {
        output( LEVEL_TRACE, LEVEL_TRACE_STR, message, throwable );
    }

    public boolean isTraceEnabled()
    {
        return m_level <= LEVEL_TRACE;
    }

    public void debug( final String message )
    {
        debug( message, null );
    }

    public void debug( final String message,
                       final Throwable throwable )
    {
        output( LEVEL_DEBUG, LEVEL_DEBUG_STR, message, throwable );
    }

    public boolean isDebugEnabled()
    {
        return m_level <= LEVEL_DEBUG;
    }

    public void info( final String message )
    {
        info( message, null );
    }

    public void info( final String message,
                      final Throwable throwable )
    {
        output( LEVEL_INFO, LEVEL_INFO_STR, message, throwable );
    }

    public boolean isInfoEnabled()
    {
        return m_level <= LEVEL_INFO;
    }

    public void warn( final String message )
    {
        warn( message, null );
    }

    public void warn( final String message,
                      final Throwable throwable )
    {
        output( LEVEL_WARN, LEVEL_WARN_STR, message, throwable );
    }

    public boolean isWarnEnabled()
    {
        return m_level <= LEVEL_WARN;
    }

    public void error( final String message )
    {
        error( message, null );
    }

    public void error( final String message,
                       final Throwable throwable )
    {
        output( LEVEL_ERROR, LEVEL_ERROR_STR, message, throwable );
    }

    public boolean isErrorEnabled()
    {
        return m_level <= LEVEL_ERROR;
    }

    public Logger getChildLogger( final String name )
    {
        return this;
    }

    private void output( final int level,
                         final String type,
                         final String message,
                         final Throwable throwable )
    {
        if( m_level <= level )
        {
            synchronized( System.out )
            {
                System.out.println( "[" + type + "] " + message );
                if( null != throwable )
                {
                    throwable.printStackTrace( System.out );
                }
            }
        }
    }
}
