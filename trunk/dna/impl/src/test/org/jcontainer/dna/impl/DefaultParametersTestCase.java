package org.jcontainer.dna.impl;

import junit.framework.TestCase;
import org.jcontainer.dna.ParameterException;

public class DefaultParametersTestCase
   extends TestCase
{
   public void testGetParameter()
      throws Exception
   {
      final DefaultParameters parameters = new DefaultParameters();
      final String name = "key";
      final String value = "value";
      parameters.setParameter( name, value );
      assertEquals( "parameters.isParameter( name )",
                    true,
                    parameters.isParameter( name ) );
      assertEquals( "parameters.getParameter( name, 'blah' )",
                    value,
                    parameters.getParameter( name, "blah" ) );
      assertEquals( "parameters.getParameter( name )",
                    value,
                    parameters.getParameter( name ) );
   }

   public void testGetMissingParameter()
      throws Exception
   {
      final DefaultParameters parameters = new DefaultParameters();
      final String name = "key";
      assertEquals( "parameters.isParameter( name )",
                    false,
                    parameters.isParameter( name ) );
      assertEquals( "parameters.getParameter( name, 'blah' )",
                    "blah",
                    parameters.getParameter( name, "blah" ) );
      try
      {
         parameters.getParameter( name );
         fail( "Expected getParameter on non existent parameter to fail" );
      }
      catch ( ParameterException e )
      {

      }
   }

   public void testGetParameterNames()
      throws Exception
   {
      final DefaultParameters parameters = new DefaultParameters();
      final String name = "key";
      final String value = "value";
      parameters.setParameter( name, value );
      final String[] names = parameters.getParameterNames();

      assertEquals( "names.length", 1, names.length );
      assertEquals( "names[0]", name, names[ 0 ] );
   }

   public void testGetParameterWithNullName()
      throws Exception
   {
      final DefaultParameters parameters = new DefaultParameters();
      try
      {
         parameters.getParameter( null );
      }
      catch ( NullPointerException npe )
      {
         assertEquals( "name", npe.getMessage() );
      }
      fail( "Expected getParameter(null) to fail due to passing null " );
   }

   public void testGetParameterWDefaultWithNullName()
      throws Exception
   {
      final DefaultParameters parameters = new DefaultParameters();
      try
      {
         parameters.getParameter( null, "blah" );
      }
      catch ( NullPointerException npe )
      {
         assertEquals( "name", npe.getMessage() );
      }
      fail( "Expected getParameter(null,'blah') to fail due to passing null " );
   }

   public void testsetParameterWithNullName()
      throws Exception
   {
      final DefaultParameters parameters = new DefaultParameters();
      try
      {
         parameters.setParameter( null, "blah" );
      }
      catch ( NullPointerException npe )
      {
         assertEquals( "name", npe.getMessage() );
      }
      fail( "Expected setParameter(null,'blah') to fail due to passing null " );
   }

   public void testsetParameterWithNullValue()
      throws Exception
   {
      final DefaultParameters parameters = new DefaultParameters();
      try
      {
         parameters.setParameter( "blah", null );
      }
      catch ( NullPointerException npe )
      {
         assertEquals( "value", npe.getMessage() );
      }
      fail( "Expected setParameter('blah',null) to fail due to passing null " );
   }

   public void testNullPassedIntoCtor()
      throws Exception
   {
      try
      {
         new DefaultParameters( null );
      }
      catch ( NullPointerException npe )
      {
         assertEquals( "prefix", npe.getMessage() );
      }
      fail( "Expected to fail due to passing null " );
   }

   public void testGetParameterAsBoolean()
      throws Exception
   {
      final DefaultParameters parameters = new DefaultParameters();
      final String name = "key";
      final String value = "true";
      parameters.setParameter( name, value );
      assertEquals( "parameters.getParameterAsBoolean( name, false )",
                    true,
                    parameters.getParameterAsBoolean( name, false ) );
      assertEquals( "parameters.getParameterAsBoolean( name )",
                    true,
                    parameters.getParameterAsBoolean( name ) );
   }

   public void testGetParameterAsInteger()
      throws Exception
   {
      final DefaultParameters parameters = new DefaultParameters();
      final String name = "key";
      final String value = "1";
      parameters.setParameter( name, value );
      assertEquals( "parameters.getParameterAsInteger( name, 3 )",
                    1,
                    parameters.getParameterAsInteger( name, 3 ) );
      assertEquals( "parameters.getParameterAsInteger( name )",
                    1,
                    parameters.getParameterAsInteger( name ) );
   }

   public void testGetParameterAsIntegerWithMalformedValue()
      throws Exception
   {
      final DefaultParameters parameters = new DefaultParameters();
      final String name = "key";
      final String value = "a";
      parameters.setParameter( name, value );
      assertEquals( "parameters.getParameterAsInteger( name, 3 )",
                    3,
                    parameters.getParameterAsInteger( name, 3 ) );
      try
      {
         parameters.getParameterAsInteger( name );
      }
      catch ( ParameterException e )
      {
         return;
      }
      fail( "Expected to fail as parameter is malformed" );
   }

   public void testGetParameterAsLong()
      throws Exception
   {
      final DefaultParameters parameters = new DefaultParameters();
      final String name = "key";
      final String value = "1";
      parameters.setParameter( name, value );
      assertEquals( "parameters.getParameterAsLong( name, 3 )",
                    1,
                    parameters.getParameterAsLong( name, 3 ) );
      assertEquals( "parameters.getParameterAsLong( name )",
                    1,
                    parameters.getParameterAsLong( name ) );
   }

   public void testGetParameterAsLongWithMalformedValue()
      throws Exception
   {
      final DefaultParameters parameters = new DefaultParameters();
      final String name = "key";
      final String value = "a";
      parameters.setParameter( name, value );
      assertEquals( "parameters.getParameterAsInteger( name, 3 )",
                    3,
                    parameters.getParameterAsLong( name, 3 ) );
      try
      {
         parameters.getParameterAsLong( name );
      }
      catch ( ParameterException e )
      {
         return;
      }
      fail( "Expected to fail as parameter is malformed" );
   }

   public void testGetParameterAsFloat()
      throws Exception
   {
      final DefaultParameters parameters = new DefaultParameters();
      final String name = "key";
      final String value = "1.0";
      parameters.setParameter( name, value );
      assertTrue( "parameters.getParameterAsFloat( name, 3 )",
                  1.0 == parameters.getParameterAsFloat( name, 3 ) );
      assertTrue( "parameters.getParameterAsFloat( name )",
                    1.0 == parameters.getParameterAsFloat( name ) );
   }

   public void testGetParameterAsFloatWithMalformedValue()
      throws Exception
   {
      final DefaultParameters parameters = new DefaultParameters();
      final String name = "key";
      final String value = "a";
      parameters.setParameter( name, value );
      assertTrue( "parameters.getParameterAsFloat( name, 3 )",
                    3.0 == parameters.getParameterAsFloat( name, 3 ) );
      try
      {
         parameters.getParameterAsFloat( name );
      }
      catch ( ParameterException e )
      {
         return;
      }
      fail( "Expected to fail as parameter is malformed" );
   }
}
