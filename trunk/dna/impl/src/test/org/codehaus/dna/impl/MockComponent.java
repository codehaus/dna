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

class MockComponent
    implements LogEnabled, Composable, Configurable, Active
{
    private Logger m_logger;
    private ResourceLocator m_services;
    private Configuration m_configuration;
    private boolean m_initialized;
    private boolean m_disposed;

    public void enableLogging( Logger logger )
    {
        m_logger = logger;
    }

    public void compose( ResourceLocator locator )
        throws MissingResourceException
    {
        m_services = locator;
    }

    public void configure( Configuration configuration )
        throws ConfigurationException
    {
        m_configuration = configuration;
    }

    public void initialize()
        throws Exception
    {
        m_initialized = true;
    }

    public void dispose()
        throws Exception
    {
        m_disposed = true;
    }

    Logger getLogger()
    {
        return m_logger;
    }

    ResourceLocator getServices()
    {
        return m_services;
    }

    Configuration getConfiguration()
    {
        return m_configuration;
    }

    boolean isInitialized()
    {
        return m_initialized;
    }

    boolean isDisposed()
    {
        return m_disposed;
    }
}
