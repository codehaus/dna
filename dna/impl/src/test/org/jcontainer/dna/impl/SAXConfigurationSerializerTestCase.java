package org.jcontainer.dna.impl;

import java.lang.reflect.Proxy;

import junit.framework.TestCase;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.AttributesImpl;

public class SAXConfigurationSerializerTestCase
   extends TestCase
{
   public void testSerializeZeroLengthAttributes()
      throws Exception
   {
      final DefaultConfiguration configuration = new DefaultConfiguration( "element", "", "" );
      final SAXConfigurationSerializer serializer = new SAXConfigurationSerializer();
      final AttributesImpl attributes = serializer.serializeAttributes( configuration );
      assertEquals( "attributes.getLength()", 0, attributes.getLength() );
   }

   public void testSerializeAttributes()
      throws Exception
   {
      final DefaultConfiguration configuration = new DefaultConfiguration( "element", "", "" );
      final String name = "key";
      final String value = "value";
      configuration.setAttribute( name, value );
      final SAXConfigurationSerializer serializer = new SAXConfigurationSerializer();
      final AttributesImpl attributes = serializer.serializeAttributes( configuration );
      assertEquals( "attributes.getLength()", 1, attributes.getLength() );
      assertEquals( "attributes.getLocalName(0)", name, attributes.getLocalName( 0 ) );
      assertEquals( "attributes.getQName(0)", name, attributes.getQName( 0 ) );
      assertEquals( "attributes.getURI(0)", "", attributes.getURI( 0 ) );
      assertEquals( "attributes.getType(0)", "CDATA", attributes.getType( 0 ) );
      assertEquals( "attributes.getType(0)", value, attributes.getValue( 0 ) );
   }

   public void testSerializeElementWithNoContentOrChildren()
      throws Exception
   {
      final String name = "element";
      final DefaultConfiguration configuration = new DefaultConfiguration( name, "", "" );
      final SAXConfigurationSerializer serializer = new MockSAXConfigurationSerializer();

      final MockInvocationRecorder recorder = new MockInvocationRecorder();
      recorder.addInvocation( SAXMethods.START_ELEMENT,
                              new Object[]{"", name, name,
                                           MockSAXConfigurationSerializer.ATTRIBUTES},
                              null );
      recorder.addInvocation( SAXMethods.END_ELEMENT,
                              new Object[]{"", name, name},
                              null );

      final ContentHandler handler = (ContentHandler)
         Proxy.newProxyInstance( getClass().getClassLoader(),
                                 new Class[]{ContentHandler.class},
                                 recorder );

      serializer.serializeElement( configuration, handler );
   }

   public void testSerializeElementWithValue()
      throws Exception
   {
      final String name = "element";
      final String value = "value";
      final DefaultConfiguration configuration = new DefaultConfiguration( name, "", "" );
      configuration.setValue( value );
      final SAXConfigurationSerializer serializer = new MockSAXConfigurationSerializer();

      final MockInvocationRecorder recorder = new MockInvocationRecorder();
      recorder.addInvocation( SAXMethods.START_ELEMENT,
                              new Object[]{"", name, name,
                                           MockSAXConfigurationSerializer.ATTRIBUTES},
                              null );
      recorder.addInvocation( SAXMethods.CHARACTERS,
                              new Object[]{value.toCharArray(), new Integer( 0 ), new Integer( 0 )},
                              null );
      recorder.addInvocation( SAXMethods.END_ELEMENT,
                              new Object[]{"", name, name},
                              null );

      final ContentHandler handler = (ContentHandler)
         Proxy.newProxyInstance( getClass().getClassLoader(),
                                 new Class[]{ContentHandler.class},
                                 recorder );

      serializer.serializeElement( configuration, handler );
   }

   public void testSerializeElementWithChild()
      throws Exception
   {
      final String name = "element";
      final String childName = "child";
      final DefaultConfiguration configuration = new DefaultConfiguration( name, "", "" );
      final DefaultConfiguration child = new DefaultConfiguration( childName, "", "" );
      configuration.addChild( child );
      final SAXConfigurationSerializer serializer = new MockSAXConfigurationSerializer();

      final MockInvocationRecorder recorder = new MockInvocationRecorder();
      recorder.addInvocation( SAXMethods.START_ELEMENT,
                              new Object[]{"", name, name,
                                           MockSAXConfigurationSerializer.ATTRIBUTES},
                              null );
      recorder.addInvocation( SAXMethods.START_ELEMENT,
                              new Object[]{"", childName, childName,
                                           MockSAXConfigurationSerializer.ATTRIBUTES},
                              null );
      recorder.addInvocation( SAXMethods.END_ELEMENT,
                              new Object[]{"", childName, childName},
                              null );
      recorder.addInvocation( SAXMethods.END_ELEMENT,
                              new Object[]{"", name, name},
                              null );

      final ContentHandler handler = (ContentHandler)
         Proxy.newProxyInstance( getClass().getClassLoader(),
                                 new Class[]{ContentHandler.class},
                                 recorder );

      serializer.serializeElement( configuration, handler );
   }

   public void testSerializeElementWithChildAndContent()
      throws Exception
   {
      final String name = "element";
      final String childName = "child";
      final String value = "text";
      final DefaultConfiguration configuration = new DefaultConfiguration( name, "", "" );
      final DefaultConfiguration child = new DefaultConfiguration( childName, "", "" );
      configuration.addChild( child );
      child.setValue( value );
      final SAXConfigurationSerializer serializer = new MockSAXConfigurationSerializer();

      final MockInvocationRecorder recorder = new MockInvocationRecorder();
      recorder.addInvocation( SAXMethods.START_ELEMENT,
                              new Object[]{"", name, name,
                                           MockSAXConfigurationSerializer.ATTRIBUTES},
                              null );
      recorder.addInvocation( SAXMethods.START_ELEMENT,
                              new Object[]{"", childName, childName,
                                           MockSAXConfigurationSerializer.ATTRIBUTES},
                              null );
      recorder.addInvocation( SAXMethods.CHARACTERS,
                              new Object[]{value.toCharArray(), new Integer( 0 ), new Integer( 0 )},
                              null );
      recorder.addInvocation( SAXMethods.END_ELEMENT,
                              new Object[]{"", childName, childName},
                              null );
      recorder.addInvocation( SAXMethods.END_ELEMENT,
                              new Object[]{"", name, name},
                              null );

      final ContentHandler handler = (ContentHandler)
         Proxy.newProxyInstance( getClass().getClassLoader(),
                                 new Class[]{ContentHandler.class},
                                 recorder );

      serializer.serializeElement( configuration, handler );
   }

   public void testSerializeElementAsPartOfDocument()
      throws Exception
   {
      final String name = "element";
      final DefaultConfiguration configuration = new DefaultConfiguration( name, "", "" );
      final SAXConfigurationSerializer serializer = new MockSAXConfigurationSerializer();

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

      serializer.serialize( configuration, handler );
   }
}
