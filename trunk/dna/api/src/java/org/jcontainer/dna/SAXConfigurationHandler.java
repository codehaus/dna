/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;

/**
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.1 $ $Date: 2003-07-28 07:57:49 $
 */
public class SAXConfigurationHandler
    extends DefaultHandler
{
    private Locator m_locator;

    public void setDocumentLocator( final Locator locator )
    {
        m_locator = locator;
    }

    public void warning( final SAXParseException spe )
        throws SAXException
    {
        throw spe;
    }

    public void error( final SAXParseException spe )
        throws SAXException
    {
        throw spe;
    }

    public void fatalError( final SAXParseException spe )
        throws SAXException
    {
        throw spe;
    }

    protected final String getLocationDescription()
    {
        if( null == m_locator )
        {
            return "Unknown";
        }
        else
        {
            return m_locator.getSystemId() + ":" +
                m_locator.getLineNumber() + ":" +
                m_locator.getColumnNumber();
        }
    }
}
