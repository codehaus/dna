/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.impl;

import java.util.HashMap;
import java.util.Map;

import org.jcontainer.dna.MissingResourceException;
import org.jcontainer.dna.ResourceLocator;

/**
 * ResourceLocator implementation backed by a Map and
 * optionally delegating to parent ResourceLocators.
 * The developer should create the DefaultResourceLocator,
 * associate resources with locator and then invoke
 * {@link #makeReadOnly()} before passing the Locator to
 * the client component.
 *
 * <p>The implementation will first check for resources
 * associated with itself and if unable to locate resource
 * locally it will delegate to parent ResourceLocator.</p>
 *
 * @version $Revision: 1.4 $ $Date: 2003-09-07 23:41:59 $
 */
public class DefaultResourceLocator
   extends AbstractFreezable
   implements ResourceLocator
{
   private final ResourceLocator m_parent;
   private final Map m_resources = new HashMap();

   /**
    * Create a ResourceLocator with no parent.
    */
   public DefaultResourceLocator()
   {
      this( null );
   }

   /**
    * Create a ResourceLocator with specified parent.
    *
    * @param parent the parent ResourceLocator
    */
   public DefaultResourceLocator( final ResourceLocator parent )
   {
      m_parent = parent;
   }

   /**
    * Return resource registered with specified key.
    *
    * @param key the key
    * @return the resource
    * @throws MissingResourceException if unable to locate
    *         resource with specified key
    */
   public Object lookup( final String key )
      throws MissingResourceException
   {
      final Object resource = getResourceMap().get( key );
      if ( null != resource )
      {
         return resource;
      }

      final ResourceLocator parent = getParent();
      if ( null != parent )
      {
         return parent.lookup( key );
      }
      else
      {
         final String message = "Unable to locate resource " + key + ".";
         throw new MissingResourceException( message, key );
      }
   }

   /**
    * Return true if a resource exists with specified key.
    *
    * @param key the key
    * @return true if a resource exists with specified key.
    */
   public boolean contains( final String key )
   {
      final Object resource = getResourceMap().get( key );
      if ( null != resource )
      {
         return true;
      }

      final ResourceLocator parent = getParent();
      if ( null != parent )
      {
         return parent.contains( key );
      }
      else
      {
         return false;
      }
   }

   /**
    * Add a resource to resource locator.
    *
    * @param key the key used to store resource
    * @param resource the resource (Must not be null).
    */
   public void put( final String key,
                    final Object resource )
   {
      checkWriteable();
      getResourceMap().put( key, resource );
   }

   /**
    * Return the parent ResourceLocator if any.
    *
    * @return the parent ResourceLocator if any.
    */
   protected final ResourceLocator getParent()
   {
      return m_parent;
   }

   /**
    * Return the map used to store resources.
    *
    * @return the map used to store resources.
    */
   protected final Map getResourceMap()
   {
      return m_resources;
   }
}
