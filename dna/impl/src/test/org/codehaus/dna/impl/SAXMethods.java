/*
 * Copyright (C) The DNA Group. All rights reserved.
 *
 * This software is published under the terms of the DNA
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl;

import java.lang.reflect.Method;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;

class SAXMethods
{
    static final Method START_DOCUMENT;
    static final Method END_DOCUMENT;
    static final Method START_ELEMENT;
    static final Method END_ELEMENT;
    static final Method CHARACTERS;

    static
    {
        try
        {
            START_DOCUMENT =
                ContentHandler.class.getMethod( "startDocument", new Class[ 0 ] );
            END_DOCUMENT =
                ContentHandler.class.getMethod( "endDocument", new Class[ 0 ] );
            START_ELEMENT =
                ContentHandler.class.getMethod( "startElement",
                                                new Class[]{String.class, String.class, String.class, Attributes.class} );
            END_ELEMENT =
                ContentHandler.class.getMethod( "endElement",
                                                new Class[]{String.class, String.class, String.class} );
            CHARACTERS =
                ContentHandler.class.getMethod( "characters",
                                                new Class[]{char[].class, Integer.TYPE, Integer.TYPE} );
        }
        catch( Exception e )
        {
            e.printStackTrace();
            throw new IllegalStateException( "Problem getting sax methods: " + e );
        }
    }
}
