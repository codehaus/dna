/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl;

import junit.framework.TestCase;
import org.apache.log.Hierarchy;
import org.apache.log.LogTarget;
import org.apache.log.Logger;
import org.apache.log.Priority;
import org.codehaus.dna.impl.LogkitLogger;

public class LogkitLoggerTestCase
    extends TestCase
{
    public void testLogkitLoggerEmptyCtor()
        throws Exception
    {
        try
        {
            new LogkitLogger( null );
        }
        catch( NullPointerException npe )
        {
            assertEquals( "npe.getMessage()", "logger", npe.getMessage() );
        }
    }

    public void testLogkitLoggerGetChildLogger()
        throws Exception
    {
        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, Priority.DEBUG );

        assertNotSame( "logger.getChildLogger == logger",
                       logger,
                       logger.getChildLogger( "whatever" ) );
    }

    public void testLogkitLoggerTraceEnabled()
        throws Exception
    {
        final Priority level = Priority.DEBUG;
        final Priority type = Priority.DEBUG;
        final String message = "Meep!";
        final Throwable throwable = null;
        final boolean output = true;

        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, level );
        logger.trace( message );
        checkLogger( target, output, message, throwable, type );
    }

    public void testLogkitLoggerTraceDisabled()
        throws Exception
    {
        final Priority level = Priority.ERROR;
        final String message = "Meep!";

        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, level );
        logger.trace( message );
        checkLogger( target, false, null, null, null );
    }

    public void testLogkitLoggerTraceWithExceptionEnabled()
        throws Exception
    {
        final Priority level = Priority.DEBUG;
        final Priority type = Priority.DEBUG;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();
        final boolean output = true;

        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, level );

        logger.trace( message, throwable );
        checkLogger( target, output, message, throwable, type );
    }

    public void testLogkitLoggerTraceWithExceptionDisabled()
        throws Exception
    {
        final Priority level = Priority.ERROR;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();

        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, level );

        logger.trace( message, throwable );
        checkLogger( target, false, null, null, null );
    }

    public void testLogkitLoggerDebugEnabled()
        throws Exception
    {
        final Priority level = Priority.DEBUG;
        final Priority type = Priority.DEBUG;
        final String message = "Meep!";
        final Throwable throwable = null;
        final boolean output = true;

        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, level );
        logger.debug( message );
        checkLogger( target, output, message, throwable, type );
    }

    public void testLogkitLoggerDebugDisabled()
        throws Exception
    {
        final Priority level = Priority.ERROR;
        final String message = "Meep!";

        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, level );
        logger.debug( message );
        checkLogger( target, false, null, null, null );
    }

    public void testLogkitLoggerDebugWithExceptionEnabled()
        throws Exception
    {
        final Priority level = Priority.DEBUG;
        final Priority type = Priority.DEBUG;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();
        final boolean output = true;

        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, level );
        logger.debug( message, throwable );
        checkLogger( target, output, message, throwable, type );
    }

    public void testLogkitLoggerDebugWithExceptionDisabled()
        throws Exception
    {
        final Priority level = Priority.ERROR;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();

        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, level );
        logger.debug( message, throwable );
        checkLogger( target, false, null, null, null );
    }

    public void testLogkitLoggerInfoEnabled()
        throws Exception
    {
        final Priority level = Priority.DEBUG;
        final Priority type = Priority.INFO;
        final String message = "Meep!";
        final Throwable throwable = null;
        final boolean output = true;

        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, level );
        logger.info( message );
        checkLogger( target, output, message, throwable, type );
    }

    public void testLogkitLoggerInfoDisabled()
        throws Exception
    {
        final Priority level = Priority.ERROR;
        final String message = "Meep!";

        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, level );
        logger.info( message );
        checkLogger( target, false, null, null, null );
    }

    public void testLogkitLoggerInfoWithExceptionEnabled()
        throws Exception
    {
        final Priority level = Priority.DEBUG;
        final Priority type = Priority.INFO;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();
        final boolean output = true;

        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, level );
        logger.info( message, throwable );
        checkLogger( target, output, message, throwable, type );
    }

    public void testLogkitLoggerInfoWithExceptionDisabled()
        throws Exception
    {
        final Priority level = Priority.ERROR;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();

        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, level );
        logger.info( message, throwable );
        checkLogger( target, false, null, null, null );
    }

    public void testLogkitLoggerWarnEnabled()
        throws Exception
    {
        final Priority level = Priority.DEBUG;
        final Priority type = Priority.WARN;
        final String message = "Meep!";
        final Throwable throwable = null;
        final boolean output = true;

        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, level );
        logger.warn( message );
        checkLogger( target, output, message, throwable, type );
    }

    public void testLogkitLoggerWarnDisabled()
        throws Exception
    {
        final Priority level = Priority.ERROR;
        final String message = "Meep!";

        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, level );
        logger.warn( message );
        checkLogger( target, false, null, null, null );
    }

    public void testLogkitLoggerWarnWithExceptionEnabled()
        throws Exception
    {
        final Priority level = Priority.DEBUG;
        final Priority type = Priority.WARN;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();
        final boolean output = true;

        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, level );
        logger.warn( message, throwable );
        checkLogger( target, output, message, throwable, type );
    }

    public void testLogkitLoggerWarnWithExceptionDisabled()
        throws Exception
    {
        final Priority level = Priority.ERROR;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();

        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, level );
        logger.warn( message, throwable );
        checkLogger( target, false, null, null, null );
    }

    public void testLogkitLoggerErrorEnabled()
        throws Exception
    {
        final Priority level = Priority.DEBUG;
        final Priority type = Priority.ERROR;
        final String message = "Meep!";
        final Throwable throwable = null;
        final boolean output = true;

        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, level );
        logger.error( message );
        checkLogger( target, output, message, throwable, type );
    }

    public void testLogkitLoggerErrorWithExceptionEnabled()
        throws Exception
    {
        final Priority level = Priority.DEBUG;
        final Priority type = Priority.ERROR;
        final String message = "Meep!";
        final Throwable throwable = new Throwable();
        final boolean output = true;

        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, level );
        logger.error( message, throwable );
        checkLogger( target, output, message, throwable, type );
    }

    public void testConsoleLevelComparisonWithDebugEnabled()
        throws Exception
    {
        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, Priority.DEBUG );

        assertEquals( "logger.isTraceEnabled()", true, logger.isTraceEnabled() );
        assertEquals( "logger.isDebugEnabled()", true, logger.isDebugEnabled() );
        assertEquals( "logger.isInfoEnabled()", true, logger.isInfoEnabled() );
        assertEquals( "logger.isWarnEnabled()", true, logger.isWarnEnabled() );
        assertEquals( "logger.isErrorEnabled()", true, logger.isErrorEnabled() );
    }

    public void testConsoleLevelComparisonWithInfoEnabled()
        throws Exception
    {
        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, Priority.INFO );

        assertEquals( "logger.isTraceEnabled()", false, logger.isTraceEnabled() );
        assertEquals( "logger.isDebugEnabled()", false, logger.isDebugEnabled() );
        assertEquals( "logger.isInfoEnabled()", true, logger.isInfoEnabled() );
        assertEquals( "logger.isWarnEnabled()", true, logger.isWarnEnabled() );
        assertEquals( "logger.isErrorEnabled()", true, logger.isErrorEnabled() );
    }

    public void testConsoleLevelComparisonWithWarnEnabled()
        throws Exception
    {
        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, Priority.WARN );

        assertEquals( "logger.isTraceEnabled()", false, logger.isTraceEnabled() );
        assertEquals( "logger.isDebugEnabled()", false, logger.isDebugEnabled() );
        assertEquals( "logger.isInfoEnabled()", false, logger.isInfoEnabled() );
        assertEquals( "logger.isWarnEnabled()", true, logger.isWarnEnabled() );
        assertEquals( "logger.isErrorEnabled()", true, logger.isErrorEnabled() );
    }

    public void testConsoleLevelComparisonWithErrorEnabled()
        throws Exception
    {
        final MockLogTarget target = new MockLogTarget();
        final LogkitLogger logger = createLogger( target, Priority.ERROR );

        assertEquals( "logger.isTraceEnabled()", false, logger.isTraceEnabled() );
        assertEquals( "logger.isDebugEnabled()", false, logger.isDebugEnabled() );
        assertEquals( "logger.isInfoEnabled()", false, logger.isInfoEnabled() );
        assertEquals( "logger.isWarnEnabled()", false, logger.isWarnEnabled() );
        assertEquals( "logger.isErrorEnabled()", true, logger.isErrorEnabled() );
    }

    private LogkitLogger createLogger( final MockLogTarget target,
                                       final Priority priority )
    {
        final Hierarchy hierarchy = new Hierarchy();
        final Logger logkitLogger = hierarchy.getLoggerFor( "test" );
        logkitLogger.setLogTargets( new LogTarget[]{target} );
        logkitLogger.setPriority( priority );
        final LogkitLogger logger = new LogkitLogger( logkitLogger );
        return logger;
    }

    private void checkLogger( final MockLogTarget target,
                              final boolean output,
                              final String message,
                              final Throwable throwable,
                              final Priority priority )
    {
        assertEquals( "logger.m_message == message", message, target.m_message );
        assertEquals( "logger.m_output == output", output, target.m_output );
        assertEquals( "logger.m_throwable == null", throwable, target.m_throwable );
        assertEquals( "logger.m_priority == null", priority, target.m_priority );
    }
}
