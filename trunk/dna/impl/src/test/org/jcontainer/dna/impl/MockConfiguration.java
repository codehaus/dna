package org.jcontainer.dna.impl;

import org.jcontainer.dna.Configuration;
import org.jcontainer.dna.ConfigurationException;

class MockConfiguration
   implements Configuration
{
   public String getName()
   {
      return null;
   }

   public String getPath()
   {
      return null;
   }

   public String getLocation()
   {
      return null;
   }

   public Configuration[] getChildren()
   {
      return new Configuration[ 0 ];
   }

   public Configuration[] getChildren( String name )
   {
      return new Configuration[ 0 ];
   }

   public Configuration getChild( String name )
   {
      return null;
   }

   public Configuration getChild( String name, boolean createChild )
   {
      return null;
   }

   public String getValue()
      throws ConfigurationException
   {
      return null;
   }

   public String getValue( String defaultValue )
   {
      return null;
   }

   public int getValueAsInteger()
      throws ConfigurationException
   {
      return 0;
   }

   public int getValueAsInteger( int defaultValue )
   {
      return 0;
   }

   public long getValueAsLong()
      throws ConfigurationException
   {
      return 0;
   }

   public long getValueAsLong( long defaultValue )
   {
      return 0;
   }

   public boolean getValueAsBoolean()
      throws ConfigurationException
   {
      return false;
   }

   public boolean getValueAsBoolean( boolean defaultValue )
   {
      return false;
   }

   public float getValueAsFloat()
      throws ConfigurationException
   {
      return 0;
   }

   public float getValueAsFloat( float defaultValue )
   {
      return 0;
   }

   public String[] getAttributeNames()
   {
      return new String[ 0 ];
   }

   public String getAttribute( String name )
      throws ConfigurationException
   {
      return null;
   }

   public String getAttribute( String name, String defaultValue )
   {
      return null;
   }

   public int getAttributeAsInteger( String name )
      throws ConfigurationException
   {
      return 0;
   }

   public int getAttributeAsInteger( String name, int defaultValue )
   {
      return 0;
   }

   public long getAttributeAsLong( String name )
      throws ConfigurationException
   {
      return 0;
   }

   public long getAttributeAsLong( String name, long defaultValue )
   {
      return 0;
   }

   public boolean getAttributeAsBoolean( String name )
      throws ConfigurationException
   {
      return false;
   }

   public boolean getAttributeAsBoolean( String name, boolean defaultValue )
   {
      return false;
   }

   public float getAttributeAsFloat( String name )
      throws ConfigurationException
   {
      return 0;
   }

   public float getAttributeAsFloat( String name, float defaultValue )
   {
      return 0;
   }
}
