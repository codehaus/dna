/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna;

import junit.framework.TestCase;

/**
 *
 * @author Peter Donald
 * @version $Revision: 1.1 $ $Date: 2004-04-18 20:13:46 $
 */
public class LogEnabledTestCase
    extends TestCase
{
    public void testGetLogger()
        throws Exception
    {
        final MockLogEnabled logEnabled = new MockLogEnabled();
        final MockLogger logger = new MockLogger( "base" );
        logEnabled.enableLogging( logger );
        assertEquals( "logger", logger, logEnabled.getLogger() );
    }

    public void testSetupLoggerOnLogEnabled()
        throws Exception
    {
        final MockLogEnabled logEnabled = new MockLogEnabled();
        final MockLogEnabled childLogEnabled = new MockLogEnabled();
        final MockLogger logger = new MockLogger( "base" );
        logEnabled.enableLogging( logger );
        logEnabled.setupLogger( childLogEnabled );
        assertEquals( "logEnabled.logger", logger, logEnabled.getLogger() );
        assertEquals( "childLogEnabled.logger", logger, childLogEnabled.getLogger() );
    }

    public void testSetupLoggerOnNonLogEnabled()
        throws Exception
    {
        final MockLogEnabled logEnabled = new MockLogEnabled();
        final MockLogger logger = new MockLogger( "base" );
        logEnabled.enableLogging( logger );
        logEnabled.setupLogger( new Object() );
    }

    public void testSetupLoggerWithNameOnLogEnabled()
        throws Exception
    {
        final MockLogEnabled logEnabled = new MockLogEnabled();
        final MockLogEnabled childLogEnabled = new MockLogEnabled();
        final MockLogger logger = new MockLogger( "base" );
        logEnabled.enableLogging( logger );
        logEnabled.setupLogger( childLogEnabled, "child" );
        assertEquals( "logEnabled.logger", logger, logEnabled.getLogger() );
        assertEquals( "childLogEnabled.logger.name",
                      "base.child",
                      ( (MockLogger)childLogEnabled.getLogger() ).getName() );
    }

    public void testSetupLoggerWithNullName()
        throws Exception
    {
        final MockLogEnabled logEnabled = new MockLogEnabled();
        final MockLogEnabled childLogEnabled = new MockLogEnabled();
        final MockLogger logger = new MockLogger( "base" );
        logEnabled.enableLogging( logger );
        try
        {
            logEnabled.setupLogger( childLogEnabled, null );
        }
        catch( final NullPointerException npe )
        {
            assertEquals( "npe.message", "name", npe.getMessage() );
            return;
        }
        fail( "Expected to fail setting up child logger with null name" );
    }
}
