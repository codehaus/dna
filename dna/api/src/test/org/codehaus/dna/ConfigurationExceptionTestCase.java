/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna;

import org.codehaus.dna.ConfigurationException;

import junit.framework.TestCase;

/**
 *
 * @author Peter Donald
 * @version $Revision: 1.1 $ $Date: 2004-04-18 20:13:46 $
 */
public class ConfigurationExceptionTestCase
    extends TestCase
{
    public void testConfigurationExceptionConstruction()
        throws Exception
    {
        final String message = "myMessage";
        final String path = "/my/path";
        final String location = "mylocation.xml:20";
        final Throwable cause = new Throwable();
        final ConfigurationException exception =
            new ConfigurationException( message, path, location, cause );

        assertEquals( "message", message, exception.getMessage() );
        assertEquals( "path", path, exception.getPath() );
        assertEquals( "location", location, exception.getLocation() );
        assertEquals( "cause", cause, exception.getCause() );
    }

    public void testConfigurationExceptionConstructionWithNullCause()
        throws Exception
    {
        final String message = "myMessage";
        final String path = "/my/path";
        final String location = "mylocation.xml:20";
        final Throwable cause = null;
        final ConfigurationException exception =
            new ConfigurationException( message, path, location, cause );

        assertEquals( "message", message, exception.getMessage() );
        assertEquals( "path", path, exception.getPath() );
        assertEquals( "location", location, exception.getLocation() );
        assertEquals( "cause", cause, exception.getCause() );
    }

    public void testConfigurationExceptionConstructionWithNullKey()
        throws Exception
    {
        final String message = "myMessage";
        final String path = null;
        final String location = "mylocation.xml:20";
        final Throwable cause = new Throwable();
        final ConfigurationException exception =
            new ConfigurationException( message, path, location, cause );

        assertEquals( "message", message, exception.getMessage() );
        assertEquals( "path", path, exception.getPath() );
        assertEquals( "location", location, exception.getLocation() );
        assertEquals( "cause", cause, exception.getCause() );
    }

    public void testConfigurationExceptionConstructionWithNullMessage()
        throws Exception
    {
        final String message = null;
        final String path = "/my/path";
        final String location = "mylocation.xml:20";
        final Throwable cause = new Throwable();
        final ConfigurationException exception =
            new ConfigurationException( message, path, location, cause );

        assertEquals( "message", message, exception.getMessage() );
        assertEquals( "path", path, exception.getPath() );
        assertEquals( "location", location, exception.getLocation() );
        assertEquals( "cause", cause, exception.getCause() );
    }

    public void testConfigurationExceptionConstructionWithNullLocation()
        throws Exception
    {
        final String message = "myMessage";
        final String path = "/my/path";
        final String location = null;
        final Throwable cause = new Throwable();
        final ConfigurationException exception =
            new ConfigurationException( message, path, location, cause );

        assertEquals( "message", message, exception.getMessage() );
        assertEquals( "path", path, exception.getPath() );
        assertEquals( "location", location, exception.getLocation() );
        assertEquals( "cause", cause, exception.getCause() );
    }

    public void testConfigurationExceptionConstructionWith3ArgCtor()
        throws Exception
    {
        final String message = "myMessage";
        final String path = "/my/path";
        final String location = "mylocation.xml:20";
        final ConfigurationException exception =
            new ConfigurationException( message, path, location );

        assertEquals( "message", message, exception.getMessage() );
        assertEquals( "path", path, exception.getPath() );
        assertEquals( "location", location, exception.getLocation() );
        assertEquals( "cause", null, exception.getCause() );
    }

    public void testConfigurationExceptionConstructionWith2ArgCtor()
        throws Exception
    {
        final String message = "myMessage";
        final Throwable cause = new Throwable();
        final ConfigurationException exception =
            new ConfigurationException( message, cause );

        assertEquals( "message", message, exception.getMessage() );
        assertEquals( "path", null, exception.getPath() );
        assertEquals( "location", null, exception.getLocation() );
        assertEquals( "cause", cause, exception.getCause() );
    }

    public void testConfigurationExceptionToString()
        throws Exception
    {
        final String path = "/my/path";
        final String location = "mylocation.xml:20";
        final ConfigurationException exception =
            new ConfigurationException( "myMessage", path, location );

        final String expected =
            "org.codehaus.dna.ConfigurationException: myMessage" +
            " - " + path +
            " @ " + location;

        assertEquals( expected, exception.toString() );
    }

    public void testConfigurationExceptionToStringWithNullPath()
        throws Exception
    {
        final String location = "mylocation.xml:20";
        final ConfigurationException exception =
            new ConfigurationException( "myMessage", null, location );

        final String expected =
            "org.codehaus.dna.ConfigurationException: myMessage" +
            " @ " + location;

        assertEquals( expected, exception.toString() );
    }

    public void testConfigurationExceptionToStringWithNullLocation()
        throws Exception
    {
        final String path = "/my/path";
        final ConfigurationException exception =
            new ConfigurationException( "myMessage", path, null );

        final String expected =
            "org.codehaus.dna.ConfigurationException: myMessage" +
            " - " + path;

        assertEquals( expected, exception.toString() );
    }

    public void testConfigurationExceptionToStringWithNullLocationAndPath()
        throws Exception
    {
        final ConfigurationException exception =
            new ConfigurationException( "myMessage", null, null );

        final String expected =
            "org.codehaus.dna.ConfigurationException: myMessage";

        assertEquals( expected, exception.toString() );
    }

    public void testConfigurationExceptionToStringWithEmptyPath()
        throws Exception
    {
        final String location = "mylocation.xml:20";
        final ConfigurationException exception =
            new ConfigurationException( "myMessage", "", location );

        final String expected =
            "org.codehaus.dna.ConfigurationException: myMessage" +
            " @ " + location;

        assertEquals( expected, exception.toString() );
    }

    public void testConfigurationExceptionToStringWithEmptyLocation()
        throws Exception
    {
        final String path = "/my/path";
        final ConfigurationException exception =
            new ConfigurationException( "myMessage", path, "" );

        final String expected =
            "org.codehaus.dna.ConfigurationException: myMessage" +
            " - " + path;

        assertEquals( expected, exception.toString() );
    }

    public void testConfigurationExceptionToStringWithEmptyLocationAndPath()
        throws Exception
    {
        final ConfigurationException exception =
            new ConfigurationException( "myMessage", "", "" );

        final String expected =
            "org.codehaus.dna.ConfigurationException: myMessage";

        assertEquals( expected, exception.toString() );
    }
}
