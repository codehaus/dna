/*
 * Copyright (C) The DNA Group. All rights reserved.
 *
 * This software is published under the terms of the DNA
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.dna.Configuration;
import org.codehaus.dna.ConfigurationException;

/**
 * In memory Configuration implementation.
 * The developer should create the DefaultConfiguration,
 * associate value, attributes and/or child elements configuration
 * and then invoke {@link #makeReadOnly()} before passing the
 * Configuration to the client component.
 *
 * @version $Revision: 1.2 $ $Date: 2004-05-01 09:51:48 $
 */
public class DefaultConfiguration
    extends AbstractFreezable
    implements Configuration
{
    /**
     * Postfix indicating that location is generated.
     */
    private static final String AUTOGEN_POSTFIX = "<autogen>";

    /**
     * The constant that boolean values must equal to be "true".
     */
    private static final String TRUE_STRING = "true";

    /**
     * Constant for empty String array to reduce
     * creation cost for empty array.
     */
    private static final String[] EMPTY_STRING_ARRAY = new String[ 0 ];

    /**
     * Constant for empty configuration array to reduce
     * creation cost for empty array.
     */
    private static final Configuration[] EMPTY_CONFIG_ARRAY = new Configuration[ 0 ];

    /**
     * The name of configuration element.
     */
    private final String m_name;

    /**
     * The location of configuration element in source.
     * May be empty string if unknown.
     */
    private final String m_location;

    /**
     * The path of configuration element in document.
     * May be empty string if unknown.
     */
    private final String m_path;

    /**
     * The attributes defined by configuration (May be null).
     */
    private Map m_attributes;

    /**
     * The child elements defined by
     * configuration (May be null). If
     * {@link #m_value} not null then
     * m_children must be null.
     */
    private List m_children;

    /**
     * The value contained in configuration
     * (May be null). If {@link #m_children} not
     * null then m_value must be null.
     */
    private String m_value;

    /**
     * Create a DefaultConfiguration instance.
     *
     * @param name the name of configuration element
     * @param location the location of configuration element in source
     * @param path the path of configuration element in document
     */
    public DefaultConfiguration( final String name,
                                 final String location,
                                 final String path )
    {
        if( null == name )
        {
            throw new NullPointerException( "name" );
        }
        if( null == path )
        {
            throw new NullPointerException( "path" );
        }
        if( null == location )
        {
            throw new NullPointerException( "location" );
        }
        m_name = name;
        m_path = path;
        m_location = location;
    }

    /**
     * Return the name of the configuration element.
     *
     * @return the name of the configuration element.
     */
    public String getName()
    {
        return m_name;
    }

    /**
     * Return the path to the configuration element.
     * The path should be in the xpath form but may
     * be the empty string if unabel to determine path.
     *
     * @return the path to the configuration element.
     */
    public final String getPath()
    {
        return m_path;
    }

    /**
     * Return the location of configuration element.
     * Usually of the form "uri[:line number[:column number]]"
     * if possible. ie "file:myFile.xml:80:2". However the line
     * number and column number may be elided if unavailable.
     *
     * @return the location of configuration element.
     */
    public String getLocation()
    {
        return m_location;
    }

    /**
     * Return an array of all the child elements.
     *
     * @return an array of all the child elements.
     */
    public Configuration[] getChildren()
    {
        final List childList = getChildList();
        if( null == childList )
        {
            return EMPTY_CONFIG_ARRAY;
        }
        else
        {
            return (Configuration[])childList.toArray( new Configuration[ childList.size() ] );
        }
    }

    /**
     * Return an array of all the child elements with specified name.
     *
     * @param name the name of child configuration objects
     * @return an array of all the child elements with specified name.
     */
    public Configuration[] getChildren( final String name )
    {
        if( null == name )
        {
            throw new NullPointerException( "name" );
        }
        final List children = getChildList();
        if( null == children )
        {
            return EMPTY_CONFIG_ARRAY;
        }
        else
        {
            final ArrayList results = new ArrayList();
            final int count = children.size();
            for( int i = 0; i < count; i++ )
            {
                final Configuration child = (Configuration)children.get( i );
                if( child.getName().equals( name ) )
                {
                    results.add( child );
                }
            }
            return (Configuration[])results.toArray( new Configuration[ results.size() ] );
        }
    }

    /**
     * Return a child Configuration element with specified name.
     * If no such element exists an element will be autocreated.
     *
     * @param name the name of child configuration object
     * @return a child Configuration element with specified name.
     */
    public Configuration getChild( final String name )
    {
        return getChild( name, true );
    }

    /**
     * Return a child Configuration element with specified name.
     * If no such element exists and createChild is true then an
     * element will be autocreated otherwise null will be returned.
     *
     * @param name the name of child configuration object
     * @param createChild true if child should be created if it does not exist
     * @return a child Configuration element with specified name.
     */
    public Configuration getChild( final String name,
                                   final boolean createChild )
    {
        if( null == name )
        {
            throw new NullPointerException( "name" );
        }
        final List children = getChildList();
        if( null != children )
        {
            final int count = children.size();
            for( int i = 0; i < count; i++ )
            {
                final Configuration child = (Configuration)children.get( i );
                if( child.getName().equals( name ) )
                {
                    return child;
                }
            }
        }
        if( createChild )
        {
            final String path = getPath() + ConfigurationUtil.PATH_SEPARATOR + getName();
            return new DefaultConfiguration( name, generateLocation(), path );
        }
        else
        {
            return null;
        }

    }

    /**
     * Return text value of element.
     *
     * @return the value
     * @throws ConfigurationException if no value in element
     */
    public String getValue()
        throws ConfigurationException
    {
        if( null != m_value )
        {
            return m_value;
        }
        else
        {
            final String message = "No value specified";
            throw new ConfigurationException( message, getPath(), getLocation() );
        }
    }

    /**
     * Return text value of element.
     * Use specified default if no value in element.
     *
     * @param defaultValue the default value
     * @return the value
     */
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

    /**
     * Return text value of element as a boolean.
     *
     * @return the value
     * @throws ConfigurationException if no value in element
     *         or value can not be converted to correct type
     */
    public boolean getValueAsBoolean()
        throws ConfigurationException
    {
        return getValue().equals( "true" );
    }

    /**
     * Return text value of element as a boolean.
     * Use specified default if no value in element or
     * value can not be converted to correct type.
     *
     * @param defaultValue the default value
     * @return the value
     */
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

    /**
     * Return text value of element as an integer.
     *
     * @return the value
     * @throws ConfigurationException if no value in element
     *         or value can not be converted to correct type
     */
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
            throw new ConfigurationException( message, getPath(), getLocation(), nfe );
        }
    }

    /**
     * Return text value of element as an integer.
     * Use specified default if no value in element or
     * value can not be converted to correct type.
     *
     * @param defaultValue the default value
     * @return the value
     */
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

    /**
     * Return text value of element as a long.
     *
     * @return the value
     * @throws ConfigurationException if no value in element
     *         or value can not be converted to correct type
     */
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
            throw new ConfigurationException( message, getPath(), getLocation(), nfe );
        }
    }

    /**
     * Return text value of element as a long.
     * Use specified default if no value in element or
     * value can not be converted to correct type.
     *
     * @param defaultValue the default value
     * @return the value
     */
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

    /**
     * Return text value of element as a float.
     *
     * @return the value
     * @throws ConfigurationException if no value in element
     *         or value can not be converted to correct type
     */
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
            throw new ConfigurationException( message, getPath(), getLocation(), nfe );
        }
    }

    /**
     * Return text value of element as a float.
     * Use specified default if no value in element or
     * value can not be converted to correct type.
     *
     * @param defaultValue the default value
     * @return the value
     */
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

    /**
     * Return an array of all the attribute names.
     *
     * @return an array of all the attribute names.
     */
    public String[] getAttributeNames()
    {
        final Map attributeMap = getAttributeMap();
        if( null == attributeMap )
        {
            return EMPTY_STRING_ARRAY;
        }
        else
        {
            final Set keys = attributeMap.keySet();
            return (String[])attributeMap.keySet().toArray( new String[ keys.size() ] );
        }
    }

    /**
     * Return attribute value with specified name.
     *
     * @param name the attribute name
     * @return the attribute value
     * @throws ConfigurationException if no attribute with
     *         specified name
     */
    public String getAttribute( final String name )
        throws ConfigurationException
    {
        final String value = doGetAttribute( name );
        if( null != value )
        {
            return value;
        }
        else
        {
            final String message =
                "Attribute named " + name + " not specified.";
            throw new ConfigurationException( message, getPath(), getLocation() );
        }
    }

    /**
     * Return attribute value with specified name.
     * If no attribute with specified name then return
     * default value.
     *
     * @param name the attribute name
     * @param defaultValue the default value
     * @return the attribute value
     */
    public String getAttribute( final String name,
                                final String defaultValue )
    {
        final String value = doGetAttribute( name );
        if( null != value )
        {
            return value;
        }
        else
        {
            return defaultValue;
        }
    }

    /**
     * Return attribute value with specified name or null
     * if no such attribute.
     *
     * @param name the attribute name
     * @return the attribute value
     */
    private String doGetAttribute( final String name )
    {
        if( null == name )
        {
            throw new NullPointerException( "name" );
        }
        final Map attributeMap = getAttributeMap();
        if( null != attributeMap )
        {
            final String value = (String)attributeMap.get( name );
            if( null != value )
            {
                return value;
            }
        }
        return null;
    }

    /**
     * Return attribute value with specified name as a boolean.
     *
     * @param name the attribute name
     * @return the attribute value
     * @throws ConfigurationException if no attribute with
     *         specified name or attribute can not be converted
     *         to correct type
     */
    public boolean getAttributeAsBoolean( final String name )
        throws ConfigurationException
    {
        return getAttribute( name ).equals( TRUE_STRING );
    }

    /**
     * Return attribute value with specified name as a boolean.
     * If no attribute with specified name or attribute can
     * not be converted to correct type then return
     * default value.
     *
     * @param name the attribute name
     * @param defaultValue the default value
     * @return the attribute value
     */
    public boolean getAttributeAsBoolean( final String name,
                                          final boolean defaultValue )
    {
        final String value = getAttribute( name, null );
        if( null != value )
        {
            return value.equals( TRUE_STRING );
        }
        return defaultValue;
    }

    /**
     * Return attribute value with specified name as an integer.
     *
     * @param name the attribute name
     * @return the attribute value
     * @throws ConfigurationException if no attribute with
     *         specified name or attribute can not be converted
     *         to correct type
     */
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
            throw new ConfigurationException( message, getPath(), getLocation() );
        }
    }

    /**
     * Return attribute value with specified name as an integer.
     * If no attribute with specified name or attribute can
     * not be converted to correct type then return
     * default value.
     *
     * @param name the attribute name
     * @param defaultValue the default value
     * @return the attribute value
     */
    public int getAttributeAsInteger( final String name,
                                      final int defaultValue )
    {
        final String value = getAttribute( name, null );
        if( null != value )
        {
            try
            {
                return Integer.parseInt( value );
            }
            catch( final NumberFormatException nfe )
            {
                //Fall through to return defaultValue
            }
        }
        return defaultValue;
    }

    /**
     * Return attribute value with specified name as a long.
     *
     * @param name the attribute name
     * @return the attribute value
     * @throws ConfigurationException if no attribute with
     *         specified name or attribute can not be converted
     *         to correct type
     */
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
            throw new ConfigurationException( message, getPath(), getLocation() );
        }
    }

    /**
     * Return attribute value with specified name as a long.
     * If no attribute with specified name or attribute can
     * not be converted to correct type then return
     * default value.
     *
     * @param name the attribute name
     * @param defaultValue the default value
     * @return the attribute value
     */
    public long getAttributeAsLong( final String name,
                                    final long defaultValue )
    {
        final String value = getAttribute( name, null );
        if( null != value )
        {
            try
            {
                return Long.parseLong( value );
            }
            catch( final NumberFormatException nfe )
            {
                //Fall through to return defaultValue
            }
        }
        return defaultValue;
    }

    /**
     * Return attribute value with specified name as afloat.
     *
     * @param name the attribute name
     * @return the attribute value
     * @throws ConfigurationException if no attribute with
     *         specified name or attribute can not be converted
     *         to correct type
     */
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
            throw new ConfigurationException( message, getPath(), getLocation() );
        }
    }

    /**
     * Return attribute value with specified name as a float.
     * If no attribute with specified name or attribute can
     * not be converted to correct type then return
     * default value.
     *
     * @param name the attribute name
     * @param defaultValue the default value
     * @return the attribute value
     */
    public float getAttributeAsFloat( final String name,
                                      final float defaultValue )
    {
        final String value = getAttribute( name, null );
        if( null != value )
        {
            try
            {
                return Float.parseFloat( value );
            }
            catch( final NumberFormatException nfe )
            {
                //Fall through to return defaultValue
            }
        }
        return defaultValue;
    }

    /**
     * Mark the configuration and child configurations as read only.
     */
    public void makeReadOnly()
    {
        super.makeReadOnly();
        final List children = getChildList();
        if( null != children )
        {
            final int count = children.size();
            for( int i = 0; i < count; i++ )
            {
                final Configuration configuration = (Configuration)children.get( i );
                if( configuration instanceof Freezable )
                {
                    ( (Freezable)configuration ).makeReadOnly();
                }
            }
        }
    }

    /**
     * Set an attribute of configuration.
     *
     * @param key the attribute key
     * @param value the attribute value
     */
    public void setAttribute( final String key,
                              final String value )
    {
        if( null == key )
        {
            throw new NullPointerException( "key" );
        }
        if( null == value )
        {
            throw new NullPointerException( "value" );
        }
        checkWriteable();
        if( null == m_attributes )
        {
            m_attributes = new HashMap();
        }
        m_attributes.put( key, value );
    }

    /**
     * Add a child configuration element.
     *
     * @param configuration the child configuration element.
     */
    public void addChild( final Configuration configuration )
    {
        if( null == configuration )
        {
            throw new NullPointerException( "configuration" );
        }
        checkWriteable();
        if( null != m_value )
        {
            throwMixedContentException();
        }
        if( null == m_children )
        {
            m_children = new ArrayList();
        }
        m_children.add( configuration );
    }

    /**
     * Set the value of the configuration element.
     *
     * @param value the value of the configuration element.
     */
    public void setValue( final String value )
    {
        if( null == value )
        {
            throw new NullPointerException( "value" );
        }
        checkWriteable();
        final List children = getChildList();
        if( null != children && 0 != children.size() )
        {
            throwMixedContentException();
        }
        m_value = value;
    }

    /**
     * Overide toString to improve ability to debug implementation.
     *
     * @return string representation of object
     */
    public String toString()
    {
        final StringBuffer sb = new StringBuffer();
        sb.append( "[Configuration name='" );
        sb.append( getName() );
        sb.append( "'" );
        if( null != m_attributes )
        {
            sb.append( " attributes=" );
            sb.append( m_attributes );
        }
        sb.append( "]" );
        return sb.toString();
    }

    /**
     * Return the list of child configuration objects.
     *
     * @return the list of child configuration objects.
     */
    protected final List getChildList()
    {
        return m_children;
    }

    /**
     * Return the backing map for attributes.
     *
     * @return the backing map for attributes.
     */
    protected final Map getAttributeMap()
    {
        return m_attributes;
    }

    /**
     * Generate a location string that postfixes
     * autogenerated marker.
     *
     * @return a autogenerated location string
     */
    protected final String generateLocation()
    {
        final String location = getLocation();
        if( !location.endsWith( AUTOGEN_POSTFIX ) )
        {
            return location + AUTOGEN_POSTFIX;
        }
        else
        {
            return location;
        }
    }

    /**
     * Throw an IllegalStateException warning about
     * mixed content.
     */
    protected final void throwMixedContentException()
    {
        final String message =
            "Configuration objects do not support Mixed content. " +
            "Configuration elements should not have both a value and " +
            "child elements.";
        throw new IllegalStateException( message );
    }
}
