/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl;

import java.util.logging.Level;

import org.codehaus.dna.impl.Jdk14Logger;

import junit.framework.TestCase;

public class Jdk14LoggerTestCase
    extends TestCase
{
    private MockLogger m_mockLogger;

    public void testLogkitLoggerEmptyCtor()
        throws Exception
    {
        try
        {
            new Jdk14Logger( null );
        }
        catch( NullPointerException npe )
        {
            assertEquals( "npe.getMessage()", "logger", npe.getMessage() );
        }
    }

    public void testJdk14LoggerGetChildLogger()
        throws Exception
    {
        final Jdk14Logger logger = createLogger( Level.FINE );

        assertNotSame( "logger.getChildLogger == logger",
                       logger,
                       logger.getChildLogger( "whatever" ) );
    }

    public void testJdk14LoggerTraceEnabled()
        throws Exception
    {
        final Level level = Level.ALL;
        final Level type = Level.FINEST;
        final String message = "Meep!";
        final Throwable throwable = null;
        final boolean output = true;

        final Jdk14Logger logger = createLogger( level );
        logger.trace( message );
        checkLogger( output, message, throwable, type );
    }

    public void testJdk14LoggerTraceDisabled()
        throws Exception
    {
        final Level level = Level.OFF;
        final String message = "Meep!";

        final Jdk14Logger logger = createLogger( level );
        logger.trace( message );
        checkLogger( false, null, null, null );
    }

    public void testJdk14LoggerTraceWithExceptionEnabled()
        throws Exception
    {
        final Level level = Level.ALL;
        final Level type = Level.FINEST;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();
        final boolean output = true;

        final Jdk14Logger logger = createLogger( level );

        logger.trace( message, throwable );
        checkLogger( output, message, throwable, type );
    }

    public void testJdk14LoggerTraceWithExceptionDisabled()
        throws Exception
    {
        final Level level = Level.OFF;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();

        final Jdk14Logger logger = createLogger( level );

        logger.trace( message, throwable );
        checkLogger( false, null, null, null );
    }

    public void testJdk14LoggerDebugEnabled()
        throws Exception
    {
        final Level level = Level.ALL;
        final Level type = Level.FINE;
        final String message = "Meep!";
        final Throwable throwable = null;
        final boolean output = true;

        final Jdk14Logger logger = createLogger( level );
        logger.debug( message );
        checkLogger( output, message, throwable, type );
    }

    public void testJdk14LoggerDebugDisabled()
        throws Exception
    {
        final Level level = Level.OFF;
        final String message = "Meep!";

        final Jdk14Logger logger = createLogger( level );
        logger.debug( message );
        checkLogger( false, null, null, null );
    }

    public void testJdk14LoggerDebugWithExceptionEnabled()
        throws Exception
    {
        final Level level = Level.ALL;
        final Level type = Level.FINE;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();
        final boolean output = true;

        final Jdk14Logger logger = createLogger( level );
        logger.debug( message, throwable );
        checkLogger( output, message, throwable, type );
    }

    public void testJdk14LoggerDebugWithExceptionDisabled()
        throws Exception
    {
        final Level level = Level.OFF;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();

        final Jdk14Logger logger = createLogger( level );
        logger.debug( message, throwable );
        checkLogger( false, null, null, null );
    }

    public void testJdk14LoggerInfoEnabled()
        throws Exception
    {
        final Level level = Level.ALL;
        final Level type = Level.INFO;
        final String message = "Meep!";
        final Throwable throwable = null;
        final boolean output = true;

        final Jdk14Logger logger = createLogger( level );
        logger.info( message );
        checkLogger( output, message, throwable, type );
    }

    public void testJdk14LoggerInfoDisabled()
        throws Exception
    {
        final Level level = Level.OFF;
        final String message = "Meep!";

        final Jdk14Logger logger = createLogger( level );
        logger.info( message );
        checkLogger( false, null, null, null );
    }

    public void testJdk14LoggerInfoWithExceptionEnabled()
        throws Exception
    {
        final Level level = Level.ALL;
        final Level type = Level.INFO;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();
        final boolean output = true;

        final Jdk14Logger logger = createLogger( level );
        logger.info( message, throwable );
        checkLogger( output, message, throwable, type );
    }

    public void testJdk14LoggerInfoWithExceptionDisabled()
        throws Exception
    {
        final Level level = Level.OFF;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();

        final Jdk14Logger logger = createLogger( level );
        logger.info( message, throwable );
        checkLogger( false, null, null, null );
    }

    public void testJdk14LoggerWarnEnabled()
        throws Exception
    {
        final Level level = Level.ALL;
        final Level type = Level.WARNING;
        final String message = "Meep!";
        final Throwable throwable = null;
        final boolean output = true;

        final Jdk14Logger logger = createLogger( level );
        logger.warn( message );
        checkLogger( output, message, throwable, type );
    }

    public void testJdk14LoggerWarnDisabled()
        throws Exception
    {
        final Level level = Level.OFF;
        final String message = "Meep!";

        final Jdk14Logger logger = createLogger( level );
        logger.warn( message );
        checkLogger( false, null, null, null );
    }

    public void testJdk14LoggerWarnWithExceptionEnabled()
        throws Exception
    {
        final Level level = Level.ALL;
        final Level type = Level.WARNING;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();
        final boolean output = true;

        final Jdk14Logger logger = createLogger( level );
        logger.warn( message, throwable );
        checkLogger( output, message, throwable, type );
    }

    public void testJdk14LoggerWarnWithExceptionDisabled()
        throws Exception
    {
        final Level level = Level.OFF;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();

        final Jdk14Logger logger = createLogger( level );
        logger.warn( message, throwable );
        checkLogger( false, null, null, null );
    }

    public void testJdk14LoggerErrorEnabled()
        throws Exception
    {
        final Level level = Level.ALL;
        final Level type = Level.SEVERE;
        final String message = "Meep!";
        final Throwable throwable = null;
        final boolean output = true;

        final Jdk14Logger logger = createLogger( level );
        logger.error( message );
        checkLogger( output, message, throwable, type );
    }

    public void testJdk14LoggerErrorWithExceptionEnabled()
        throws Exception
    {
        final Level level = Level.ALL;
        final Level type = Level.SEVERE;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();
        final boolean output = true;

        final Jdk14Logger logger = createLogger( level );
        logger.error( message, throwable );
        checkLogger( output, message, throwable, type );
    }

    public void testConsoleLevelComparisonWithDebugEnabled()
        throws Exception
    {
        final Jdk14Logger logger = createLogger( Level.FINEST );

        assertEquals( "logger.isTraceEnabled()", true, logger.isTraceEnabled() );
        assertEquals( "logger.isDebugEnabled()", true, logger.isDebugEnabled() );
        assertEquals( "logger.isInfoEnabled()", true, logger.isInfoEnabled() );
        assertEquals( "logger.isWarnEnabled()", true, logger.isWarnEnabled() );
        assertEquals( "logger.isErrorEnabled()", true, logger.isErrorEnabled() );
    }

    public void testConsoleLevelComparisonWithInfoEnabled()
        throws Exception
    {
        final Jdk14Logger logger = createLogger( Level.INFO );

        assertEquals( "logger.isTraceEnabled()", false, logger.isTraceEnabled() );
        assertEquals( "logger.isDebugEnabled()", false, logger.isDebugEnabled() );
        assertEquals( "logger.isInfoEnabled()", true, logger.isInfoEnabled() );
        assertEquals( "logger.isWarnEnabled()", true, logger.isWarnEnabled() );
        assertEquals( "logger.isErrorEnabled()", true, logger.isErrorEnabled() );
    }

    public void testConsoleLevelComparisonWithWarnEnabled()
        throws Exception
    {
        final Jdk14Logger logger = createLogger( Level.WARNING );

        assertEquals( "logger.isTraceEnabled()", false, logger.isTraceEnabled() );
        assertEquals( "logger.isDebugEnabled()", false, logger.isDebugEnabled() );
        assertEquals( "logger.isInfoEnabled()", false, logger.isInfoEnabled() );
        assertEquals( "logger.isWarnEnabled()", true, logger.isWarnEnabled() );
        assertEquals( "logger.isErrorEnabled()", true, logger.isErrorEnabled() );
    }

    public void testConsoleLevelComparisonWithErrorEnabled()
        throws Exception
    {
        final Jdk14Logger logger = createLogger( Level.SEVERE );

        assertEquals( "logger.isTraceEnabled()", false, logger.isTraceEnabled() );
        assertEquals( "logger.isDebugEnabled()", false, logger.isDebugEnabled() );
        assertEquals( "logger.isInfoEnabled()", false, logger.isInfoEnabled() );
        assertEquals( "logger.isWarnEnabled()", false, logger.isWarnEnabled() );
        assertEquals( "logger.isErrorEnabled()", true, logger.isErrorEnabled() );
    }

    private Jdk14Logger createLogger( final Level priority )
    {
        m_mockLogger = new MockLogger( priority );
        return new Jdk14Logger( m_mockLogger );
    }

    private void checkLogger( final boolean output,
                              final String message,
                              final Throwable throwable,
                              final Level priority )
    {
        assertEquals( "logger.m_message == message", message, m_mockLogger.m_message );
        assertEquals( "logger.m_output == output", output, m_mockLogger.m_output );
        assertEquals( "logger.m_throwable == null", throwable, m_mockLogger.m_throwable );
        assertEquals( "logger.m_priority == null", priority, m_mockLogger.m_priority );
    }
}
