/*
 * Copyright (C) The DNA Group. All rights reserved.
 *
 * This software is published under the terms of the DNA
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl;

import junit.framework.TestCase;

import org.codehaus.dna.ResourceLocator;
import org.codehaus.dna.impl.ConsoleLogger;
import org.codehaus.dna.impl.ContainerUtil;
import org.codehaus.dna.impl.DefaultConfiguration;
import org.codehaus.dna.impl.DefaultResourceLocator;

public class ContainerUtilTestCase
    extends TestCase
{
    public void testEnableLoggingOnComponentNotImplementingStage()
        throws Exception
    {
        final Object object = new Object();
        ContainerUtil.enableLogging( object, null );
    }

    public void testEnableLoggingOnComponentImplementingStage()
        throws Exception
    {
        final MockComponent object = new MockComponent();
        final ConsoleLogger logger = new ConsoleLogger();

        ContainerUtil.enableLogging( object, logger );

        assertEquals( logger, object.getLogger() );
    }

    public void testEnableLoggingOnComponentImplementingStageButNullLogger()
        throws Exception
    {
        final MockComponent object = new MockComponent();
        final ConsoleLogger logger = null;

        try
        {
            ContainerUtil.enableLogging( object, logger );
        }
        catch( IllegalArgumentException iae )
        {
            return;
        }
        fail( "Expected stage to fail as passing in null " +
              "resource but object implements stage." );
    }

    public void testComposeOnComponentNotImplementingStage()
        throws Exception
    {
        final Object object = new Object();
        ContainerUtil.compose( object, null );
    }

    public void testComposeOnComponentImplementingStage()
        throws Exception
    {
        final MockComponent object = new MockComponent();
        final ResourceLocator resource = new DefaultResourceLocator();

        ContainerUtil.compose( object, resource );

        assertEquals( resource, object.getServices() );
    }

    public void testComposeOnComponentImplementingStageButNullLogger()
        throws Exception
    {
        final MockComponent object = new MockComponent();
        final ResourceLocator resource = null;

        try
        {
            ContainerUtil.compose( object, resource );
        }
        catch( IllegalArgumentException iae )
        {
            return;
        }
        fail( "Expected stage to fail as passing in null " +
              "resource but object implements stage." );
    }

    public void testConfigureOnComponentNotImplementingStage()
        throws Exception
    {
        final Object object = new Object();
        ContainerUtil.configure( object, null );
    }

    public void testConfigureOnComponentImplementingStage()
        throws Exception
    {
        final MockComponent object = new MockComponent();
        final DefaultConfiguration resource = new DefaultConfiguration( "s", "", "" );

        ContainerUtil.configure( object, resource );

        assertEquals( resource, object.getConfiguration() );
    }

    public void testConfigureOnComponentImplementingStageButNullLogger()
        throws Exception
    {
        final MockComponent object = new MockComponent();
        final DefaultConfiguration resource = null;

        try
        {
            ContainerUtil.configure( object, resource );
        }
        catch( IllegalArgumentException iae )
        {
            return;
        }
        fail( "Expected stage to fail as passing in null " +
              "resource but object implements stage." );
    }

    public void testInitializeOnComponentNotImplementingStage()
        throws Exception
    {
        final Object object = new Object();
        ContainerUtil.initialize( object );
    }

    public void testInitializeOnComponentImplementingStage()
        throws Exception
    {
        final MockComponent object = new MockComponent();
        ContainerUtil.initialize( object );
        assertEquals( true, object.isInitialized() );
    }

    public void testDisposeOnComponentNotImplementingStage()
        throws Exception
    {
        final Object object = new Object();
        ContainerUtil.dispose( object );
    }

    public void testDisposeOnComponentImplementingStage()
        throws Exception
    {
        final MockComponent object = new MockComponent();
        ContainerUtil.dispose( object );
        assertEquals( true, object.isDisposed() );
    }
}
