/*
 * Copyright (C) The DNA Group. All rights reserved.
 *
 * This software is published under the terms of the DNA
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl;

import org.codehaus.dna.Logger;

/**
 * Logging facade implmentation for Apache Logkit project.
 * The following lists the mapping between DNA log levels
 * and LogKit log levels.
 *
 * <ul>
 *   <li>trace ==&gt; debug</li>
 *   <li>debug ==&gt; debug</li>
 *   <li>info ==&gt; info</li>
 *   <li>warn ==&gt; warn</li>
 *   <li>error ==&gt; error</li>
 * </ul>
 *
 * @version $Revision: 1.2 $ $Date: 2004-05-01 09:51:48 $
 */
public class LogkitLogger
    implements Logger
{
    /**
     * The Logkit logger.
     */
    private final org.apache.log.Logger m_logger;

    /**
     * Create an instance of LogKit logger facade.
     *
     * @param logger the logkit logger
     */
    public LogkitLogger( final org.apache.log.Logger logger )
    {
        if( null == logger )
        {
            throw new NullPointerException( "logger" );
        }
        m_logger = logger;
    }

    /**
     * Log a trace message.
     *
     * @param message the message
     */
    public void trace( final String message )
    {
        m_logger.debug( message );
    }

    /**
     * Log a trace message with an associated throwable.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public void trace( final String message,
                       final Throwable throwable )
    {
        m_logger.debug( message, throwable );
    }

    /**
     * Return true if a trace message will be logged.
     *
     * @return true if message will be logged
     */
    public boolean isTraceEnabled()
    {
        return m_logger.isDebugEnabled();
    }

    /**
     * Log a debug message.
     *
     * @param message the message
     */
    public void debug( final String message )
    {
        m_logger.debug( message );
    }

    /**
     * Log a debug message with an associated throwable.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public void debug( final String message,
                       final Throwable throwable )
    {
        m_logger.debug( message, throwable );
    }

    /**
     * Return true if a debug message will be logged.
     *
     * @return true if message will be logged
     */
    public boolean isDebugEnabled()
    {
        return m_logger.isDebugEnabled();
    }

    /**
     * Log a info message.
     *
     * @param message the message
     */
    public void info( final String message )
    {
        m_logger.info( message );
    }

    /**
     * Log a info message with an associated throwable.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public void info( final String message,
                      final Throwable throwable )
    {
        m_logger.info( message, throwable );
    }

    /**
     * Return true if an info message will be logged.
     *
     * @return true if message will be logged
     */
    public boolean isInfoEnabled()
    {
        return m_logger.isInfoEnabled();
    }

    /**
     * Log a warn message.
     *
     * @param message the message
     */
    public void warn( final String message )
    {
        m_logger.warn( message );
    }

    /**
     * Log a warn message with an associated throwable.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public void warn( final String message,
                      final Throwable throwable )
    {
        m_logger.warn( message, throwable );
    }

    /**
     * Return true if a warn message will be logged.
     *
     * @return true if message will be logged
     */
    public boolean isWarnEnabled()
    {
        return m_logger.isWarnEnabled();
    }

    /**
     * Log a error message.
     *
     * @param message the message
     */
    public void error( final String message )
    {
        m_logger.error( message );
    }

    /**
     * Log a error message with an associated throwable.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public void error( final String message,
                       final Throwable throwable )
    {
        m_logger.error( message, throwable );
    }

    /**
     * Return true if a error message will be logged.
     *
     * @return true if message will be logged
     */
    public boolean isErrorEnabled()
    {
        return m_logger.isErrorEnabled();
    }

    /**
     * Get the child logger with specified name.
     *
     * @param name the name of child logger
     * @return the child logger
     */
    public Logger getChildLogger( final String name )
    {
        return new LogkitLogger( m_logger.getChildLogger( name ) );
    }
}
