/*
 * Copyright (C) The DNA Group. All rights reserved.
 *
 * This software is published under the terms of the DNA
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl;

import junit.framework.TestCase;

public class FreezableTestCase
    extends TestCase
{
    public void testMakeReadOnly()
        throws Exception
    {
        final MockFreezable freezable = new MockFreezable();
        assertEquals( "freezable.isReadOnly() prior to makeReadOnly",
                      false,
                      freezable.isReadOnly() );
        freezable.makeReadOnly();
        assertEquals( "freezable.isReadOnly() after to makeReadOnly",
                      true,
                      freezable.isReadOnly() );
    }

    public void testCheckWriteable()
        throws Exception
    {
        final MockFreezable freezable = new MockFreezable();
        freezable.makeReadOnly();
        try
        {
            freezable.checkWriteable();
        }
        catch( final IllegalStateException ise )
        {
            return;
        }
        fail( "Expected checkWriteable to throw an " +
              "IllegalStateException as freezable is" +
              "marked as read-only." );
    }

    public void testCheckWriteableOnWriteable()
        throws Exception
    {
        final MockFreezable freezable = new MockFreezable();
        freezable.checkWriteable();
    }
}
