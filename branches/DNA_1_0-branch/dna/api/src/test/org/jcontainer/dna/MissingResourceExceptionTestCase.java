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
