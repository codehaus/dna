package org.jcontainer.dna.impl;

import junit.framework.TestCase;
import org.jcontainer.dna.ConfigurationException;

public class DefaultConfigurationTestCase
   extends TestCase
{
   public void testBasicConfigurationElement()
      throws Exception
   {
      final String name = "myElement";
      final String location = "file.xml:20";
      final String path = "";
      final DefaultConfiguration configuration =
         new DefaultConfiguration( name, location, path );
      assertEquals( "name", name, configuration.getName() );
      assertEquals( "location", location, configuration.getLocation() );
      assertEquals( "path", path, configuration.getPath() );
   }

   public void testGetValueAsText()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      final String value = "blah";
      configuration.setValue( value );
      assertEquals( "getValue()", value, configuration.getValue() );
      assertEquals( "getValue('test')", value, configuration.getValue( "test" ) );
   }

   public void testGetNullValueAsText()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      assertEquals( "getValue('test')", "test", configuration.getValue( "test" ) );
      try
      {
         configuration.getValue();
      }
      catch ( ConfigurationException e )
      {
         return;
      }
      fail( "Expected getValue() to throw an exception" );
   }

   public void testGetValueAsBoolean()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      configuration.setValue( "true" );
      assertEquals( "getValue()", true, configuration.getValueAsBoolean() );
      assertEquals( "getValue('false')", true, configuration.getValueAsBoolean( false ) );
   }

   public void testGetNullValueAsBoolean()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      assertEquals( "getValue('false')", false, configuration.getValueAsBoolean( false ) );
      try
      {
         configuration.getValueAsBoolean();
      }
      catch ( ConfigurationException e )
      {
         return;
      }
      fail( "Expected getValue() to throw an exception" );
   }

   public void testGetValueAsInteger()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      configuration.setValue( "3" );
      assertEquals( "getValue()", 3, configuration.getValueAsInteger() );
      assertEquals( "getValue('1')", 3, configuration.getValueAsInteger( 1 ) );
   }

   public void testGetNullValueAsInteger()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      assertEquals( "getValue('1')", 1, configuration.getValueAsInteger( 1 ) );
      try
      {
         configuration.getValueAsInteger();
      }
      catch ( ConfigurationException e )
      {
         return;
      }
      fail( "Expected getValue() to throw an exception" );
   }

   public void testGetMalformedValueAsInteger()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      configuration.setValue( "malformed" );
      assertEquals( "getValue('1')", 1, configuration.getValueAsInteger( 1 ) );
      try
      {
         configuration.getValueAsInteger();
      }
      catch ( ConfigurationException e )
      {
         return;
      }
      fail( "Expected getValue() to throw an exception" );
   }

   public void testGetValueAsLong()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      configuration.setValue( "3" );
      assertEquals( "getValue()", 3, configuration.getValueAsLong() );
      assertEquals( "getValue('1')", 3, configuration.getValueAsLong( 1 ) );
   }

   public void testGetNullValueAsLong()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      assertEquals( "getValue('1')", 1, configuration.getValueAsLong( 1 ) );
      try
      {
         configuration.getValueAsLong();
      }
      catch ( ConfigurationException e )
      {
         return;
      }
      fail( "Expected getValue() to throw an exception" );
   }

   public void testGetMalformedValueAsLong()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      configuration.setValue( "malformed" );
      assertEquals( "getValue('1')", 1, configuration.getValueAsLong( 1 ) );
      try
      {
         configuration.getValueAsLong();
      }
      catch ( ConfigurationException e )
      {
         return;
      }
      fail( "Expected getValue() to throw an exception" );
   }

   public void testGetValueAsFloat()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      configuration.setValue( "3.0" );
      assertTrue( "getValue()", 3.0 == configuration.getValueAsFloat() );
      assertTrue( "getValue('1')", 3.0 == configuration.getValueAsFloat( 1 ) );
   }

   public void testGetNullValueAsFloat()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      assertTrue( "getValue('1')", 1.0 == configuration.getValueAsFloat( 1 ) );
      try
      {
         configuration.getValueAsFloat();
      }
      catch ( ConfigurationException e )
      {
         return;
      }
      fail( "Expected getValue() to throw an exception" );
   }

   public void testGetMalformedValueAsFloat()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      configuration.setValue( "malformed" );
      assertTrue( "getValue('1')", 1.0 == configuration.getValueAsFloat( 1 ) );
      try
      {
         configuration.getValueAsFloat();
      }
      catch ( ConfigurationException e )
      {
         return;
      }
      fail( "Expected getValue() to throw an exception" );
   }
}
