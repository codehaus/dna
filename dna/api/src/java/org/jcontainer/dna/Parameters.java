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
 * @version $Revision: 1.1 $ $Date: 2003-07-25 11:34:35 $
 */
public interface Parameters
{
    String[] getParameterNames();

    String getParameter( String name )
        throws ConfigurationException;

    String getParameter( String name, String defaultValue );

    int getParameterAsInteger( String name )
        throws ConfigurationException;

    int getParameterAsInteger( String name, int defaultValue );

    long getParameterAsLong( String name )
        throws ConfigurationException;

    long getParameterAsLong( String name, long defaultValue );

    boolean getParameterAsBoolean( String name )
        throws ConfigurationException;

    boolean getParameterAsBoolean( String name, boolean defaultValue );

    float getParameterAsFloat( String name )
        throws ConfigurationException;

    float getParameterAsFloat( String name, float defaultValue );

    Parameters getChildParameters( String prefix );
}
