/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
 package org.jcontainer.dna.impl;

import junit.framework.TestCase;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.jcontainer.dna.Configuration;

public class SAXConfigurationHandlerTestCase
   extends TestCase
{
   public void testGetLocationWithNullLocator()
      throws Exception
   {
      final SAXConfigurationHandler handler = new SAXConfigurationHandler();
      final String location = handler.getLocationDescription();
      assertEquals( "location", "", location );
   }

   public void testGetLocationWithNullSystemId()
      throws Exception
   {
      final SAXConfigurationHandler handler = new SAXConfigurationHandler();
      handler.setDocumentLocator( new MockLocator( null ) );
      final String location = handler.getLocationDescription();
      assertEquals( "location", "", location );
   }

   public void testGetLocationWithNonNullSystemId()
      throws Exception
   {
      final SAXConfigurationHandler handler = new SAXConfigurationHandler();
      handler.setDocumentLocator( new MockLocator( "file.xml" ) );
      final String location = handler.getLocationDescription();
      assertEquals( "location", "file.xml", location );
   }

   public void testGetLocationWithLineSet()
      throws Exception
   {
      final SAXConfigurationHandler handler = new SAXConfigurationHandler();
      final MockLocator locator = new MockLocator( "file.xml" );
      locator.setLineNumber( 23 );
      handler.setDocumentLocator( locator );
      final String location = handler.getLocationDescription();
      assertEquals( "location", "file.xml:23", location );
   }

   public void testGetLocationWithColSet()
      throws Exception
   {
      final SAXConfigurationHandler handler = new SAXConfigurationHandler();
      final MockLocator locator = new MockLocator( "file.xml" );
      locator.setLineNumber( 23 );
      locator.setColumnNumber( 15 );
      handler.setDocumentLocator( locator );
      final String location = handler.getLocationDescription();
      assertEquals( "location", "file.xml:23:15", location );
   }

   public void testGetLocationWithColSetButLineNotSet()
      throws Exception
   {
      final SAXConfigurationHandler handler = new SAXConfigurationHandler();
      final MockLocator locator = new MockLocator( "file.xml" );
      locator.setColumnNumber( 15 );
      handler.setDocumentLocator( locator );
      final String location = handler.getLocationDescription();
      assertEquals( "location", "file.xml", location );
   }

   public void testWarningRethrowsException()
      throws Exception
   {
      final SAXConfigurationHandler handler = new SAXConfigurationHandler();
      final SAXParseException spe = new SAXParseException( "", null );
      try
      {
         handler.warning( spe );
      }
      catch ( final SAXException se )
      {
         assertEquals( spe, se );
         return;
      }
      fail( "Expected exception to be thrown" );
   }

   public void testErrorRethrowsException()
      throws Exception
   {
      final SAXConfigurationHandler handler = new SAXConfigurationHandler();
      final SAXParseException spe = new SAXParseException( "", null );
      try
      {
         handler.error( spe );
      }
      catch ( final SAXException se )
      {
         assertEquals( spe, se );
         return;
      }
      fail( "Expected exception to be thrown" );
   }

   public void testFatalRethrowsException()
      throws Exception
   {
      final SAXConfigurationHandler handler = new SAXConfigurationHandler();
      final SAXParseException spe = new SAXParseException( "", null );
      try
      {
         handler.fatalError( spe );
      }
      catch ( final SAXException se )
      {
         assertEquals( spe, se );
         return;
      }
      fail( "Expected exception to be thrown" );
   }

   public void testCreateSimpleConfiguration()
      throws Exception
   {
      final SAXConfigurationHandler handler = new SAXConfigurationHandler();
      final String qName = "myElement";
      handler.startElement( "", "", qName, new AttributesImpl() );
      handler.endElement( "", "", qName );
      final Configuration configuration = handler.getConfiguration();
      assertEquals( "configuration.name", qName, configuration.getName() );
      assertEquals( "configuration.location", "", configuration.getLocation() );
      assertEquals( "configuration.path", "", configuration.getPath() );
   }

   public void testCreateConfigurationWithValue()
      throws Exception
   {
      final SAXConfigurationHandler handler = new SAXConfigurationHandler();
      final String qName = "myElement";
      final String value = "value";
      handler.startElement( "", "", qName, new AttributesImpl() );
      handler.characters( value.toCharArray(), 0, value.length() );
      handler.endElement( "", "", qName );
      final Configuration configuration = handler.getConfiguration();
      assertEquals( "configuration.name", qName, configuration.getName() );
      assertEquals( "configuration.location", "", configuration.getLocation() );
      assertEquals( "configuration.path", "", configuration.getPath() );
      assertEquals( "configuration.value", value, configuration.getValue() );
   }

   public void testCreateConfigurationWithValueInMultipleFragments()
      throws Exception
   {
      final SAXConfigurationHandler handler = new SAXConfigurationHandler();
      final String qName = "myElement";
      final String value = "value";
      handler.startElement( "", "", qName, new AttributesImpl() );
      handler.characters( value.toCharArray(), 0, value.length() );
      handler.characters( value.toCharArray(), 0, value.length() );
      handler.endElement( "", "", qName );
      final Configuration configuration = handler.getConfiguration();
      assertEquals( "configuration.name", qName, configuration.getName() );
      assertEquals( "configuration.location", "", configuration.getLocation() );
      assertEquals( "configuration.path", "", configuration.getPath() );
      assertEquals( "configuration.value",
                    value + value,
                    configuration.getValue() );
   }

   public void testCreateConfigurationWithChildElement()
      throws Exception
   {
      final SAXConfigurationHandler handler = new SAXConfigurationHandler();
      final String qName = "myElement";
      final String childName = "myChild";
      handler.startElement( "", "", qName, new AttributesImpl() );
      handler.startElement( "", "", childName, new AttributesImpl() );
      handler.endElement( "", "", childName );
      handler.endElement( "", "", qName );

      final Configuration configuration = handler.getConfiguration();
      assertEquals( "configuration.name", qName, configuration.getName() );
      assertEquals( "configuration.location", "", configuration.getLocation() );
      assertEquals( "configuration.path", "", configuration.getPath() );
      final Configuration[] children = configuration.getChildren();
      assertEquals( "children.length", 1, children.length );
      assertEquals( "children[ 0 ].name", childName, children[ 0 ].getName() );
      assertEquals( "children[ 0 ].location", "", children[ 0 ].getLocation() );
      assertEquals( "children[ 0 ].path", qName, children[ 0 ].getPath() );
   }

   public void testCreateConfigurationWithDeepChildElements()
      throws Exception
   {
      final SAXConfigurationHandler handler = new SAXConfigurationHandler();
      final String qName = "myElement";
      final String childName = "myChild";
      final String grandChildName = "myGrandChild";
      handler.startElement( "", "", qName, new AttributesImpl() );
      handler.startElement( "", "", childName, new AttributesImpl() );
      handler.startElement( "", "", grandChildName, new AttributesImpl() );
      handler.endElement( "", "", grandChildName );
      handler.endElement( "", "", childName );
      handler.endElement( "", "", qName );

      final Configuration configuration = handler.getConfiguration();
      assertEquals( "configuration.name", qName, configuration.getName() );
      assertEquals( "configuration.location", "", configuration.getLocation() );
      assertEquals( "configuration.path", "", configuration.getPath() );
      final Configuration[] children = configuration.getChildren();
      assertEquals( "children.length", 1, children.length );
      assertEquals( "children[ 0 ].name", childName, children[ 0 ].getName() );
      assertEquals( "children[ 0 ].location", "", children[ 0 ].getLocation() );
      assertEquals( "children[ 0 ].path", qName, children[ 0 ].getPath() );
      final Configuration[] grandChildren = children[ 0 ].getChildren();
      assertEquals( "grandChildren.length", 1, grandChildren.length );
      assertEquals( "grandChildren[ 0 ].name", grandChildName, grandChildren[ 0 ].getName() );
      assertEquals( "grandChildren[ 0 ].location", "", grandChildren[ 0 ].getLocation() );
      assertEquals( "grandChildren[ 0 ].path", "myElement/myChild", grandChildren[ 0 ].getPath() );
   }

   public void testCreateConfigurationWithMixedContent()
      throws Exception
   {
      final SAXConfigurationHandler handler = new SAXConfigurationHandler();
      final String qName = "myElement";
      final String childName = "myChild";
      final String value = "value";
      handler.startElement( "", "", qName, new AttributesImpl() );
      handler.characters( value.toCharArray(), 0, value.length() );
      handler.startElement( "", "", childName, new AttributesImpl() );
      handler.endElement( "", "", childName );
      try
      {
         handler.endElement( "", "", qName );
      }
      catch ( SAXException e )
      {
         return;
      }
      fail( "Expected to fail handling sax events as mixed content" );
   }

   public void testClearHandler()
      throws Exception
   {
      final SAXConfigurationHandler handler = new SAXConfigurationHandler();
      //TODO: This is a really bad unit test - should test internal state
      handler.clear();
   }

   public void testCreateConfigurationContainingEmptySeparator()
      throws Exception
   {
      final SAXConfigurationHandler handler = new SAXConfigurationHandler();
      final String qName = "myElement";
      final String value = "   \n   \t";
      handler.startElement( "", "", qName, new AttributesImpl() );
      handler.characters( value.toCharArray(), 0, value.length() );
      handler.endElement( "", "", qName );
      final Configuration configuration = handler.getConfiguration();
      assertEquals( "configuration.name", qName, configuration.getName() );
      assertEquals( "configuration.location", "", configuration.getLocation() );
      assertEquals( "configuration.path", "", configuration.getPath() );
      assertEquals( "configuration.value", null, configuration.getValue( null ) );
   }

   public void testCreateConfigurationWithAttributes()
      throws Exception
   {
      final SAXConfigurationHandler handler = new SAXConfigurationHandler();
      final String qName = "myElement";
      final AttributesImpl attributes = new AttributesImpl();
      attributes.addAttribute( "", "", "key", "CDATA", "value" );
      handler.startElement( "", "", qName, attributes );
      handler.endElement( "", "", qName );
      final Configuration configuration = handler.getConfiguration();
      assertEquals( "configuration.name", qName, configuration.getName() );
      assertEquals( "configuration.location", "", configuration.getLocation() );
      assertEquals( "configuration.path", "", configuration.getPath() );
      final String[] names = configuration.getAttributeNames();
      assertEquals( "names.length", 1, names.length );
      assertEquals( "names[0]", "key", names[ 0 ] );
      assertEquals( "configuration.getAttribute( names[ 0 ] )",
                    "value", configuration.getAttribute( names[ 0 ] ) );
   }
}
