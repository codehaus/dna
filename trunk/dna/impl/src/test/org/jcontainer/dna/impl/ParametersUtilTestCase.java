/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.impl;

import junit.framework.TestCase;
import java.util.Properties;
import org.jcontainer.dna.Parameters;

/**
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.1 $ $Date: 2003-10-26 04:32:25 $
 */
public class ParametersUtilTestCase
    extends TestCase
{
    public void testFromProperties()
        throws Exception
    {
        final Properties properties = new Properties();
        properties.setProperty( "key1", "value1" );
        properties.setProperty( "key2", "value2" );
        final Parameters parameters = ParametersUtil.fromProperties( properties );
        final String[] names = parameters.getParameterNames();
        assertEquals( "names.length", 2, names.length );
        assertEquals( "parameters('key1')", "value1",
                      parameters.getParameter( "key1" ) );
        assertEquals( "parameters('key2')", "value2",
                      parameters.getParameter( "key2" ) );
    }
}
