/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.impl;

import org.jcontainer.dna.Configuration;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Utility class that serializes a Configuration object
 * to a SAX2 compliant ContentHandler.
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.6 $ $Date: 2003-09-23 09:51:47 $
 */
public class SAXConfigurationSerializer
{
   /**
    * Constant for CDATA type in attributes.
    */
   private static final String CDATA_TYPE = "CDATA";

   /**
    * Constant for empty namespace in attributes.
    */
   private static final String EMPTY_NAMESPACE = "";

   /**
    * Constant for start of CDATA content sections.
    */
   //private static final String CDATA_PREFIX = "<![CDATA[";

   /**
    * Constant for end of CDATA content sections.
    */
   //private static final String CDATA_POSTFIX = "]]>";

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
      final AttributesImpl attributes = serializeAttributes( configuration );

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
         /*if ( needsEscaping( value ) )
         {
            value = CDATA_PREFIX + value + CDATA_POSTFIX;
         }
         */
         handler.characters( value.toCharArray(), 0, value.length() );
      }

      handler.endElement( EMPTY_NAMESPACE, name, name );
   }

   /**
    * Serialize Configuration attributes to an AttributesImpl instance.
    *
    * @param configuration the configuration
    * @return the AttributesImpl instance
    */
   AttributesImpl serializeAttributes( final Configuration configuration )
   {
      final AttributesImpl attributes = new AttributesImpl();
      final String[] names = configuration.getAttributeNames();
      for ( int i = 0; i < names.length; i++ )
      {
         final String name = names[ i ];
         final String value = configuration.getAttribute( name, "" );
         attributes.addAttribute( EMPTY_NAMESPACE, name, name,
                                  CDATA_TYPE, value );
      }
      return attributes;
   }

   /**
    * Determine whether the specified value string needs to
    * be escaped in a CDATA section to produce valid XML.
    *
    * @param value the string value
    * @return true if value needs escaping, false otherwise
    */
   /*
   boolean needsEscaping( final String value )
   {
      return false;
   }
   */
}
