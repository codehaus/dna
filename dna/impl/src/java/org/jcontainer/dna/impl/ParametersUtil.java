/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.impl;

import java.util.Properties;
import java.util.Iterator;
import org.jcontainer.dna.Parameters;

/**
 * Class containing utility methods to work with Parameters
 * objects.
 *
 * @version $Revision: 1.1 $ $Date: 2003-10-26 04:32:25 $
 */
public class ParametersUtil
{
    /**
     * Create a Parameters object from aproperties object.
     *
     * @param properties the properties object
     * @return the new Parameters object
     */
    public static Parameters fromProperties( final Properties properties )
    {
        final DefaultParameters parameters = new DefaultParameters();
        final Iterator iterator = properties.keySet().iterator();
        while( iterator.hasNext() )
        {
            final String name = (String)iterator.next();
            final String value = properties.getProperty( name );
            parameters.setParameter( name, value );
        }
        return parameters;
    }
}
