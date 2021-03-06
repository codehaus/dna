/*
 * Copyright (C) The DNA Group. All rights reserved.
 *
 * This software is published under the terms of the DNA
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl;

import junit.framework.TestCase;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

import org.codehaus.dna.impl.ConsoleLogger;

public class ConsoleLoggerTestCase
    extends TestCase
{
    public void testConsoleWithEmptyOutput()
        throws Exception
    {
        try
        {
            new ConsoleLogger( MockConsoleLogger.LEVEL_ALL, null );
        }
        catch( final NullPointerException npe )
        {
            assertEquals( "npe.message", "output", npe.getMessage() );
            return;
        }
        fail( "Expected to fail due to NPE in ctor" );
    }

    public void testMockConsoleEmptyCtor()
        throws Exception
    {
        final MockConsoleLogger logger = new MockConsoleLogger();
        assertEquals( "logger.level",
                      MockConsoleLogger.LEVEL_ALL,
                      logger.getLevel() );
    }

    public void testMockConsoleGetChildLogger()
        throws Exception
    {
        final MockConsoleLogger logger = new MockConsoleLogger();
        assertEquals( "logger.getChildLogger == logger",
                      logger,
                      logger.getChildLogger( "whatever" ) );
    }

    public void testMockConsoleOutputToConsole()
        throws Exception
    {
        final ByteArrayOutputStream arrayOutput = new ByteArrayOutputStream();
        final PrintStream output = new PrintStream( arrayOutput );
        final ConsoleLogger logger = new ConsoleLogger( MockConsoleLogger.LEVEL_ALL, output );
        logger.debug( "ignore me!", null );
        final String message = arrayOutput.toString();
        final String expectedMessage =
            "[DEBUG] ignore me!" + System.getProperty( "line.separator" );
        assertEquals( "message", expectedMessage, message );
    }

    public void testMockConsoleOutputToConsoleWithException()
        throws Exception
    {
        final ByteArrayOutputStream arrayOutput = new ByteArrayOutputStream();
        final PrintStream output = new PrintStream( arrayOutput );
        final ConsoleLogger logger = new ConsoleLogger( MockConsoleLogger.LEVEL_ALL, output );
        logger.debug( "ignore me!", new Throwable( "Ignore me aswell!" ) );
        final String message = arrayOutput.toString();
        final String expectedMessage =
            "[DEBUG] ignore me!" + System.getProperty( "line.separator" );
        assertTrue( "message", message.startsWith( expectedMessage ) );
        assertTrue( "throwable message", -1 != message.indexOf( "Ignore me aswell!" ) );
        assertTrue( "throwable", -1 != message.indexOf( Throwable.class.getName() ) );
    }

    public void testMockConsoleTraceEnabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_ALL;
        final String message = "Meep!";
        final String type = "TRACE";
        final Throwable throwable = null;
        final boolean output = true;

        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.trace( message );
        checkLogger( logger, output, message, throwable, type );
    }

    public void testMockConsoleTraceDisabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_NONE;
        final String message = "Meep!";
        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.trace( message );
        checkLogger( logger, false, null, null, null );
    }

    public void testMockConsoleTraceWithExceptionEnabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_ALL;
        final String message = "Meep!";
        final String type = "TRACE";
        final Throwable throwable = new Throwable();
        final boolean output = true;

        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.trace( message, throwable );
        checkLogger( logger, output, message, throwable, type );
    }

    public void testMockConsoleTraceWithExceptionDisabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_NONE;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();
        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.trace( message, throwable );
        checkLogger( logger, false, null, null, null );
    }

    public void testMockConsoleDebugEnabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_ALL;
        final String message = "Meep!";
        final String type = "DEBUG";
        final Throwable throwable = null;
        final boolean output = true;

        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.debug( message );
        checkLogger( logger, output, message, throwable, type );
    }

    public void testMockConsoleDebugDisabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_NONE;
        final String message = "Meep!";
        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.debug( message );
        checkLogger( logger, false, null, null, null );
    }

    public void testMockConsoleDebugWithExceptionEnabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_ALL;
        final String message = "Meep!";
        final String type = "DEBUG";
        final Throwable throwable = new Throwable();
        final boolean output = true;

        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.debug( message, throwable );
        checkLogger( logger, output, message, throwable, type );
    }

    public void testMockConsoleDebugWithExceptionDisabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_NONE;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();
        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.debug( message, throwable );
        checkLogger( logger, false, null, null, null );
    }

    public void testMockConsoleInfoEnabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_ALL;
        final String message = "Meep!";
        final String type = "INFO";
        final Throwable throwable = null;
        final boolean output = true;

        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.info( message );
        checkLogger( logger, output, message, throwable, type );
    }

    public void testMockConsoleInfoDisabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_NONE;
        final String message = "Meep!";
        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.info( message );
        checkLogger( logger, false, null, null, null );
    }

    public void testMockConsoleInfoWithExceptionEnabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_ALL;
        final String message = "Meep!";
        final String type = "INFO";
        final Throwable throwable = new Throwable();
        final boolean output = true;

        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.info( message, throwable );
        checkLogger( logger, output, message, throwable, type );
    }

    public void testMockConsoleInfoWithExceptionDisabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_NONE;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();
        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.info( message, throwable );
        checkLogger( logger, false, null, null, null );
    }

    public void testMockConsoleWarnEnabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_ALL;
        final String message = "Meep!";
        final String type = "WARN";
        final Throwable throwable = null;
        final boolean output = true;

        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.warn( message );
        checkLogger( logger, output, message, throwable, type );
    }

    public void testMockConsoleWarnDisabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_NONE;
        final String message = "Meep!";
        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.warn( message );
        checkLogger( logger, false, null, null, null );
    }

    public void testMockConsoleWarnWithExceptionEnabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_ALL;
        final String message = "Meep!";
        final String type = "WARN";
        final Throwable throwable = new Throwable();
        final boolean output = true;

        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.warn( message, throwable );
        checkLogger( logger, output, message, throwable, type );
    }

    public void testMockConsoleWarnWithExceptionDisabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_NONE;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();
        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.warn( message, throwable );
        checkLogger( logger, false, null, null, null );
    }

    public void testMockConsoleErrorEnabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_ALL;
        final String message = "Meep!";
        final String type = "ERROR";
        final Throwable throwable = null;
        final boolean output = true;

        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.error( message );
        checkLogger( logger, output, message, throwable, type );
    }

    public void testMockConsoleErrorDisabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_NONE;
        final String message = "Meep!";
        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.error( message );
        checkLogger( logger, false, null, null, null );
    }

    public void testMockConsoleErrorWithExceptionEnabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_ALL;
        final String message = "Meep!";
        final String type = "ERROR";
        final Throwable throwable = new Throwable();
        final boolean output = true;

        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.error( message, throwable );
        checkLogger( logger, output, message, throwable, type );
    }

    public void testMockConsoleErrorWithExceptionDisabled()
        throws Exception
    {
        final int level = MockConsoleLogger.LEVEL_NONE;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();
        final MockConsoleLogger logger = new MockConsoleLogger( level );
        logger.error( message, throwable );
        checkLogger( logger, false, null, null, null );
    }

    public void testConsoleLevelComparisonWithAll()
        throws Exception
    {
        final MockConsoleLogger logger = new MockConsoleLogger( MockConsoleLogger.LEVEL_ALL );
        assertEquals( "logger.isTraceEnabled()", true, logger.isTraceEnabled() );
        assertEquals( "logger.isDebugEnabled()", true, logger.isDebugEnabled() );
        assertEquals( "logger.isInfoEnabled()", true, logger.isInfoEnabled() );
        assertEquals( "logger.isWarnEnabled()", true, logger.isWarnEnabled() );
        assertEquals( "logger.isErrorEnabled()", true, logger.isErrorEnabled() );
    }

    public void testConsoleLevelComparisonWithNone()
        throws Exception
    {
        final MockConsoleLogger logger = new MockConsoleLogger( MockConsoleLogger.LEVEL_NONE );
        assertEquals( "logger.isTraceEnabled()", false, logger.isTraceEnabled() );
        assertEquals( "logger.isDebugEnabled()", false, logger.isDebugEnabled() );
        assertEquals( "logger.isInfoEnabled()", false, logger.isInfoEnabled() );
        assertEquals( "logger.isWarnEnabled()", false, logger.isWarnEnabled() );
        assertEquals( "logger.isErrorEnabled()", false, logger.isErrorEnabled() );
    }

    public void testConsoleLevelComparisonWithTraceEnabled()
        throws Exception
    {
        final MockConsoleLogger logger = new MockConsoleLogger( MockConsoleLogger.LEVEL_TRACE );
        assertEquals( "logger.isTraceEnabled()", true, logger.isTraceEnabled() );
        assertEquals( "logger.isDebugEnabled()", true, logger.isDebugEnabled() );
        assertEquals( "logger.isInfoEnabled()", true, logger.isInfoEnabled() );
        assertEquals( "logger.isWarnEnabled()", true, logger.isWarnEnabled() );
        assertEquals( "logger.isErrorEnabled()", true, logger.isErrorEnabled() );
    }

    public void testConsoleLevelComparisonWithDebugEnabled()
        throws Exception
    {
        final MockConsoleLogger logger = new MockConsoleLogger( MockConsoleLogger.LEVEL_DEBUG );
        assertEquals( "logger.isTraceEnabled()", false, logger.isTraceEnabled() );
        assertEquals( "logger.isDebugEnabled()", true, logger.isDebugEnabled() );
        assertEquals( "logger.isInfoEnabled()", true, logger.isInfoEnabled() );
        assertEquals( "logger.isWarnEnabled()", true, logger.isWarnEnabled() );
        assertEquals( "logger.isErrorEnabled()", true, logger.isErrorEnabled() );
    }

    public void testConsoleLevelComparisonWithInfoEnabled()
        throws Exception
    {
        final MockConsoleLogger logger = new MockConsoleLogger( MockConsoleLogger.LEVEL_INFO );
        assertEquals( "logger.isTraceEnabled()", false, logger.isTraceEnabled() );
        assertEquals( "logger.isDebugEnabled()", false, logger.isDebugEnabled() );
        assertEquals( "logger.isInfoEnabled()", true, logger.isInfoEnabled() );
        assertEquals( "logger.isWarnEnabled()", true, logger.isWarnEnabled() );
        assertEquals( "logger.isErrorEnabled()", true, logger.isErrorEnabled() );
    }

    public void testConsoleLevelComparisonWithWarnEnabled()
        throws Exception
    {
        final MockConsoleLogger logger = new MockConsoleLogger( MockConsoleLogger.LEVEL_WARN );
        assertEquals( "logger.isTraceEnabled()", false, logger.isTraceEnabled() );
        assertEquals( "logger.isDebugEnabled()", false, logger.isDebugEnabled() );
        assertEquals( "logger.isInfoEnabled()", false, logger.isInfoEnabled() );
        assertEquals( "logger.isWarnEnabled()", true, logger.isWarnEnabled() );
        assertEquals( "logger.isErrorEnabled()", true, logger.isErrorEnabled() );
    }

    public void testConsoleLevelComparisonWithErrorEnabled()
        throws Exception
    {
        final MockConsoleLogger logger = new MockConsoleLogger( MockConsoleLogger.LEVEL_ERROR );
        assertEquals( "logger.isTraceEnabled()", false, logger.isTraceEnabled() );
        assertEquals( "logger.isDebugEnabled()", false, logger.isDebugEnabled() );
        assertEquals( "logger.isInfoEnabled()", false, logger.isInfoEnabled() );
        assertEquals( "logger.isWarnEnabled()", false, logger.isWarnEnabled() );
        assertEquals( "logger.isErrorEnabled()", true, logger.isErrorEnabled() );
    }

    private void checkLogger( final MockConsoleLogger logger,
                              final boolean output,
                              final String message,
                              final Throwable throwable,
                              final String type )
    {
        assertEquals( "logger.m_message == message", message, logger.m_message );
        assertEquals( "logger.m_output == true", output, logger.m_output );
        assertEquals( "logger.m_throwable == null", throwable, logger.m_throwable );
        assertEquals( "logger.m_type == null", type, logger.m_type );
    }
}
