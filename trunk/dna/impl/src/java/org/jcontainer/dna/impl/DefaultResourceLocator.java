/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.impl;

import java.util.HashMap;
import java.util.Map;
import org.jcontainer.dna.MissingResourceException;
import org.jcontainer.dna.ResourceLocator;

/**
 *
 * @version $Revision: 1.1 $ $Date: 2003-07-25 11:36:49 $
 */
public class DefaultResourceLocator
    implements ResourceLocator
{
    private final ResourceLocator m_parent;
    private final Map m_resources = new HashMap();
    private boolean m_readOnly;

    public DefaultResourceLocator( final ResourceLocator parent )
    {
        m_parent = parent;
    }

    public Object lookup( final String key )
        throws MissingResourceException
    {
        final Object resource = m_resources.get( key );
        if( null != resource )
        {
            return resource;
        }
        else if( null != m_parent )
        {
            return m_parent.lookup( key );
        }
        else
        {
            final String message = "Unable to locate resource " + key + ".";
            throw new MissingResourceException( message, key );
        }
    }

    public boolean contains( final String key )
    {
        final Object resource = m_resources.get( key );
        if( null != resource )
        {
            return true;
        }
        else if( null != m_parent )
        {
            return m_parent.contains( key );
        }
        else
        {
            return false;
        }
    }

    public void makeReadOnly()
    {
        m_readOnly = true;
    }

    public void put( final String key,
                     final Object resource )
    {
        checkWriteable();
        m_resources.put( key, resource );
    }

    protected final ResourceLocator getParent()
    {
        return m_parent;
    }

    protected final boolean isReadOnly()
    {
        return m_readOnly;
    }

    protected final Map getResourceMap()
    {
        return m_resources;
    }

    protected final void checkWriteable()
        throws IllegalStateException
    {
        if( m_readOnly )
        {
            final String message =
                "ResourceLocator is read only and can not be modified";
            throw new IllegalStateException( message );
        }
    }
}
