/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.impl;

import org.jcontainer.dna.Configuration;

/**
 *
 * @author Peter Donald
 * @version $Revision: 1.2 $ $Date: 2003-11-27 06:54:31 $
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
