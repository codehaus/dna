package org.jcontainer.dna.impl;

/**
 * Abstract utility class for resources that can be "frozen"
 * and made read-only after being constructed.
 *
 * @version $Revision: 1.1 $ $Date: 2003-09-07 23:33:02 $
 */
public class AbstractFreezable
   implements Freezable
{
   /**
    * Flag indicating whether resource has been
    * made read-only yet.
    */
   private boolean m_readOnly;

   /**
    * Mark the resource as read only.
    */
   public void makeReadOnly()
   {
      m_readOnly = true;
   }

   /**
    * Check if the resource has been "frozen"
    * and thus is read only. If read-only then
    * throw an IllegalStateException.
    *
    * @throws java.lang.IllegalStateException if resource is read-only
    */
   protected final void checkWriteable()
      throws IllegalStateException
   {
      if ( m_readOnly )
      {
         final String message =
            "Resource (" + this + ") is read only and can not be modified";
         throw new IllegalStateException( message );
      }
   }

   /**
    * Return true if resource has been made read-only or frozen.
    *
    * @return true if resource has been made read-only or
    *         frozen, false otherwise.
    */
   protected final boolean isReadOnly()
   {
      return m_readOnly;
   }
}
