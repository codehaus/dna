/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl;

import junit.framework.TestCase;

import org.codehaus.dna.MissingResourceException;
import org.codehaus.dna.impl.DefaultResourceLocator;

public class DefaultResourceLocatorTestCase
    extends TestCase
{
    public void testLookupMissingResourceWithNoParent()
        throws Exception
    {
        final DefaultResourceLocator locator = new DefaultResourceLocator();
        assertEquals( "locator.contains(rez) post to insert",
                      false,
                      locator.contains( "rez" ) );

        try
        {
            locator.lookup( "rez" );
        }
        catch( MissingResourceException e )
        {
            return;
        }
        fail( "Expected to fail looking up missing resource" );
    }

    public void testLookupMissingResourceWithParent()
        throws Exception
    {
        final DefaultResourceLocator parent = new DefaultResourceLocator();
        final DefaultResourceLocator locator = new DefaultResourceLocator( parent );
        assertEquals( "locator.contains(rez) post to insert",
                      false,
                      locator.contains( "rez" ) );

        try
        {
            locator.lookup( "rez" );
        }
        catch( MissingResourceException e )
        {
            return;
        }
        fail( "Expected to fail looking up missing resource" );
    }

    public void testLookupResourceInLocalLocator()
        throws Exception
    {
        final Object resource = new Object();
        final DefaultResourceLocator locator = new DefaultResourceLocator();
        locator.put( "rez", resource );
        assertEquals( "locator.contains(rez) post to insert",
                      true,
                      locator.contains( "rez" ) );

        final Object result = locator.lookup( "rez" );
        assertEquals( "locator.contains(rez) == resource",
                      resource, result );
    }

    public void testLookupResourceInParentLocator()
        throws Exception
    {
        final Object resource = new Object();
        final DefaultResourceLocator parent = new DefaultResourceLocator();
        final DefaultResourceLocator locator = new DefaultResourceLocator( parent );
        parent.put( "rez", resource );
        assertEquals( "locator.contains(rez) post to insert",
                      true,
                      locator.contains( "rez" ) );

        final Object result = locator.lookup( "rez" );
        assertEquals( "locator.contains(rez) == resource",
                      resource, result );
    }

    public void testPutWithNullKey()
        throws Exception
    {
        final DefaultResourceLocator locator = new DefaultResourceLocator();
        try
        {
            locator.put( null, new Object() );
        }
        catch( NullPointerException e )
        {
            assertEquals( "key", e.getMessage() );
            return;
        }
        fail( "Expect to fail to put resource with null key" );
    }

    public void testPutWithNullResource()
        throws Exception
    {
        final DefaultResourceLocator locator = new DefaultResourceLocator();
        try
        {
            locator.put( "rez", null );
        }
        catch( NullPointerException e )
        {
            assertEquals( "resource", e.getMessage() );
            return;
        }
        fail( "Expect to fail to put resource with null resource" );
    }
}
