package org.jcontainer.dna.impl;

import junit.framework.TestCase;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ConfigurationUtilTestCase
   extends TestCase
{
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

      final String property = System.getProperty( "javax.xml.parsers.DocumentBuilderFactory" );
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
         System.setProperty( "javax.xml.parsers.DocumentBuilderFactory",
                             property );
      }
      fail( "Expected to fail to create element as " +
            "invlaid document factory property" );
   }
}
