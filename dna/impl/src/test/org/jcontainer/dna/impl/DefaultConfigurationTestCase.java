package org.jcontainer.dna.impl;

import java.util.Map;

import junit.framework.TestCase;
import org.jcontainer.dna.ConfigurationException;
import org.jcontainer.dna.Configuration;

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

   public void testNullNameInCtor()
      throws Exception
   {
      final String name = null;
      final String location = "file.xml:20";
      final String path = "";
      try
      {
         new DefaultConfiguration( name, location, path );
      }
      catch ( final NullPointerException npe )
      {
         assertEquals( "name", npe.getMessage() );
         return;
      }
      fail( "Expected null pointer exception as passed in null to ctor." );
   }

   public void testNullLocationInCtor()
      throws Exception
   {
      final String name = "name";
      final String location = null;
      final String path = "";
      try
      {
         new DefaultConfiguration( name, location, path );
      }
      catch ( final NullPointerException npe )
      {
         assertEquals( "location", npe.getMessage() );
         return;
      }
      fail( "Expected null pointer exception as passed in null to ctor." );
   }

   public void testNullPathInCtor()
      throws Exception
   {
      final String name = "name";
      final String location = "";
      final String path = null;
      try
      {
         new DefaultConfiguration( name, location, path );
      }
      catch ( final NullPointerException npe )
      {
         assertEquals( "path", npe.getMessage() );
         return;
      }
      fail( "Expected null pointer exception as passed in null to ctor." );
   }

   public void testNullNameInSetAttribute()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "name", "", "" );
      try
      {
         configuration.setAttribute( null, "" );
      }
      catch ( final NullPointerException npe )
      {
         assertEquals( "key", npe.getMessage() );
         return;
      }
      fail( "Expected null pointer exception as passed in null to setAttribute." );
   }

   public void testNullValueInSetAttribute()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "name", "", "" );
      try
      {
         configuration.setAttribute( "", null );
      }
      catch ( final NullPointerException npe )
      {
         assertEquals( "value", npe.getMessage() );
         return;
      }
      fail( "Expected null pointer exception as passed in null to setAttribute." );
   }

   public void testNullValueInSetValue()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "name", "", "" );
      try
      {
         configuration.setValue( null );
      }
      catch ( final NullPointerException npe )
      {
         assertEquals( "value", npe.getMessage() );
         return;
      }
      fail( "Expected null pointer exception as passed in null to setValue." );
   }

   public void testNullChildinAddChild()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "name", "", "" );
      try
      {
         configuration.addChild( null );
      }
      catch ( final NullPointerException npe )
      {
         assertEquals( "configuration", npe.getMessage() );
         return;
      }
      fail( "Expected null pointer exception as passed in null to addChild." );
   }

   public void testNullNameInGetAttribute()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "name", "", "" );
      try
      {
         configuration.getAttribute( null );
      }
      catch ( final NullPointerException npe )
      {
         assertEquals( "name", npe.getMessage() );
         return;
      }
      fail( "Expected null pointer exception as passed in null to getAttribute." );
   }

   public void testNullNameInGetChild()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "name", "", "" );
      try
      {
         configuration.getChild( null, false );
      }
      catch ( final NullPointerException npe )
      {
         assertEquals( "name", npe.getMessage() );
         return;
      }
      fail( "Expected null pointer exception as passed in null to getChild." );
   }

   public void testNullNameInGetChildren()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "name", "", "" );
      try
      {
         configuration.getChildren( null );
      }
      catch ( final NullPointerException npe )
      {
         assertEquals( "name", npe.getMessage() );
         return;
      }
      fail( "Expected null pointer exception as passed in null to getChildren." );
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

   public void testGetAttributeAsText()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      final String key = "key";
      final String value = "value";
      configuration.setAttribute( key, value );
      assertEquals( "getAttribute('key')",
                    value,
                    configuration.getAttribute( key ) );
      assertEquals( "getAttribute('key','defaultValue')",
                    value,
                    configuration.getAttribute( key, "defaultValue" ) );
   }

   public void testGetMissingAttributeAsText()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      final String key = "key";
      configuration.setAttribute( "AnotherKey", "someValue" );
      assertEquals( "getAttribute('key','defaultValue')",
                    "defaultValue",
                    configuration.getAttribute( key, "defaultValue" ) );

      try
      {
         configuration.getAttribute( key );
      }
      catch ( ConfigurationException e )
      {
         return;
      }
      fail( "Expected to fail with getAttribute for non existent key" );
   }

   public void testGetAttributeAsBoolean()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      final String key = "key";
      final String value = "true";
      configuration.setAttribute( key, value );
      assertEquals( "getAttribute('key')",
                    true,
                    configuration.getAttributeAsBoolean( key ) );
      assertEquals( "getAttribute('key','false')",
                    true,
                    configuration.getAttributeAsBoolean( key, false ) );
   }

   public void testGetMissingAttributeAsBoolean()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      final String key = "key";
      assertEquals( "getAttribute('key','false')",
                    false,
                    configuration.getAttributeAsBoolean( key, false ) );
      try
      {
         configuration.getAttribute( key );
      }
      catch ( ConfigurationException e )
      {
         return;
      }
      fail( "Expected to fail with getAttribute for non existent key" );
   }

   public void testGetAttributeAsInteger()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      final String key = "key";
      final String value = "3";
      configuration.setAttribute( key, value );
      assertEquals( "getAttribute('key')",
                    3,
                    configuration.getAttributeAsInteger( key ) );
      assertEquals( "getAttribute('key','1')",
                    3,
                    configuration.getAttributeAsInteger( key, 1 ) );
   }

   public void testGetMissingAttributeAsInteger()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      final String key = "key";
      assertEquals( "getAttribute('key','defaultValue')",
                    1,
                    configuration.getAttributeAsInteger( key, 1 ) );

      try
      {
         configuration.getAttributeAsInteger( key );
      }
      catch ( ConfigurationException e )
      {
         return;
      }
      fail( "Expected to fail with getAttribute for non existent key" );
   }

   public void testGetMalformedAttributeAsInteger()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      final String key = "key";
      final String value = "malformed";
      configuration.setAttribute( key, value );
      assertEquals( "getAttribute('key','defaultValue')",
                    1,
                    configuration.getAttributeAsInteger( key, 1 ) );

      try
      {
         configuration.getAttributeAsInteger( key );
      }
      catch ( ConfigurationException e )
      {
         return;
      }
      fail( "Expected to fail with getAttribute for malformed attribute" );
   }

   public void testGetAttributeAsLong()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      final String key = "key";
      final String value = "3";
      configuration.setAttribute( key, value );
      assertEquals( "getAttribute('key')",
                    3,
                    configuration.getAttributeAsLong( key ) );
      assertEquals( "getAttribute('key','1')",
                    3,
                    configuration.getAttributeAsLong( key, 1 ) );
   }

   public void testGetMissingAttributeAsLong()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      final String key = "key";
      assertEquals( "getAttribute('key','1')",
                    1,
                    configuration.getAttributeAsLong( key, 1 ) );

      try
      {
         configuration.getAttributeAsLong( key );
      }
      catch ( ConfigurationException e )
      {
         return;
      }
      fail( "Expected to fail with getAttribute for non existent key" );
   }

   public void testGetMalformedAttributeAsLong()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      final String key = "key";
      final String value = "malformed";
      configuration.setAttribute( key, value );
      assertEquals( "getAttribute('key','1')",
                    1,
                    configuration.getAttributeAsLong( key, 1 ) );

      try
      {
         configuration.getAttributeAsLong( key );
      }
      catch ( ConfigurationException e )
      {
         return;
      }
      fail( "Expected to fail with getAttribute for malformed attribute" );
   }

   public void testGetAttributeAsFloat()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      final String key = "key";
      final String value = "3";
      configuration.setAttribute( key, value );
      assertTrue( "getAttribute('key')",
                  3.0 == configuration.getAttributeAsFloat( key ) );
      assertTrue( "getAttribute('key','1')",
                  3.0 == configuration.getAttributeAsFloat( key, 1 ) );
   }

   public void testGetMissingAttributeAsFloat()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      final String key = "key";
      assertTrue( "getAttribute('key','defaultValue')",
                  1.0 == configuration.getAttributeAsFloat( key, 1 ) );

      try
      {
         configuration.getAttributeAsFloat( key );
      }
      catch ( ConfigurationException e )
      {
         return;
      }
      fail( "Expected to fail with getAttribute for non existent key" );
   }

   public void testGetMalformedAttributeAsFloat()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      final String key = "key";
      final String value = "malformed";
      configuration.setAttribute( key, value );
      assertTrue( "getAttribute('key','defaultValue')",
                  1.0 == configuration.getAttributeAsFloat( key, 1 ) );

      try
      {
         configuration.getAttributeAsFloat( key );
      }
      catch ( ConfigurationException e )
      {
         return;
      }
      fail( "Expected to fail with getAttribute for malformed attribute" );
   }

   public void testGetAttributes()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      configuration.setAttribute( "key1", "value1" );
      configuration.setAttribute( "key2", "value2" );

      final String[] names = configuration.getAttributeNames();
      assertEquals( "names.length", 2, names.length );
   }

   public void testGetAttributesWithNoAttributesSet()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );

      final String[] names = configuration.getAttributeNames();
      assertEquals( "names.length", 0, names.length );
   }

   public void testGetChildren()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      final DefaultConfiguration child =
         new DefaultConfiguration( "mychild", "file.xml:20", "/myElement" );

      configuration.addChild( child );

      final Configuration[] children = configuration.getChildren();
      assertEquals( "children.length", 1, children.length );
      assertEquals( "children[0]", child, children[ 0 ] );
   }

   public void testGetChildrenWithNoChildren()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );

      final Configuration[] children = configuration.getChildren();
      assertEquals( "children.length", 0, children.length );
   }

   public void testGetChild()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      final DefaultConfiguration child =
         new DefaultConfiguration( "mychild", "file.xml:20", "/myElement" );
      configuration.addChild( child );

      final Configuration test = configuration.getChild( "mychild" );
      assertEquals( child, test );
   }

   public void testGetNotExistentChildWithNoAutoCreateButOtherChildren()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );

      final DefaultConfiguration child =
         new DefaultConfiguration( "meep", "file.xml:20", "/myElement" );
      configuration.addChild( child );

      final Configuration test = configuration.getChild( "mychild", false );
      assertEquals( null, test );
   }

   public void testGetNotExistentChildWithNoAutoCreate()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );

      final Configuration test = configuration.getChild( "mychild", false );
      assertEquals( null, test );
   }

   public void testGetNotExistentChildWithAutoCreate()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );

      final Configuration test = configuration.getChild( "mychild", true );
      assertNotNull( test );
      assertEquals( "mychild", test.getName() );
   }

   public void testGuardAgainstMixedContentWhenAddingValue()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      final DefaultConfiguration child =
         new DefaultConfiguration( "mychild", "file.xml:20", "/myElement" );
      configuration.addChild( child );

      try
      {
         configuration.setValue( "blah" );
      }
      catch ( IllegalStateException e )
      {
         return;
      }
      fail( "Expected to fail setting mixed content for configuration" );
   }

   public void testGuardAgainstMixedContentWhenAddingChild()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      final DefaultConfiguration child =
         new DefaultConfiguration( "mychild", "file.xml:20", "/myElement" );
      configuration.setValue( "blah" );

      try
      {
         configuration.addChild( child );
      }
      catch ( IllegalStateException e )
      {
         return;
      }
      fail( "Expected to fail setting mixed content for configuration" );
   }

   public void testGetChildrenWithName()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      final DefaultConfiguration child1 =
         new DefaultConfiguration( "mychild", "file.xml:20", "/myElement" );
      final DefaultConfiguration child2 =
         new DefaultConfiguration( "blah", "file.xml:20", "/myElement" );
      final DefaultConfiguration child3 =
         new DefaultConfiguration( "myOtherChild", "file.xml:20", "/myElement" );

      configuration.addChild( child1 );
      configuration.addChild( child2 );
      configuration.addChild( child3 );

      final Configuration[] children = configuration.getChildren( "mychild" );
      assertEquals( "children.length", 1, children.length );
   }

   public void testGetChildrenWithNameAndNoExistingChildren()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );

      final Configuration[] children =
         configuration.getChildren( "mychild" );
      assertEquals( "children.length", 0, children.length );
   }

   public void testAutogeneratePath()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );

      final Configuration child = configuration.getChild( "test" ).getChild( "blah" );
      assertEquals( "child.path", "/myElement/test", child.getPath() );
      assertTrue( "child.location", child.getLocation().endsWith( "<autogen>" ) );
   }

   public void testMakeReadOnlyWithNoChildren()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      configuration.makeReadOnly();
      assertTrue( "configuration.isReadOnly()", configuration.isReadOnly() );
   }

   public void testMakeReadOnlyWithChildren()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );

      final DefaultConfiguration child =
         new DefaultConfiguration( "child", "file.xml:20", "/myElement" );
      configuration.addChild( child );

      configuration.makeReadOnly();
      assertTrue( "configuration.isReadOnly()", configuration.isReadOnly() );
      assertTrue( "child.isReadOnly()", child.isReadOnly() );
   }

   public void testMakeReadOnlyWithNonFreezableChildren()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );

      configuration.addChild( new MockConfiguration() );

      configuration.makeReadOnly();
      assertTrue( "configuration.isReadOnly()", configuration.isReadOnly() );
   }

   public void testToString()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );

      final String expected = "[Configuration name='myElement']";
      final String string = configuration.toString();
      assertEquals( expected, string );
   }

   public void testToStringWithAttributes()
      throws Exception
   {
      final DefaultConfiguration configuration =
         new DefaultConfiguration( "myElement", "file.xml:20", "" );
      configuration.setAttribute( "key", "value" );
      final Map attributeMap = configuration.getAttributeMap();

      final String expected =
         "[Configuration name='myElement' attributes=" + attributeMap + "]";
      final String string = configuration.toString();
      assertEquals( expected, string );
   }
}
