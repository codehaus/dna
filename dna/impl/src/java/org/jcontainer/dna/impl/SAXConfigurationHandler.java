/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.impl;

import java.util.ArrayList;
import java.util.List;
import org.jcontainer.dna.impl.DefaultConfiguration;
import org.jcontainer.dna.Configuration;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.4 $ $Date: 2003-08-13 09:01:38 $
 */
public class SAXConfigurationHandler
    extends DefaultHandler
{
    private static final String UNKNOWN = "Unknown";

    private final List m_elements = new ArrayList();
    private final List m_values = new ArrayList();

    private Configuration m_configuration;
    private Locator m_locator;

    public void setDocumentLocator( final Locator locator )
    {
        m_locator = locator;
    }

    public void clear()
    {
        m_elements.clear();
        m_values.clear();
        m_locator = null;
    }

    public Configuration getConfiguration()
    {
        return m_configuration;
    }

    public void startElement( final String uri,
                              final String localName,
                              final String qName,
                              final Attributes attributes )
        throws SAXException
    {
        final DefaultConfiguration configuration =
            new DefaultConfiguration( qName, getLocationDescription() );
        if( m_elements.size() > 0 )
        {
            final int index = m_elements.size() - 1;
            final DefaultConfiguration parent =
                (DefaultConfiguration)m_elements.get( index );
            parent.addChild( configuration );
        }
        m_elements.add( configuration );
    }

    public void endElement( final String uri,
                            final String localName,
                            final String qName )
        throws SAXException
    {
        final int index = m_elements.size() - 1;
        final DefaultConfiguration configuration =
            (DefaultConfiguration)m_elements.remove( index );
        if( index < m_values.size() )
        {
            final String value = m_values.remove( index ).toString();
            configuration.setValue( value );
        }
        m_configuration = configuration;
    }

    public void characters( final char[] ch,
                            final int start,
                            final int length )
        throws SAXException
    {
        final int index = m_elements.size() - 1;
        StringBuffer sb = null;
        if( index < m_values.size() )
        {
            sb = (StringBuffer)m_values.get( index );
        }
        if( null == sb )
        {
            sb = new StringBuffer();
            m_values.add( index, sb );
        }
        sb.append( ch, start, length );
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
            return UNKNOWN;
        }
        else
        {
            return m_locator.getSystemId() + ":" +
                m_locator.getLineNumber() + ":" +
                m_locator.getColumnNumber();
        }
    }
}
