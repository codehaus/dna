/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl;

import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;

import org.codehaus.dna.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

/**
 * Class containing utility methods to work with Configuration
 * objects.
 *
 * @version $Revision: 1.1 $ $Date: 2004-04-18 20:13:45 $
 */
public class ConfigurationUtil
{
    /**
     * Constant defining separator for paths in document.
     */
    public static final String PATH_SEPARATOR = "/";

    /**
     * Constant defining root path of document.
     */
    public static final String ROOT_PATH = "";

    /**
     * Constant indicating location was generated from DOM
     * Element.
     */
    private static final String ELEMENT_LOCATION = "dom-gen";

    /**
     * Serialize Configuration object to sepcified Result object.
     * The developer can serialize to a system out by using
     * {@link javax.xml.transform.stream.StreamResult} in code
     * such as;
     *
     * <pre>
     *  ConfigurationUtil.
     *     serializeToResult( new StreamResult( System.out ),
     *                        configuration );
     * </pre>
     *
     * <p>The developer can also output to SAX stream or DOM trees
     * via {@link javax.xml.transform.sax.SAXResult} and
     * {@link javax.xml.transform.dom.DOMResult}.</p>
     *
     * @param result the result object to serialize configuration to
     * @param configuration the configuration
     * @throws Exception if unable to serialize configuration
     */
    public static void serializeToResult( final Result result,
                                          final Configuration configuration )
        throws Exception
    {
        final SAXTransformerFactory factory =
            (SAXTransformerFactory)TransformerFactory.newInstance();
        final TransformerHandler handler = factory.newTransformerHandler();

        final Properties format = new Properties();
        format.put( OutputKeys.METHOD, "xml" );
        format.put( OutputKeys.INDENT, "yes" );
        handler.setResult( result );
        handler.getTransformer().setOutputProperties( format );

        final SAXConfigurationSerializer serializer = new SAXConfigurationSerializer();
        serializer.serialize( configuration, handler );
    }

    /**
     * Create a configuration object from specified XML InputSource.
     *
     * @param input the InputSource
     * @return the configuration object
     * @throws Exception if unable to create configuration object
     *         from input
     */
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

    /**
     * Convert specified Element into a configuration object.
     *
     * @param element the Element
     * @return the Configuration object
     */
    public static Configuration toConfiguration( final Element element )
    {
        return toConfiguration( element, ROOT_PATH );
    }

    /**
     * Internal utility method to convert specified Element into
     * a configuration object.
     *
     * @param element the Element
     * @param parentPath the path to root of document
     * @return the Configuration object
     */
    private static Configuration toConfiguration( final Element element,
                                                  final String parentPath )
    {
        final DefaultConfiguration configuration =
            new DefaultConfiguration( element.getNodeName(), ELEMENT_LOCATION, parentPath );
        final NamedNodeMap attributes = element.getAttributes();
        final int length = attributes.getLength();
        for( int i = 0; i < length; i++ )
        {
            final Node node = attributes.item( i );
            final String name = node.getNodeName();
            final String value = node.getNodeValue();
            configuration.setAttribute( name, value );
        }

        final String childPath =
            generatePathName( parentPath, configuration.getName() );

        String content = null;
        final NodeList nodes = element.getChildNodes();
        final int count = nodes.getLength();
        for( int i = 0; i < count; i++ )
        {
            final Node node = nodes.item( i );
            if( node instanceof Element )
            {
                final Configuration child = toConfiguration( (Element)node, childPath );
                configuration.addChild( child );
            }
            else if( node instanceof Text )
            {
                final Text data = (Text)node;
                if( null != content )
                {
                    content += data.getData();
                }
                else
                {
                    content = data.getData();
                }
            }
        }

        if( null != content )
        {
            configuration.setValue( content );
        }

        return configuration;
    }

    /**
     * Add in utity method to generate path string from parent.
     *
     * @param path parents path
     * @param name parents name
     * @return the path string
     */
    static String generatePathName( final String path,
                                    final String name )
    {
        if( ROOT_PATH.equals( path ) )
        {
            return name;
        }
        else
        {
            return path + PATH_SEPARATOR + name;
        }
    }

    /**
     * Convert specified Configuration object into a Element.
     *
     * @param configuration the Configuration
     * @return the Element object
     */
    public static Element toElement( final Configuration configuration )
    {
        try
        {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.newDocument();

            return createElement( document, configuration );
        }
        catch( final Throwable t )
        {
            throw new IllegalStateException( t.toString() );
        }
    }

    /**
     * Internal helper method to convert specified Configuration object
     * into a Element.
     *
     * @param document the owner document
     * @param configuration the Configuration
     * @return the Element object
     */
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

    /**
     * Test if two configuration objects are equal. To be equal
     * the configuration objects must have equal child configuration
     * objects in identical orders or identical content values and
     * must have the same attributes with the same values.
     *
     * @param configuration1 a configuration object
     * @param configuration2 a configuration object
     * @return true if the configuration objects are equal
     */
    public static boolean equals( final Configuration configuration1,
                                  final Configuration configuration2 )
    {
        final String name1 = configuration1.getName();
        final String name2 = configuration2.getName();
        if( !name1.equals( name2 ) )
        {
            return false;
        }

        final Configuration[] children1 = configuration1.getChildren();
        final Configuration[] children2 = configuration2.getChildren();
        if( children1.length != children2.length )
        {
            return false;
        }
        else
        {
            for( int i = 0; i < children1.length; i++ )
            {
                if( !equals( children1[ i ], children2[ i ] ) )
                {
                    return false;
                }
            }
        }

        final String[] names1 = configuration1.getAttributeNames();
        final String[] names2 = configuration2.getAttributeNames();
        if( names1.length != names2.length )
        {
            return false;
        }
        else
        {
            for( int i = 0; i < names1.length; i++ )
            {
                final String value1 =
                    configuration1.getAttribute( names1[ i ], null );
                final String value2 =
                    configuration2.getAttribute( names1[ i ], null );
                if( !value1.equals( value2 ) )
                {
                    return false;
                }
            }
        }

        final String value1 = configuration1.getValue( null );
        final String value2 = configuration2.getValue( null );
        if( null == value1 && null == value2 )
        {
            return true;
        }
        else if( null != value1 && null != value2 )
        {
            return value1.equals( value2 );
        }
        else
        {
            return false;
        }
    }
}
