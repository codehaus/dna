/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.impl;

import org.jcontainer.dna.Configuration;
import org.xml.sax.helpers.AttributesImpl;

class MockSAXConfigurationSerializer
    extends SAXConfigurationSerializer
{
    static final AttributesImpl ATTRIBUTES = new AttributesImpl();

    AttributesImpl serializeAttributes( Configuration configuration )
    {
        return ATTRIBUTES;
    }
}
