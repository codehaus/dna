/*
 * Copyright (C) The DNA Group. All rights reserved.
 *
 * This software is published under the terms of the DNA
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna;

import org.codehaus.dna.MissingResourceException;

import junit.framework.TestCase;

/**
 *
 * @author Peter Donald
 * @version $Revision: 1.2 $ $Date: 2004-05-01 09:51:48 $
 */
public class MissingResourceExceptionTestCase
    extends TestCase
{
    public void testMissingResourceExceptionConstruction()
        throws Exception
    {
        final String message = "myMessage";
        final String key = "myKey";
        final Throwable cause = new Throwable();
        final MissingResourceException exception =
            new MissingResourceException( message, key, cause );

        assertEquals( "message", message, exception.getMessage() );
        assertEquals( "key", key, exception.getKey() );
        assertEquals( "cause", cause, exception.getCause() );
    }

    public void testMissingResourceExceptionConstructionWithNullCause()
        throws Exception
    {
        final String message = "myMessage";
        final String key = "myKey";
        final Throwable cause = null;
        final MissingResourceException exception =
            new MissingResourceException( message, key, cause );

        assertEquals( "message", message, exception.getMessage() );
        assertEquals( "key", key, exception.getKey() );
        assertEquals( "cause", cause, exception.getCause() );
    }

    public void testMissingResourceExceptionConstructionWithNullKey()
        throws Exception
    {
        final String message = "myMessage";
        final String key = null;
        final Throwable cause = new Throwable();
        final MissingResourceException exception =
            new MissingResourceException( message, key, cause );

        assertEquals( "message", message, exception.getMessage() );
        assertEquals( "key", key, exception.getKey() );
        assertEquals( "cause", cause, exception.getCause() );
    }

    public void testMissingResourceExceptionConstructionWithNullMessage()
        throws Exception
    {
        final String message = null;
        final String key = "myKey";
        final Throwable cause = new Throwable();
        final MissingResourceException exception =
            new MissingResourceException( message, key, cause );

        assertEquals( "message", message, exception.getMessage() );
        assertEquals( "key", key, exception.getKey() );
        assertEquals( "cause", cause, exception.getCause() );
    }

    public void testMissingResourceExceptionConstructionWithSimpleCtor()
        throws Exception
    {
        final String message = "myMessage";
        final String key = "myKey";
        final MissingResourceException exception =
            new MissingResourceException( message, key );

        assertEquals( "message", message, exception.getMessage() );
        assertEquals( "key", key, exception.getKey() );
        assertEquals( "cause", null, exception.getCause() );
    }
}
