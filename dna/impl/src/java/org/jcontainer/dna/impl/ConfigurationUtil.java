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
 * Class containing utility methods to work with Configuration
 * objects.
 *
 * @version $Revision: 1.6 $ $Date: 2003-09-08 02:00:39 $
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
   public static final String ROOT_PATH = PATH_SEPARATOR;

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
      final SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory.newInstance();
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
    * @param path the path to root of document
    * @return the Configuration object
    */
   private static Configuration toConfiguration( final Element element,
                                                 final String path )
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( element.getNodeName(), "dom-gen", path );
      final NamedNodeMap attributes = element.getAttributes();
      final int length = attributes.getLength();
      for ( int i = 0; i < length; i++ )
      {
         final Node node = attributes.item( i );
         final String name = node.getNodeName();
         final String value = node.getNodeValue();
         configuration.setAttribute( name, value );
      }

      final String childPath = path + PATH_SEPARATOR + configuration.getName();

      String content = null;
      final NodeList nodes = element.getChildNodes();
      final int count = nodes.getLength();
      for ( int i = 0; i < count; i++ )
      {
         final Node node = nodes.item( i );
         if ( node instanceof Element )
         {
            final Configuration child = toConfiguration( (Element) node, childPath );
            configuration.addChild( child );
         }
         else if ( node instanceof CharacterData )
         {
            final CharacterData data = (CharacterData) node;
            content += data.getData();
         }
      }

      if ( null != content )
      {
         configuration.setValue( content );
      }

      return configuration;
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
      catch ( final Exception ce )
      {
         throw new IllegalStateException( ce.toString() );
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
      if ( null != content )
      {
         final Text child = document.createTextNode( content );
         element.appendChild( child );
      }

      final String[] names = configuration.getAttributeNames();
      for ( int i = 0; i < names.length; i++ )
      {
         final String name = names[ i ];
         final String value = configuration.getAttribute( name, null );
         element.setAttribute( name, value );
      }
      final Configuration[] children = configuration.getChildren();
      for ( int i = 0; i < children.length; i++ )
      {
         final Element child = createElement( document, children[ i ] );
         element.appendChild( child );
      }
      return element;
   }
}
