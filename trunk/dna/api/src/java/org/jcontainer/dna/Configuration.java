/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna;

/**
 * The configuration object represents hierarchial configuration
 * data. The data represented by this object is a simplified XML
 * format. Configuration objects are unable to represent namespace
 * information and elements can not have mixed content. ie
 * Configuration elements can not have both a value and child
 * elements.
 *
 * @version $Revision: 1.3 $ $Date: 2003-09-05 06:23:11 $
 */
public interface Configuration
{
   /**
    * Return the name of the configuration element.
    *
    * @return the name of the configuration element.
    */
   String getName();

   /**
    * Return the path to the configuration element.
    * The path should be in the xpath form but may
    * be the empty string if unabel to determine path.
    *
    * @return the path to the configuration element.
    */
   String getPath();

   /**
    * Return the location of configuration element.
    * Usually of the form "uri[:line number[:column number]]"
    * if possible. ie "file:myFile.xml:80:2". However the line
    * number and column number may be elided if unavailable.
    *
    * @return the location of configuration element.
    */
   String getLocation();

   /**
    * Return an array of all the child elements.
    *
    * @return an array of all the child elements.
    */
   Configuration[] getChildren();

   /**
    * Return an array of all the child elements with specified name.
    *
    * @return an array of all the child elements with specified name.
    */
   Configuration[] getChildren( String name );

   /**
    * Return a child Configuration element with specified name.
    * If no such element exists an element will be autocreated.
    *
    * @return a child Configuration element with specified name.
    */
   Configuration getChild( String name );

   /**
    * Return a child Configuration element with specified name.
    * If no such element exists and createChild is true then an
    * element will be autocreated otherwise null will be returned.
    *
    * @return a child Configuration element with specified name.
    */
   Configuration getChild( String name, boolean createChild );

   /**
    * Return text value of element.
    *
    * @return the value
    * @throws ConfigurationException if no value in element
    */
   String getValue()
      throws ConfigurationException;

   /**
    * Return text value of element.
    * Use specified default if no value in element.
    *
    * @param defaultValue the default value
    * @return the value
    */
   String getValue( String defaultValue );

   /**
    * Return text value of element as an integer.
    *
    * @return the value
    * @throws ConfigurationException if no value in element
    *         or value can not be converted to correct type
    */
   int getValueAsInteger()
      throws ConfigurationException;

   /**
    * Return text value of element as an integer.
    * Use specified default if no value in element or
    * value can not be converted to correct type.
    *
    * @param defaultValue the default value
    * @return the value
    */
   int getValueAsInteger( int defaultValue );

   /**
    * Return text value of element as a long.
    *
    * @return the value
    * @throws ConfigurationException if no value in element
    *         or value can not be converted to correct type
    */
   long getValueAsLong()
      throws ConfigurationException;

   /**
    * Return text value of element as a long.
    * Use specified default if no value in element or
    * value can not be converted to correct type.
    *
    * @param defaultValue the default value
    * @return the value
    */
   long getValueAsLong( long defaultValue );

   /**
    * Return text value of element as a boolean.
    *
    * @return the value
    * @throws ConfigurationException if no value in element
    *         or value can not be converted to correct type
    */
   boolean getValueAsBoolean()
      throws ConfigurationException;

   /**
    * Return text value of element as a boolean.
    * Use specified default if no value in element or
    * value can not be converted to correct type.
    *
    * @param defaultValue the default value
    * @return the value
    */
   boolean getValueAsBoolean( boolean defaultValue );

   /**
    * Return text value of element as a float.
    *
    * @return the value
    * @throws ConfigurationException if no value in element
    *         or value can not be converted to correct type
    */
   float getValueAsFloat()
      throws ConfigurationException;

   /**
    * Return text value of element as a float.
    * Use specified default if no value in element or
    * value can not be converted to correct type.
    *
    * @param defaultValue the default value
    * @return the value
    */
   float getValueAsFloat( float defaultValue );

   /**
    * Return an array of all the attribute names.
    *
    * @return an array of all the attribute names.
    */
   String[] getAttributeNames();

   /**
    * Return attribute value with specified name.
    *
    * @param name the attribute name
    * @return the attribute value
    * @throws ConfigurationException if no attribute with
    *         specified name
    */
   String getAttribute( String name )
      throws ConfigurationException;

   /**
    * Return attribute value with specified name.
    * If no attribute with specified name then return
    * default value.
    *
    * @param name the attribute name
    * @param defaultValue the default value
    * @return the attribute value
    */
   String getAttribute( String name, String defaultValue );

   /**
    * Return attribute value with specified name as an integer.
    *
    * @param name the attribute name
    * @return the attribute value
    * @throws ConfigurationException if no attribute with
    *         specified name or attribute can not be converted
    *         to correct type
    */
   int getAttributeAsInteger( String name )
      throws ConfigurationException;

   /**
    * Return attribute value with specified name as an integer.
    * If no attribute with specified name or attribute can
    * not be converted to correct type then return
    * default value.
    *
    * @param name the attribute name
    * @param defaultValue the default value
    * @return the attribute value
    */
   int getAttributeAsInteger( String name, int defaultValue );


   /**
    * Return attribute value with specified name as a long.
    *
    * @param name the attribute name
    * @return the attribute value
    * @throws ConfigurationException if no attribute with
    *         specified name or attribute can not be converted
    *         to correct type
    */
   long getAttributeAsLong( String name )
      throws ConfigurationException;

   /**
    * Return attribute value with specified name as a long.
    * If no attribute with specified name or attribute can
    * not be converted to correct type then return
    * default value.
    *
    * @param name the attribute name
    * @param defaultValue the default value
    * @return the attribute value
    */
   long getAttributeAsLong( String name, long defaultValue );


   /**
    * Return attribute value with specified name as a boolean.
    *
    * @param name the attribute name
    * @return the attribute value
    * @throws ConfigurationException if no attribute with
    *         specified name or attribute can not be converted
    *         to correct type
    */
   boolean getAttributeAsBoolean( String name )
      throws ConfigurationException;

   /**
    * Return attribute value with specified name as a boolean.
    * If no attribute with specified name or attribute can
    * not be converted to correct type then return
    * default value.
    *
    * @param name the attribute name
    * @param defaultValue the default value
    * @return the attribute value
    */
   boolean getAttributeAsBoolean( String name, boolean defaultValue );

   /**
    * Return attribute value with specified name as afloat.
    *
    * @param name the attribute name
    * @return the attribute value
    * @throws ConfigurationException if no attribute with
    *         specified name or attribute can not be converted
    *         to correct type
    */
   float getAttributeAsFloat( String name )
      throws ConfigurationException;

   /**
    * Return attribute value with specified name as a float.
    * If no attribute with specified name or attribute can
    * not be converted to correct type then return
    * default value.
    *
    * @param name the attribute name
    * @param defaultValue the default value
    * @return the attribute value
    */
   float getAttributeAsFloat( String name, float defaultValue );
}
