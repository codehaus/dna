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
 * @version $Revision: 1.5 $ $Date: 2003-08-07 04:30:06 $
 */
public interface Parameters
{
    String[] getParameterNames();

    boolean isParameter( String name );

    String getParameter( String name )
        throws ParameterException;

    String getParameter( String name, String defaultValue );

    int getParameterAsInteger( String name )
        throws ParameterException;

    int getParameterAsInteger( String name, int defaultValue );

    long getParameterAsLong( String name )
        throws ParameterException;

    long getParameterAsLong( String name, long defaultValue );

    boolean getParameterAsBoolean( String name )
        throws ParameterException;

    boolean getParameterAsBoolean( String name, boolean defaultValue );

    float getParameterAsFloat( String name )
        throws ParameterException;

    float getParameterAsFloat( String name, float defaultValue );

    Parameters getChildParameters( String prefix );
}
