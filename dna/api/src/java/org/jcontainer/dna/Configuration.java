/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna;

/**
 *
 * @version $Revision: 1.2 $ $Date: 2003-09-02 03:47:34 $
 */
public interface Configuration
{
    String getName();

    String getPath();

    String getLocation();

    Configuration[] getChildren();

    Configuration[] getChildren( String name );

    Configuration getChild( String name );

    Configuration getChild( String name, boolean createChild );

    String getValue()
        throws ConfigurationException;

    String getValue( String defaultValue );

    int getValueAsInteger( int defaultValue );

    int getValueAsInteger()
        throws ConfigurationException;

    long getValueAsLong( long defaultValue );

    long getValueAsLong()
        throws ConfigurationException;

    boolean getValueAsBoolean( boolean defaultValue );

    boolean getValueAsBoolean()
        throws ConfigurationException;

    float getValueAsFloat( float defaultValue );

    float getValueAsFloat()
        throws ConfigurationException;

    String[] getAttributeNames();

    String getAttribute( String name )
        throws ConfigurationException;

    String getAttribute( String name, String defaultValue );

    int getAttributeAsInteger( String name, int defaultValue );

    int getAttributeAsInteger( String name )
        throws ConfigurationException;

    long getAttributeAsLong( String name, long defaultValue );

    long getAttributeAsLong( String name )
        throws ConfigurationException;

    boolean getAttributeAsBoolean( String name, boolean defaultValue );

    boolean getAttributeAsBoolean( String name )
        throws ConfigurationException;

    float getAttributeAsFloat( String name, float defaultValue );

    float getAttributeAsFloat( String name )
        throws ConfigurationException;
}
