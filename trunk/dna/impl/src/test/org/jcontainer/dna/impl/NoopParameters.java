package org.jcontainer.dna.impl;

import org.jcontainer.dna.Parameters;
import org.jcontainer.dna.ParameterException;

class NoopParameters
   implements Parameters
{
   public String[] getParameterNames()
   {
      return new String[ 0 ];
   }

   public boolean isParameter( String name )
   {
      return false;
   }

   public String getParameter( String name )
      throws ParameterException
   {
      return null;
   }

   public String getParameter( String name, String defaultValue )
   {
      return null;
   }

   public int getParameterAsInteger( String name )
      throws ParameterException
   {
      return 0;
   }

   public int getParameterAsInteger( String name, int defaultValue )
   {
      return 0;
   }

   public long getParameterAsLong( String name )
      throws ParameterException
   {
      return 0;
   }

   public long getParameterAsLong( String name, long defaultValue )
   {
      return 0;
   }

   public boolean getParameterAsBoolean( String name )
      throws ParameterException
   {
      return false;
   }

   public boolean getParameterAsBoolean( String name, boolean defaultValue )
   {
      return false;
   }

   public float getParameterAsFloat( String name )
      throws ParameterException
   {
      return 0;
   }

   public float getParameterAsFloat( String name, float defaultValue )
   {
      return 0;
   }

   public Parameters getChildParameters( String prefix )
   {
      return null;
   }
}
