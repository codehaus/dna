package org.jcontainer.dna.impl;

import org.jcontainer.dna.LogEnabled;
import org.jcontainer.dna.Contextualizable;
import org.jcontainer.dna.Composable;
import org.jcontainer.dna.Parameterizable;
import org.jcontainer.dna.Configurable;
import org.jcontainer.dna.Active;
import org.jcontainer.dna.Logger;
import org.jcontainer.dna.ResourceLocator;
import org.jcontainer.dna.MissingResourceException;
import org.jcontainer.dna.Parameters;
import org.jcontainer.dna.ParameterException;
import org.jcontainer.dna.Configuration;
import org.jcontainer.dna.ConfigurationException;

class MockComponent
   implements LogEnabled, Contextualizable, Composable, Parameterizable, Configurable, Active
{
   private Logger m_logger;
   private ResourceLocator m_context;
   private ResourceLocator m_services;
   private Parameters m_parameters;
   private Configuration m_configuration;
   private boolean m_initialized;
   private boolean m_disposed;

   public void enableLogging( Logger logger )
   {
      m_logger = logger;
   }

   public void contextualize( ResourceLocator locator )
      throws MissingResourceException
   {
      m_context = locator;
   }

   public void compose( ResourceLocator locator )
      throws MissingResourceException
   {
      m_services = locator;
   }

   public void parameterize( Parameters parameters )
      throws ParameterException
   {
      m_parameters = parameters;
   }

   public void configure( Configuration configuration )
      throws ConfigurationException
   {
      m_configuration = configuration;
   }

   public void initialize()
      throws Exception
   {
      m_initialized = true;
   }

   public void dispose()
      throws Exception
   {
      m_disposed = true;
   }

   Logger getLogger()
   {
      return m_logger;
   }

   ResourceLocator getContext()
   {
      return m_context;
   }

   ResourceLocator getServices()
   {
      return m_services;
   }

   Parameters getParameters()
   {
      return m_parameters;
   }

   Configuration getConfiguration()
   {
      return m_configuration;
   }

   boolean isInitialized()
   {
      return m_initialized;
   }

   boolean isDisposed()
   {
      return m_disposed;
   }
}
