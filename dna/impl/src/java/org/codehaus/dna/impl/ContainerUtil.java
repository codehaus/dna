/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl;

import org.codehaus.dna.Active;
import org.codehaus.dna.Composable;
import org.codehaus.dna.Configurable;
import org.codehaus.dna.Configuration;
import org.codehaus.dna.ConfigurationException;
import org.codehaus.dna.LogEnabled;
import org.codehaus.dna.Logger;
import org.codehaus.dna.MissingResourceException;
import org.codehaus.dna.ResourceLocator;

/**
 * Utility class to make it easier to process a object
 * through its lifecycle stages.
 *
 * @version $Revision: 1.1 $ $Date: 2004-04-18 20:13:45 $
 */
public class ContainerUtil
{
    /**
     * Supply specified object with Logger if it implements the
     * LogEnabled interface.
     *
     * @param object the object to process
     * @param logger the logger. If null then the specified object must
     *        not implement LogEnabled.
     * @throws IllegalArgumentException if the object is LogEnabled and
     *         Logger is null
     */
    public static void enableLogging( final Object object,
                                      final Logger logger )
    {
        if( object instanceof LogEnabled )
        {
            if( null == logger )
            {
                final String message = "Null logger.";
                throw new IllegalArgumentException( message );
            }
            ( (LogEnabled)object ).enableLogging( logger );
        }
    }

    /**
     * Supply specified object with ResourceLocator if it implements the
     * Composable interface.
     *
     * @param object the object to process
     * @param locator the ResourceLocator. If null then the specified
     *        object must not implement Composable.
     * @throws IllegalArgumentException if the object is Composable
     *         and locator is null
     * @throws MissingResourceException if processing lifecycle stage on
     *         object throws exception
     */
    public static void compose( final Object object,
                                final ResourceLocator locator )
        throws MissingResourceException
    {
        if( object instanceof Composable )
        {
            if( null == locator )
            {
                final String message = "Null locator.";
                throw new IllegalArgumentException( message );
            }
            ( (Composable)object ).compose( locator );
        }
    }

    /**
     * Supply specified object with Configuration if it implements the
     * Configurable interface.
     *
     * @param object the object to process
     * @param configuration the Configuration. If null then the specified
     *        object must not implement Configurable.
     * @throws IllegalArgumentException if the object is Configurable
     *         and configuration is null
     * @throws ConfigurationException if processing lifecycle stage on
     *         object throws exception
     */
    public static void configure( final Object object,
                                  final Configuration configuration )
        throws ConfigurationException
    {
        if( object instanceof Configurable )
        {
            if( null == configuration )
            {
                final String message = "Null configuration.";
                throw new IllegalArgumentException( message );
            }
            ( (Configurable)object ).configure( configuration );
        }
    }

    /**
     * Initialize specified object if it implements the
     * Active interface.
     *
     * @param object the object to process
     * @throws Exception if processing lifecycle stage on
     *         object throws exception
     */
    public static void initialize( final Object object )
        throws Exception
    {
        if( object instanceof Active )
        {
            ( (Active)object ).initialize();
        }
    }

    /**
     * Dispose specified object if it implements the
     * Active interface.
     *
     * @param object the object to process
     * @throws Exception if processing lifecycle stage on
     *         object throws exception
     */
    public static void dispose( final Object object )
        throws Exception
    {
        if( object instanceof Active )
        {
            ( (Active)object ).dispose();
        }
    }
}
