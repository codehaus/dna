/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
 package org.jcontainer.dna.impl;

import java.lang.reflect.Proxy;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.sax.SAXResult;

import junit.framework.TestCase;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Text;
import org.w3c.dom.Comment;
import org.jcontainer.dna.Configuration;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;

public class ConfigurationUtilTestCase
   extends TestCase
{
   private static final String DOC_FACTORY = "javax.xml.parsers.DocumentBuilderFactory";

   public void testToElementWithBasicConfiguration()
      throws Exception
   {
      final String name = "meep";
      final DefaultConfiguration configuration = new DefaultConfiguration( name, "", "" );
      final Element element = ConfigurationUtil.toElement( configuration );
      assertEquals( "element.getNodeName()", name, element.getNodeName() );

      final NodeList nodeList = element.getChildNodes();
      assertEquals( "nodeList.getLength()", 0, nodeList.getLength() );

      final NamedNodeMap attributes = element.getAttributes();
      assertEquals( "attributes.getLength()", 0, attributes.getLength() );
   }

   public void testToElementWithConfigurationWithValue()
      throws Exception
   {
      final String name = "meep";
      final String value = "blah";
      final DefaultConfiguration configuration = new DefaultConfiguration( name, "", "" );
      configuration.setValue( value );
      final Element element = ConfigurationUtil.toElement( configuration );
      assertEquals( "element.getNodeName()", name, element.getNodeName() );

      final NodeList nodeList = element.getChildNodes();
      assertEquals( "nodeList.getLength()", 1, nodeList.getLength() );
      final Node node = nodeList.item( 0 );
      assertEquals( "element[0].value", value, node.getNodeValue() );


      final NamedNodeMap attributes = element.getAttributes();
      assertEquals( "attributes.getLength()", 0, attributes.getLength() );
   }

   public void testToElementWithConfigurationWithAttributes()
      throws Exception
   {
      final String name = "meep";
      final String key = "key";
      final String value = "value";

      final DefaultConfiguration configuration = new DefaultConfiguration( name, "", "" );
      configuration.setAttribute( key, value );

      final Element element = ConfigurationUtil.toElement( configuration );
      assertEquals( "element.getNodeName()", name, element.getNodeName() );

      final NodeList nodeList = element.getChildNodes();
      assertEquals( "nodeList.getLength()", 0, nodeList.getLength() );

      final NamedNodeMap attributes = element.getAttributes();
      assertEquals( "attributes.getLength()", 1, attributes.getLength() );
      final Node node = attributes.item( 0 );
      assertEquals( "attribute[0].name", key, node.getNodeName() );
      assertEquals( "attribute[0].value", value, node.getNodeValue() );
   }

   public void testToElementWithConfigurationWithChildren()
      throws Exception
   {
      final String name = "meep";
      final String childName = "moop";

      final DefaultConfiguration configuration = new DefaultConfiguration( name, "", "" );
      final DefaultConfiguration child = new DefaultConfiguration( childName, "", "" );
      configuration.addChild( child );

      final Element element = ConfigurationUtil.toElement( configuration );
      assertEquals( "element.getNodeName()", name, element.getNodeName() );

      final NodeList nodeList = element.getChildNodes();
      assertEquals( "nodeList.getLength()", 1, nodeList.getLength() );
      final Node node = nodeList.item( 0 );
      assertEquals( "element[0].name", childName, node.getNodeName() );

      final NamedNodeMap attributes = element.getAttributes();
      assertEquals( "attributes.getLength()", 0, attributes.getLength() );

   }

   public void testToElementWithSevereError()
      throws Exception
   {
      final String name = "meep";
      final DefaultConfiguration configuration = new DefaultConfiguration( name, "", "" );

      final String property = System.getProperty( DOC_FACTORY );
      try
      {
         System.setProperty( "javax.xml.parsers.DocumentBuilderFactory",
                             "I dont exist!!!" );
         ConfigurationUtil.toElement( configuration );
      }
      catch ( final IllegalStateException ise )
      {
         return;
      }
      finally
      {
         if ( null != property )
         {
            System.setProperty( DOC_FACTORY, property );
         }
         else
         {
            System.getProperties().remove( DOC_FACTORY );
         }
      }
      fail( "Expected to fail to create element as " +
            "invlaid document factory property" );
   }

   public void testToConfigurationFromBasicElement()
      throws Exception
   {
      final Document document = createDocument();
      final String name = "meep";
      final Element element = document.createElement( name );
      final Configuration configuration = ConfigurationUtil.toConfiguration( element );

      assertEquals( "configuration.getName()", name, configuration.getName() );
      assertEquals( "configuration.getPath()",
                    ConfigurationUtil.ROOT_PATH,
                    configuration.getPath() );
      assertEquals( "configuration.getLocation()",
                    "dom-gen",
                    configuration.getLocation() );
   }

   public void testToConfigurationFromElementWithValue()
      throws Exception
   {
      final Document document = createDocument();
      final String name = "meep";
      final String value = "text";
      final Element element = document.createElement( name );
      final Text text = document.createTextNode( value );
      element.appendChild( text );
      final Configuration configuration = ConfigurationUtil.toConfiguration( element );

      assertEquals( "configuration.getName()", name, configuration.getName() );
      assertEquals( "configuration.getPath()",
                    ConfigurationUtil.ROOT_PATH,
                    configuration.getPath() );
      assertEquals( "configuration.getLocation()",
                    "dom-gen",
                    configuration.getLocation() );
      assertEquals( "configuration.getValue()",
                    value,
                    configuration.getValue() );
   }

   public void testToConfigurationFromElementWithMultipleValueFragments()
      throws Exception
   {
      final Document document = createDocument();
      final String name = "meep";
      final String value = "text";
      final Element element = document.createElement( name );
      final Text text = document.createTextNode( value );
      element.appendChild( text );
      final Text text2 = document.createTextNode( value );
      element.appendChild( text2 );
      final Configuration configuration = ConfigurationUtil.toConfiguration( element );

      assertEquals( "configuration.getName()", name, configuration.getName() );
      assertEquals( "configuration.getPath()",
                    ConfigurationUtil.ROOT_PATH,
                    configuration.getPath() );
      assertEquals( "configuration.getLocation()",
                    "dom-gen",
                    configuration.getLocation() );
      assertEquals( "configuration.getValue()",
                    value + value,
                    configuration.getValue() );
   }

   public void testToConfigurationFromElementWithInternalComment()
      throws Exception
   {
      final Document document = createDocument();
      final String name = "meep";
      final Element element = document.createElement( name );
      final Comment comment = document.createComment( "comment" );
      element.appendChild( comment );
      final Configuration configuration = ConfigurationUtil.toConfiguration( element );

      assertEquals( "configuration.getName()", name, configuration.getName() );
      assertEquals( "configuration.getPath()",
                    ConfigurationUtil.ROOT_PATH,
                    configuration.getPath() );
      assertEquals( "configuration.getLocation()",
                    "dom-gen",
                    configuration.getLocation() );
      assertEquals( "configuration.getValue()",
                    null,
                    configuration.getValue( null ) );
   }

   public void testToConfigurationFromElementWithAttributes()
      throws Exception
   {
      final Document document = createDocument();
      final String name = "meep";
      final String key = "key";
      final String value = "value";
      final Element element = document.createElement( name );
      element.setAttribute( key, value );
      final Configuration configuration = ConfigurationUtil.toConfiguration( element );

      assertEquals( "configuration.getName()", name, configuration.getName() );
      assertEquals( "configuration.getPath()",
                    ConfigurationUtil.ROOT_PATH,
                    configuration.getPath() );
      assertEquals( "configuration.getLocation()",
                    "dom-gen",
                    configuration.getLocation() );
      assertEquals( "configuration.getAttributeNames().length",
                    1,
                    configuration.getAttributeNames().length );
      assertEquals( "configuration.getAttribute( key )",
                    value,
                    configuration.getAttribute( key ) );
   }

   public void testToConfigurationFromElementWithChildren()
      throws Exception
   {
      final Document document = createDocument();
      final String name = "meep";
      final String childName = "lilmeep";
      final Element element = document.createElement( name );
      final Element childElement = document.createElement( childName );
      element.appendChild( childElement );

      final Configuration configuration = ConfigurationUtil.toConfiguration( element );

      assertEquals( "configuration.getName()", name, configuration.getName() );
      assertEquals( "configuration.getPath()",
                    ConfigurationUtil.ROOT_PATH,
                    configuration.getPath() );
      assertEquals( "configuration.getLocation()",
                    "dom-gen",
                    configuration.getLocation() );
      assertEquals( "configuration.getAttributeNames().length",
                    0,
                    configuration.getAttributeNames().length );
      assertEquals( "configuration.getChildren().length",
                    1,
                    configuration.getChildren().length );
      final Configuration child = configuration.getChildren()[ 0 ];
      assertEquals( "child.name", childName, child.getName() );
      assertEquals( "child.getPath()", "meep", child.getPath() );
   }

   public void testSerializeToResult()
      throws Exception
   {
      final String name = "meep";
      final DefaultConfiguration configuration = new DefaultConfiguration( name, "", "" );

      final MockInvocationRecorder recorder = new MockInvocationRecorder();
      recorder.addInvocation( SAXMethods.START_DOCUMENT,
                              new Object[ 0 ],
                              null );
      recorder.addInvocation( SAXMethods.START_ELEMENT,
                              new Object[]{"", name, name,
                                           MockSAXConfigurationSerializer.ATTRIBUTES},
                              null );
      recorder.addInvocation( SAXMethods.END_ELEMENT,
                              new Object[]{"", name, name},
                              null );
      recorder.addInvocation( SAXMethods.END_DOCUMENT,
                              new Object[ 0 ],
                              null );

      final ContentHandler handler = (ContentHandler)
         Proxy.newProxyInstance( getClass().getClassLoader(),
                                 new Class[]{ContentHandler.class},
                                 recorder );

      final SAXResult result = new SAXResult( handler );

      ConfigurationUtil.serializeToResult( result, configuration );
   }

   public void testBuildFromXML()
      throws Exception
   {
      final String data = "<element/>";
      final InputSource input = new InputSource();
      input.setCharacterStream( new StringReader( data ) );
      final Configuration configuration = ConfigurationUtil.buildFromXML( input );
      assertEquals( "configuration.name", "element", configuration.getName() );
      assertEquals( "configuration.path", "", configuration.getPath() );
      assertEquals( "configuration.location", "", configuration.getLocation() );
   }

   private Document createDocument() throws ParserConfigurationException
   {
      final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      final DocumentBuilder builder = factory.newDocumentBuilder();
      return builder.newDocument();
   }

   public void testGeneratePathNameFromRootForRoot()
      throws Exception
   {
      final String path =
         ConfigurationUtil.generatePathName( "", "" );
      assertEquals( "", path );
   }

   public void testGeneratePathNameFromRoot()
      throws Exception
   {
      final String path =
         ConfigurationUtil.generatePathName( "", "element" );

      assertEquals( "element", path );
   }

   public void testGeneratePathNameFromNonRoot()
      throws Exception
   {
      final String path =
         ConfigurationUtil.generatePathName( "element", "child" );

      assertEquals( "element/child", path );
   }
}
