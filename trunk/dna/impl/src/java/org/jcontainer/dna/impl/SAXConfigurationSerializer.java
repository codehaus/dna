/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.impl;

import org.jcontainer.dna.Configuration;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.1 $ $Date: 2003-08-29 02:37:51 $
 */
public class SAXConfigurationSerializer
{
   private static final String CDATA_TYPE = "CDATA";
   private static final String EMPTY_NAMESPACE = "";
   private static final String CDATA_PREFIX = "<![CDATA[";
   private static final String CDATA_POSTFIX = "]]>";

   /**
    * Serialize the configuration to as a Document to the
    * specified ContentHandler. The serialization writes
    * out an Element for each configuration object.
    *
    * @param handler the ContentHandler to write Configuration out to
    * @param configuration the Configuration
    * @throws SAXException if the handler throws an exception
    */
   public void serialize( final Configuration configuration,
                          final ContentHandler handler )
      throws SAXException
   {
      handler.startDocument();
      serializeElement( configuration, handler );
      handler.endDocument();
   }

   /**
    * Serialize the configuration as an Element to
    * specified ContentHandler.
    *
    * @param handler the ContentHandler to write Configuration out to
    * @param configuration the Configuration
    * @throws SAXException if the handler throws an exception
    */
   void serializeElement( final Configuration configuration,
                          final ContentHandler handler )
      throws SAXException
   {
      final AttributesImpl attributes = new AttributesImpl();
      final String[] names = configuration.getAttributeNames();
      for ( int i = 0; i < names.length; i++ )
      {
         final String name = names[ i ];
         final String value = configuration.getAttribute( name, "" );
         attributes.addAttribute( EMPTY_NAMESPACE, name, name, CDATA_TYPE, value );
      }

      final String name = configuration.getName();
      handler.startElement( EMPTY_NAMESPACE, name, name, attributes );

      String value = configuration.getValue( null );
      if ( null == value )
      {
         final Configuration[] children = configuration.getChildren();
         for ( int i = 0; i < children.length; i++ )
         {
            serializeElement( children[ i ], handler );
         }
      }
      else
      {
         if ( needsEscaping( value ) )
         {
            value = CDATA_PREFIX + value + CDATA_POSTFIX;
         }
         handler.characters( value.toCharArray(), 0, value.length() );
      }

      handler.endElement( EMPTY_NAMESPACE, name, name );
   }

   /**
    * Determine whether the specified value string needs to
    * be escaped in a CDATA section to produce valid XML.
    *
    * @param value the string value
    * @return true if value needs escaping, false otherwise
    */
   boolean needsEscaping( final String value )
   {
      //TODO: Implement this
      return false;
   }
}
