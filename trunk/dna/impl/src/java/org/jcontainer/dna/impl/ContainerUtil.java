/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.impl;

import org.jcontainer.dna.Active;
import org.jcontainer.dna.Composable;
import org.jcontainer.dna.Configurable;
import org.jcontainer.dna.Configuration;
import org.jcontainer.dna.ConfigurationException;
import org.jcontainer.dna.Contextualizable;
import org.jcontainer.dna.LogEnabled;
import org.jcontainer.dna.Logger;
import org.jcontainer.dna.MissingResourceException;
import org.jcontainer.dna.ParameterException;
import org.jcontainer.dna.Parameterizable;
import org.jcontainer.dna.Parameters;
import org.jcontainer.dna.ResourceLocator;

/**
 *
 * @version $Revision: 1.1 $ $Date: 2003-07-27 10:44:19 $
 */
public class ContainerUtil
{
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

    public static void contextualize( final Object object,
                                      final ResourceLocator locator )
        throws MissingResourceException
    {
        if( object instanceof Contextualizable )
        {
            if( null == locator )
            {
                final String message = "Null locator.";
                throw new IllegalArgumentException( message );
            }
            ( (Contextualizable)object ).contextualize( locator );
        }
    }

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

    public static void parameterize( final Object object,
                                     final Parameters parameters )
        throws ParameterException
    {
        if( object instanceof Parameterizable )
        {
            if( null == parameters )
            {
                final String message = "Null parameters.";
                throw new IllegalArgumentException( message );
            }
            ( (Parameterizable)object ).parameterize( parameters );
        }
    }

    public static void initialize( final Object object )
        throws Exception
    {
        if( object instanceof Active )
        {
            ( (Active)object ).initialize();
        }
    }

    public static void dispose( final Object object )
        throws Exception
    {
        if( object instanceof Active )
        {
            ( (Active)object ).dispose();
        }
    }
}
