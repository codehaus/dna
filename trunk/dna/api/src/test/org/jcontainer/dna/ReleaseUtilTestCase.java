/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna;

import junit.framework.TestCase;

/**
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.2 $ $Date: 2003-09-23 08:10:14 $
 */
public class ReleaseUtilTestCase
    extends TestCase
{
    public void testReleaseOnReleasable()
        throws Exception
    {
        final MockReleaseable releaseable = new MockReleaseable();
        ReleaseUtil.release( releaseable );
        assertEquals( "isReleased", true, releaseable.isReleased() );
    }

    public void testReleaseOnNonReleasable()
        throws Exception
    {
        final Object object = new Object();
        ReleaseUtil.release( object );
    }
}
