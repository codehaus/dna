package org.jcontainer.dna.impl;

import junit.framework.TestCase;

public class AbstractFreezableTestCase
   extends TestCase
{
   public void testMakeReadOnly()
      throws Exception
   {
      final MockFreezable freezable = new MockFreezable();
      assertEquals( "freezable.isReadOnly() prior to makeReadOnly",
                    false,
                    freezable.isReadOnly() );
      freezable.makeReadOnly();
      assertEquals( "freezable.isReadOnly() after to makeReadOnly",
                    true,
                    freezable.isReadOnly() );
   }

   public void testCheckWriteable()
      throws Exception
   {
      final MockFreezable freezable = new MockFreezable();
      freezable.makeReadOnly();
      try
      {
         freezable.checkWriteable();
      }
      catch ( final IllegalStateException ise )
      {
         return;
      }
      fail( "Expected checkWriteable to throw an " +
            "IllegalStateException as freezable is" +
            "marked as read-only." );
   }
}
