/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import org.jcontainer.dna.ParameterException;
import org.jcontainer.dna.Parameters;

/**
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.5 $ $Date: 2003-09-07 23:49:33 $
 */
public class DefaultParameters
    extends AbstractFreezable
    implements Parameters
{
    private static final String SEPARATOR = ".";
    private static final String EMPTY_PREFIX = "";

    private final Properties m_parameters = new Properties();
    private final Set m_children = new HashSet();
    private final String m_prefix;

    public DefaultParameters()
    {
        this( EMPTY_PREFIX );
    }

    public DefaultParameters( final String prefix )
    {
        if( null == prefix )
        {
            throw new NullPointerException( "prefix" );
        }
        m_prefix = prefix;
    }

    public String[] getParameterNames()
    {
        final Set set = m_parameters.keySet();
        return (String[])set.toArray( new String[ set.size() ] );
    }

    public boolean isParameter( final String name )
    {
        return m_parameters.containsKey( name );
    }

    public String getParameter( final String name )
        throws ParameterException
    {
        final String property = m_parameters.getProperty( name );
        if( null == property )
        {
            final String message =
                "Unable to locate parameter named " + name;
            throw new ParameterException( message, name );
        }
        return property;
    }

    public String getParameter( final String name,
                                final String defaultValue )
    {
        final String fullname = m_prefix + name;
        return m_parameters.getProperty( fullname, defaultValue );
    }

    public boolean getParameterAsBoolean( final String name )
        throws ParameterException
    {
        return getParameter( name ).equals( "true" );
    }

    public boolean getParameterAsBoolean( final String name,
                                          final boolean defaultValue )
    {
        final String value = getParameter( name, null );
        if( null == value )
        {
            return defaultValue;
        }
        else
        {
            return value.equals( "true" );
        }
    }

    public int getParameterAsInteger( final String name )
        throws ParameterException
    {
        final String value = getParameter( name );
        try
        {
            return Integer.parseInt( value );
        }
        catch( final NumberFormatException nfe )
        {
            final String message =
                "Unable to parse parameter named " + name +
                " with value '" + value + "'";
            throw new ParameterException( message, name, nfe );
        }
    }

    public int getParameterAsInteger( final String name,
                                      final int defaultValue )
    {
        final String value = getParameter( name, null );
        if( null == value )
        {
            return defaultValue;
        }
        else
        {
            try
            {
                return Integer.parseInt( value );
            }
            catch( final NumberFormatException nfe )
            {
                return defaultValue;
            }
        }
    }

    public long getParameterAsLong( final String name )
        throws ParameterException
    {
        final String value = getParameter( name );
        try
        {
            return Long.parseLong( value );
        }
        catch( final NumberFormatException nfe )
        {
            final String message =
                "Unable to parse parameter named " + name +
                " with value '" + value + "'";
            throw new ParameterException( message, name, nfe );
        }
    }

    public long getParameterAsLong( final String name,
                                    final long defaultValue )
    {
        final String value = getParameter( name, null );
        if( null == value )
        {
            return defaultValue;
        }
        else
        {
            try
            {
                return Long.parseLong( value );
            }
            catch( final NumberFormatException nfe )
            {
                return defaultValue;
            }
        }
    }

    public float getParameterAsFloat( final String name )
        throws ParameterException
    {
        final String value = getParameter( name );
        try
        {
            return Float.parseFloat( value );
        }
        catch( final NumberFormatException nfe )
        {
            final String message =
                "Unable to parse parameter named " + name +
                " with value '" + value + "'";
            throw new ParameterException( message, name, nfe );
        }
    }

    public float getParameterAsFloat( final String name,
                                      final float defaultValue )
    {
        final String value = getParameter( name, null );
        if( null == value )
        {
            return defaultValue;
        }
        else
        {
            try
            {
                return Float.parseFloat( value );
            }
            catch( final NumberFormatException nfe )
            {
                return defaultValue;
            }
        }
    }

    public Parameters getChildParameters( final String prefix )
    {
        final String prefixAndDot = prefix + SEPARATOR;
        final int length = prefix.length() + 1;
        final DefaultParameters parameters = new DefaultParameters( m_prefix + SEPARATOR + prefix );
        final Iterator iterator = m_parameters.keySet().iterator();
        while( iterator.hasNext() )
        {
            final String key = (String)iterator.next();
            if( key.startsWith( prefixAndDot ) )
            {
                final String value = getParameter( key, null );
                final String newKey = key.substring( length );
                parameters.setParameter( newKey, value );
            }
        }

        parameters.makeReadOnly();
        m_children.add( parameters );
        return parameters;
    }

    public void makeReadOnly()
    {
        super.makeReadOnly();
        final Iterator iterator = m_children.iterator();
        while( iterator.hasNext() )
        {
            final Object child = iterator.next();
            if( child instanceof Freezable )
            {
                ((Freezable)child).makeReadOnly();
            }
        }
    }

    public void setParameter( final String name, final String value )
    {
        checkWriteable();
        m_parameters.setProperty( name, value );
    }

    protected final Properties getParameters()
    {
        return m_parameters;
    }

    protected final String getPrefix()
    {
        return m_prefix;
    }
}
