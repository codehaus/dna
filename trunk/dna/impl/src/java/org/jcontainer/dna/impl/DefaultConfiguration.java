/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.impl;

import org.jcontainer.dna.Configuration;
import org.jcontainer.dna.ConfigurationException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @version $Revision: 1.4 $ $Date: 2003-07-27 10:33:20 $
 */
public class DefaultConfiguration
    implements Configuration, Freezable
{
    private static final String TRUE_STRING = "true";

    private final String m_name;
    private final String m_location;
    private final Map m_attributes = new HashMap();
    private final List m_children = new ArrayList();
    private String m_value;
    private boolean m_readOnly;

    public DefaultConfiguration( final String name,
                                 final String location )
    {
        if( null == name )
        {
            throw new NullPointerException( "name" );
        }
        if( null == location )
        {
            throw new NullPointerException( "location" );
        }
        m_name = name;
        m_location = location;
    }

    public String getName()
    {
        return m_name;
    }

    public String getLocation()
    {
        return m_location;
    }

    public Configuration[] getChildren()
    {
        return (Configuration[])m_children.toArray( new Configuration[ m_children.size() ] );
    }

    public Configuration[] getChildren( final String name )
    {
        final ArrayList results = new ArrayList();
        final int count = m_children.size();
        for( int i = 0; i < count; i++ )
        {
            final Configuration child = (Configuration)m_children.get( i );
            if( child.getName().equals( name ) )
            {
                results.add( child );
            }
        }
        return (Configuration[])results.toArray( new Configuration[ results.size() ] );
    }

    public Configuration getChild( final String name )
    {
        return getChild( name, true );
    }

    public Configuration getChild( final String name,
                                   final boolean createChild )
    {
        final int count = m_children.size();
        for( int i = 0; i < count; i++ )
        {
            final Configuration child = (Configuration)m_children.get( i );
            if( child.getName().equals( name ) )
            {
                return child;
            }
        }
        if( createChild )
        {
            return new DefaultConfiguration( name, "config:autogen" );
        }
        else
        {
            return null;
        }

    }

    public String getValue()
        throws ConfigurationException
    {
        if( null != m_value )
        {
            return m_value;
        }
        else
        {
            throw new ConfigurationException( "No value specified", getLocation() );
        }
    }

    public String getValue( final String defaultValue )
    {
        if( null != m_value )
        {
            return m_value;
        }
        else
        {
            return defaultValue;
        }
    }

    public boolean getValueAsBoolean()
        throws ConfigurationException
    {
        return getValue().equals( "true" );
    }

    public boolean getValueAsBoolean( final boolean defaultValue )
    {
        if( null == m_value )
        {
            return defaultValue;
        }
        else
        {
            return m_value.equals( TRUE_STRING );
        }
    }

    public int getValueAsInteger()
        throws ConfigurationException
    {
        try
        {
            return Integer.parseInt( getValue() );
        }
        catch( final NumberFormatException nfe )
        {
            final String message =
                "Unable to parse " + getValue() + " as an integer";
            throw new ConfigurationException( message, nfe, getLocation() );
        }
    }

    public int getValueAsInteger( final int defaultValue )
    {
        if( null == m_value )
        {
            return defaultValue;
        }
        else
        {
            try
            {
                return Integer.parseInt( m_value );
            }
            catch( final NumberFormatException nfe )
            {
                return defaultValue;
            }
        }
    }

    public long getValueAsLong()
        throws ConfigurationException
    {
        try
        {
            return Long.parseLong( getValue() );
        }
        catch( final NumberFormatException nfe )
        {
            final String message =
                "Unable to parse " + getValue() + " as a Long";
            throw new ConfigurationException( message, nfe, getLocation() );
        }
    }

    public long getValueAsLong( final long defaultValue )
    {
        if( null == m_value )
        {
            return defaultValue;
        }
        else
        {
            try
            {
                return Long.parseLong( m_value );
            }
            catch( final NumberFormatException nfe )
            {
                return defaultValue;
            }
        }
    }

    public float getValueAsFloat()
        throws ConfigurationException
    {
        try
        {
            return Float.parseFloat( getValue() );
        }
        catch( final NumberFormatException nfe )
        {
            final String message =
                "Unable to parse " + getValue() + " as a Long";
            throw new ConfigurationException( message, nfe, getLocation() );
        }
    }

    public float getValueAsFloat( final float defaultValue )
    {
        if( null == m_value )
        {
            return defaultValue;
        }
        else
        {
            try
            {
                return Float.parseFloat( m_value );
            }
            catch( final NumberFormatException nfe )
            {
                return defaultValue;
            }
        }
    }

    public String[] getAttributeNames()
    {
        final Set keys = m_attributes.keySet();
        return (String[])m_attributes.keySet().toArray( new String[ keys.size() ] );
    }

    public String getAttribute( final String name )
        throws ConfigurationException
    {
        final String value = (String)m_attributes.get( name );
        if( null != value )
        {
            return value;
        }
        else
        {
            final String message =
                "Attribute named " + name + " not specified.";
            throw new ConfigurationException( message, getLocation() );
        }
    }

    public String getAttribute( final String name,
                                final String defaultValue )
    {
        final String value = (String)m_attributes.get( name );
        if( null != value )
        {
            return value;
        }
        else
        {
            return defaultValue;
        }
    }

    public boolean getAttributeAsBoolean( final String name )
        throws ConfigurationException
    {
        return getAttribute( name ).equals( TRUE_STRING );
    }

    public boolean getAttributeAsBoolean( final String name,
                                          final boolean defaultValue )
    {
        final String value = (String)m_attributes.get( name );
        if( null == value )
        {
            return defaultValue;
        }
        else
        {
            return value.equals( TRUE_STRING );
        }
    }

    public int getAttributeAsInteger( final String name )
        throws ConfigurationException
    {
        final String value = getAttribute( name );
        try
        {
            return Integer.parseInt( value );
        }
        catch( final NumberFormatException nfe )
        {
            final String message =
                "Unable to parse " + value + " as an Integer.";
            throw new ConfigurationException( message, getLocation() );
        }
    }

    public int getAttributeAsInteger( final String name,
                                      final int defaultValue )
    {
        final String value = (String)m_attributes.get( name );
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

    public long getAttributeAsLong( final String name )
        throws ConfigurationException
    {
        final String value = getAttribute( name );
        try
        {
            return Long.parseLong( value );
        }
        catch( final NumberFormatException nfe )
        {
            final String message =
                "Unable to parse " + value + " as a Long.";
            throw new ConfigurationException( message, getLocation() );
        }
    }

    public long getAttributeAsLong( final String name,
                                    final long defaultValue )
    {
        final String value = (String)m_attributes.get( name );
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

    public float getAttributeAsFloat( final String name )
        throws ConfigurationException
    {
        final String value = getAttribute( name );
        try
        {
            return Float.parseFloat( value );
        }
        catch( final NumberFormatException nfe )
        {
            final String message =
                "Unable to parse " + value + " as a Float.";
            throw new ConfigurationException( message, getLocation() );
        }
    }

    public float getAttributeAsFloat( final String name,
                                      final float defaultValue )
    {
        final String value = (String)m_attributes.get( name );
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

    public void makeReadOnly()
    {
        m_readOnly = true;
        final int count = m_children.size();
        for( int i = 0; i < count; i++ )
        {
            final Configuration configuration = (Configuration)m_children.get( i );
            if( configuration instanceof Freezable )
            {
                ( (Freezable)configuration ).makeReadOnly();
            }
        }
    }

    public void setAttribute( final String key, final String value )
    {
        checkWriteable();
        m_attributes.put( key, value );
    }

    public void addChild( final Configuration configuration )
    {
        checkWriteable();
        m_children.add( configuration );
    }

    public void setValue( final String value )
    {
        checkWriteable();
        m_value = value;
    }

    protected final void checkWriteable()
        throws IllegalStateException
    {
        if( m_readOnly )
        {
            final String message =
                "Configuration is read only and can not be modified.";
            throw new IllegalStateException( message );
        }
    }
}
