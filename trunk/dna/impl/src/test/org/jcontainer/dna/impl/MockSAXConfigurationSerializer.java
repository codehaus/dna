package org.jcontainer.dna.impl;

import org.xml.sax.helpers.AttributesImpl;
import org.jcontainer.dna.Configuration;

public class MockSAXConfigurationSerializer
   extends SAXConfigurationSerializer
{
   static final AttributesImpl ATTRIBUTES = new AttributesImpl();

   AttributesImpl serializeAttributes( Configuration configuration )
   {
      return ATTRIBUTES;
   }
}
