/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
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
 * The SAXConfigurationHandler builds a Configuration tree
 * from SAX events.
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.17 $ $Date: 2003-09-23 10:14:46 $
 */
public class SAXConfigurationHandler
    extends DefaultHandler
{
    /**
     * Constant to indicate location of
     * element when parser does not support Locator
     * interface.
     */
    private static final String UNKNOWN = "";

    /**
     * Stack of configuration elements currently being
     * constructed.
     */
    private final List m_elements = new ArrayList();

    /**
     * Stakc of content text for elements currently being
     * constructed.
     */
    private final List m_values = new ArrayList();

    /**
     * The configuration element created.
     */
    private Configuration m_configuration;

    /**
     * The Locator specified by XML parser.
     */
    private Locator m_locator;

    /**
     * Let the XML parser specify locator for when
     * events arrive at handler.
     *
     * @param locator the locator
     */
    public void setDocumentLocator( final Locator locator )
    {
        m_locator = locator;
    }

    /**
     * Reset internal state of handler in preapration for reuse.
     */
    public void clear()
    {
        m_elements.clear();
        m_values.clear();
        m_locator = null;
    }

    /**
     * Return the configuration created by handler.
     *
     * @return the configuration created by handler.
     */
    public Configuration getConfiguration()
    {
        return m_configuration;
    }

    /**
     * Start an element and thus a Configuration object.
     *
     * @param uri the uri (ignored)
     * @param localName the localName (ignored)
     * @param qName the qualified name (used for name of configuration)
     * @param attributes the attributes of XML element
     * @throws SAXException if unable to parse element
     */
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
            path = ConfigurationUtil.
                generatePathName( parent.getPath(),
                                  parent.getName() );
        }
        final DefaultConfiguration configuration =
            new DefaultConfiguration( qName, getLocationDescription(), path );
        if( null != parent )
        {
            parent.addChild( configuration );
        }
        final int length = attributes.getLength();
        for( int i = 0; i < length; i++ )
        {
            final String key = attributes.getQName( i );
            final String value = attributes.getValue( i );
            configuration.setAttribute( key, value );
        }

        m_elements.add( configuration );
    }

    /**
     * End an element and thus a Configuration object.
     * Will pop of configuration and value of object from
     * stack. If the handler detects that element has both
     * child elements and a text value then it will throw
     * a SAXException.
     *
     * @param uri the uri (ignored)
     * @param localName the localName (ignored)
     * @param qName the qualified name (used for name of configuration)
     * @throws SAXException if element had mixed content
     */
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
                        "Mixed content (" + value.trim() + ") " +
                        "not supported @ " + getLocationDescription();
                    throw new SAXException( message );
                }
            }
        }
        m_configuration = configuration;
    }

    /**
     * Receive text data for current element.
     *
     * @param ch the char array
     * @param start the start index
     * @param length the length of data
     * @throws SAXException if unable ot parse data
     */
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

    /**
     * Rethrow exception and dont attempt to do
     * any error handling.
     *
     * @param spe the input exception
     * @throws SAXException always thrown
     */
    public void warning( final SAXParseException spe )
        throws SAXException
    {
        throw spe;
    }

    /**
     * Rethrow exception and dont attempt to do
     * any error handling.
     *
     * @param spe the input exception
     * @throws SAXException always thrown
     */
    public void error( final SAXParseException spe )
        throws SAXException
    {
        throw spe;
    }

    /**
     * Rethrow exception and dont attempt to do
     * any error handling.
     *
     * @param spe the input exception
     * @throws SAXException always thrown
     */
    public void fatalError( final SAXParseException spe )
        throws SAXException
    {
        throw spe;
    }

    /**
     * Utility method to derive current location of
     * XML parser. Attempts to build up a string containing
     * systemID:lineNumber:columnNumber such as
     * "file.xml:20:3" if parser supports all fields.
     *
     * @return the location description
     */
    protected final String getLocationDescription()
    {
        if( null == m_locator ||
            null == m_locator.getSystemId() )
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
