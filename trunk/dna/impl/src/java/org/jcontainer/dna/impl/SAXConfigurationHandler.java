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
import org.jcontainer.dna.Configuration;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.9 $ $Date: 2003-08-30 02:08:35 $
 */
public class SAXConfigurationHandler
    extends DefaultHandler
{
    private static final String UNKNOWN = "";

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
        DefaultConfiguration parent = null;
        String path = ConfigurationUtil.ROOT_PATH;
        if( m_elements.size() > 0 )
        {
            final int index = m_elements.size() - 1;
            parent =
                (DefaultConfiguration)m_elements.get( index );
            path = parent.getPath() + ConfigurationUtil.PATH_SEPARATOR + qName;
        }
        final DefaultConfiguration configuration =
            new DefaultConfiguration( qName, getLocationDescription(), path );
        if( null != parent )
        {
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
            if( 0 != value.trim().length() )
            {
                if( 0 == configuration.getChildren().length )
                {
                    configuration.setValue( value );
                }
                else
                {
                    final String message =
                       "Mixed content (" + value.trim() +  ") " +
                       "not supported @ " + getLocationDescription();
                    throw new SAXException( message );
                }
            }
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
        else if( -1 == m_locator.getLineNumber() )
        {
            return m_locator.getSystemId();
        }
        else if( -1 == m_locator.getColumnNumber() )
        {
            return m_locator.getSystemId() + ":" +
                m_locator.getLineNumber();
        }
        else
        {
            return m_locator.getSystemId() + ':' +
                m_locator.getLineNumber() + ':' +
                m_locator.getColumnNumber();
        }
    }
}