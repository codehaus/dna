package org.jcontainer.dna.impl;

import junit.framework.TestCase;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;

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
}
