/*
 * Copyright (C) The DNA Group. All rights reserved.
 *
 * This software is published under the terms of the DNA
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna;

import org.codehaus.dna.ReleaseUtil;

import junit.framework.TestCase;

/**
 *
 * @author Peter Donald
 * @version $Revision: 1.2 $ $Date: 2004-05-01 09:51:48 $
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
