/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.impl;

import org.jcontainer.dna.Active;
import org.jcontainer.dna.Composable;
import org.jcontainer.dna.Configurable;
import org.jcontainer.dna.Configuration;
import org.jcontainer.dna.ConfigurationException;
import org.jcontainer.dna.LogEnabled;
import org.jcontainer.dna.Logger;
import org.jcontainer.dna.MissingResourceException;
import org.jcontainer.dna.ResourceLocator;

/**
 * Utility class to make it easier to process a object
 * through its lifecycle stages.
 *
 * @version $Revision: 1.5 $ $Date: 2003-10-29 22:36:21 $
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
