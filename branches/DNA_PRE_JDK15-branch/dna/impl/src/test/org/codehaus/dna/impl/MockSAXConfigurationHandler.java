/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl;

import org.codehaus.dna.Configuration;
import org.codehaus.dna.impl.SAXConfigurationHandler;

/**
 *
 * @author Peter Donald
 * @version $Revision: 1.1 $ $Date: 2004-04-18 20:13:44 $
 */
class MockSAXConfigurationHandler
    extends SAXConfigurationHandler
{
    public static final String REPLACEMENT = "INTERCEPT!";

    protected String processAttributeText( Configuration configuration,
                                           String name,
                                           String value )
    {
        return REPLACEMENT;
    }

    protected String processValueText( Configuration configuration,
                                       String value )
    {
        return REPLACEMENT;
    }
}
