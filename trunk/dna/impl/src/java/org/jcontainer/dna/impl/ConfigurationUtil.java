/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.impl;

import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Result;

import org.jcontainer.dna.Configuration;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

/**
 *
 * @version $Revision: 1.4 $ $Date: 2003-08-29 02:47:23 $
 */
public class ConfigurationUtil
{
    public static void serializeToResult( final Result result,
                                          final Configuration configuration )
        throws Exception
    {
        final SAXTransformerFactory factory = (SAXTransformerFactory)TransformerFactory.newInstance();
        final TransformerHandler handler = factory.newTransformerHandler();

        final Properties format = new Properties();
        format.put( OutputKeys.METHOD, "xml" );
        format.put( OutputKeys.INDENT, "yes" );
        handler.setResult( result );
        handler.getTransformer().setOutputProperties( format );

        final SAXConfigurationSerializer serializer = new SAXConfigurationSerializer();
        serializer.serialize( configuration, handler );
    }

    public static Configuration buildFromXML( final InputSource input )
        throws Exception
    {
        final SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setNamespaceAware( false );
        final SAXParser saxParser = saxParserFactory.newSAXParser();
        final SAXConfigurationHandler handler = new SAXConfigurationHandler();
        saxParser.parse( input, handler );
        return handler.getConfiguration();
    }

    public static Configuration toConfiguration( final Element element )
    {
        final DefaultConfiguration configuration =
            new DefaultConfiguration( element.getNodeName(), "dom-gen" );
        final NamedNodeMap attributes = element.getAttributes();
        final int length = attributes.getLength();
        for( int i = 0; i < length; i++ )
        {
            final Node node = attributes.item( i );
            final String name = node.getNodeName();
            final String value = node.getNodeValue();
            configuration.setAttribute( name, value );
        }

        String content = null;
        final NodeList nodes = element.getChildNodes();
        final int count = nodes.getLength();
        for( int i = 0; i < count; i++ )
        {
            final Node node = nodes.item( i );
            if( node instanceof Element )
            {
                final Configuration child = toConfiguration( (Element)node );
                configuration.addChild( child );
            }
            else if( node instanceof CharacterData )
            {
                final CharacterData data = (CharacterData)node;
                content += data.getData();
            }
        }

        if( null != content )
        {
            configuration.setValue( content );
        }

        return configuration;
    }

    public static Element toElement( final Configuration configuration )
    {
        try
        {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.newDocument();

            return createElement( document, configuration );
        }
        catch( final Exception ce )
        {
            throw new IllegalStateException( ce.toString() );
        }
    }

    private static Element createElement( final Document document,
                                          final Configuration configuration )
    {
        final Element element = document.createElement( configuration.getName() );

        final String content = configuration.getValue( null );
        if( null != content )
        {
            final Text child = document.createTextNode( content );
            element.appendChild( child );
        }

        final String[] names = configuration.getAttributeNames();
        for( int i = 0; i < names.length; i++ )
        {
            final String name = names[ i ];
            final String value = configuration.getAttribute( name, null );
            element.setAttribute( name, value );
        }
        final Configuration[] children = configuration.getChildren();
        for( int i = 0; i < children.length; i++ )
        {
            final Element child = createElement( document, children[ i ] );
            element.appendChild( child );
        }
        return element;
    }
}
