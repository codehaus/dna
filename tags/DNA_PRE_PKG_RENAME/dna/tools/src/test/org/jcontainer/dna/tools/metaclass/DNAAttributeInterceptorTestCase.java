/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.tools.metaclass;

import com.thoughtworks.qdox.model.JavaMethod;
import java.util.Properties;
import junit.framework.TestCase;
import org.codehaus.metaclass.model.Attribute;

/**
 *
 * @author Peter Donald
 * @version $Revision: 1.4 $ $Date: 2004-04-18 14:44:19 $
 */
public class DNAAttributeInterceptorTestCase
    extends TestCase
{
    public void testProcessClassAttributeWithoutTransformations()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();
        final Attribute attribute = new Attribute( "ignored" );
        final Attribute result =
            interceptor.processClassAttribute( new MockJavaClass(), attribute );
        assertNotNull( "attribute", result );
        assertEquals( "attribute.name", "ignored", result.getName() );
        assertEquals( "attribute.value", null, result.getValue() );
        assertEquals( "attribute.parameterCount", 0, result.getParameterCount() );
    }

    public void testProcessClassAttributeWithDNAService()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();
        final Properties parameters = new Properties();
        parameters.setProperty( "type", "X" );
        final Attribute attribute = new Attribute( "dna.service", parameters );
        final Attribute result =
            interceptor.processClassAttribute( new MockJavaClass(), attribute );
        assertNotNull( "attribute", result );
        assertEquals( "attribute.name", "dna.service", result.getName() );
        assertEquals( "attribute.value", null, result.getValue() );
        assertEquals( "attribute.parameterCount", 1, result.getParameterCount() );
        assertEquals( "attribute.parameter(type)",
                      MockJavaSource.PREFIX + "X", result.getParameter( "type" ) );
    }

    public void testProcessClassAttributeWithDNAServiceMissingType()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();
        final Attribute attribute = new Attribute( "dna.service" );
        try
        {
            final MockJavaClass clazz = new MockJavaClass();
            clazz.setName( "Blah" );
            interceptor.processClassAttribute( clazz, attribute );
        }
        catch( final IllegalArgumentException iae )
        {
            return;
        }
        fail( "Expected to fail to process dna.service " +
              "attribute missing type parameter" );
    }

    public void testProcessMethodAttributeWithDNAConfigurationWith()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();
        final Attribute attribute = new Attribute( "ignored" );
        final Attribute result =
            interceptor.processMethodAttribute( new JavaMethod(), attribute );
        assertNotNull( "attribute", result );
        assertEquals( "attribute.name", "ignored", result.getName() );
        assertEquals( "attribute.value", null, result.getValue() );
        assertEquals( "attribute.parameterCount", 0, result.getParameterCount() );
    }

    public void testProcessMethodAttributeWithoutTransformations()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();
        final Attribute attribute = new Attribute( "ignored" );
        final Attribute result =
            interceptor.processMethodAttribute( new JavaMethod(), attribute );
        assertNotNull( "attribute", result );
        assertEquals( "attribute.name", "ignored", result.getName() );
        assertEquals( "attribute.value", null, result.getValue() );
        assertEquals( "attribute.parameterCount", 0, result.getParameterCount() );
    }

    public void testProcessMethodForConfiguration()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();

        final MockJavaClass clazz = new MockJavaClass();
        clazz.setName( "Blah" );

        final JavaMethod javaMethod = new JavaMethod();
        javaMethod.setParentClass( clazz );
        final Attribute attribute = new Attribute( "dna.configuration" );
        final Attribute result =
            interceptor.processMethodAttribute( javaMethod, attribute );
        assertNotNull( "attribute", result );
        assertEquals( "attribute.name", "dna.configuration", result.getName() );
        assertEquals( "attribute.value", null, result.getValue() );
        assertEquals( "attribute.parameterCount", 1, result.getParameterCount() );
        assertEquals( "attribute.parameter(location)",
                      "Blah-schema.xml", result.getParameter( "location" ) );
    }

    public void testProcessMethodForParameters()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();

        final MockJavaClass clazz = new MockJavaClass();
        clazz.setName( "Blah" );

        final JavaMethod javaMethod = new JavaMethod();
        javaMethod.setParentClass( clazz );
        final Attribute attribute = new Attribute( "dna.parameters" );
        final Attribute result =
            interceptor.processMethodAttribute( javaMethod, attribute );
        assertNotNull( "attribute", result );
        assertEquals( "attribute.name", "dna.parameters", result.getName() );
        assertEquals( "attribute.value", null, result.getValue() );
        assertEquals( "attribute.parameterCount", 1, result.getParameterCount() );
        assertEquals( "attribute.parameter(location)",
                      "Blah-schema.xml", result.getParameter( "location" ) );
    }

    public void testProcessMethodForDependencySpecifyingOptionalAttribute()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();
        final Properties parameters = new Properties();
        parameters.setProperty( "type", "Foo" );
        parameters.setProperty( "optional", "true" );
        final Attribute attribute = new Attribute( "dna.dependency", parameters );
        final JavaMethod method = new JavaMethod();
        method.setParentClass( new MockJavaClass() );
        final Attribute result =
            interceptor.processMethodAttribute( method, attribute );
        assertNotNull( "attribute", result );
        assertEquals( "attribute.name", "dna.dependency", result.getName() );
        assertEquals( "attribute.value", null, result.getValue() );
        assertEquals( "attribute.parameterCount", 3, result.getParameterCount() );
        assertEquals( "attribute.parameter(type)",
                      MockJavaSource.PREFIX + "Foo",
                      result.getParameter( "type" ) );
        assertEquals( "attribute.parameter(key)",
                      MockJavaSource.PREFIX + "Foo",
                      result.getParameter( "key" ) );
        assertEquals( "attribute.parameter(optional)",
                      "true",
                      result.getParameter( "optional" ) );
    }

    public void testProcessMethodForDependencyNotSpecifyingType()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();
        final Properties parameters = new Properties();
        final Attribute attribute = new Attribute( "dna.dependency", parameters );
        final JavaMethod method = new JavaMethod();
        method.setParentClass( new MockJavaClass() );
        try
        {
            interceptor.processMethodAttribute( method, attribute );
        }
        catch( final IllegalArgumentException iae )
        {
            return;
        }
        fail( "Expected to fail as dna.dependency is missing typ[e parameter" );
    }

    public void testProcessMethodForDependencySpecifyingJustType()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();
        final Properties parameters = new Properties();
        parameters.setProperty( "type", "Foo" );
        final Attribute attribute = new Attribute( "dna.dependency", parameters );
        final JavaMethod method = new JavaMethod();
        method.setParentClass( new MockJavaClass() );
        final Attribute result =
            interceptor.processMethodAttribute( method, attribute );
        assertNotNull( "attribute", result );
        assertEquals( "attribute.name", "dna.dependency", result.getName() );
        assertEquals( "attribute.value", null, result.getValue() );
        assertEquals( "attribute.parameterCount", 3, result.getParameterCount() );
        assertEquals( "attribute.parameter(type)",
                      MockJavaSource.PREFIX + "Foo",
                      result.getParameter( "type" ) );
        assertEquals( "attribute.parameter(key)",
                      MockJavaSource.PREFIX + "Foo",
                      result.getParameter( "key" ) );
        assertEquals( "attribute.parameter(optional)",
                      "false",
                      result.getParameter( "optional" ) );
    }

    public void testProcessConfigurationAttributeWithoutLocationOrType()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();

        final MockJavaClass clazz = new MockJavaClass();
        clazz.setName( "Blah" );

        final Attribute attribute = new Attribute( "dna.configuration" );
        final Attribute result =
            interceptor.processConfigurationAttribute( clazz, attribute );
        assertNotNull( "attribute", result );
        assertEquals( "attribute.name", "dna.configuration", result.getName() );
        assertEquals( "attribute.value", null, result.getValue() );
        assertEquals( "attribute.parameterCount", 1, result.getParameterCount() );
        assertEquals( "attribute.parameter(location)",
                      "Blah-schema.xml", result.getParameter( "location" ) );
    }

    public void testProcessConfigurationAttributeWithLocation()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();

        final MockJavaClass clazz = new MockJavaClass();
        clazz.setName( "Blah" );

        final Properties parameters = new Properties();
        parameters.setProperty( "location", "Meep.xml" );
        final Attribute attribute = new Attribute( "dna.configuration", parameters );
        final Attribute result =
            interceptor.processConfigurationAttribute( clazz, attribute );
        assertNotNull( "attribute", result );
        assertEquals( "attribute.name", "dna.configuration", result.getName() );
        assertEquals( "attribute.value", null, result.getValue() );
        assertEquals( "attribute.parameterCount", 1, result.getParameterCount() );
        assertEquals( "attribute.parameter(location)",
                      "Meep.xml", result.getParameter( "location" ) );
    }

    public void testProcessConfigurationAttributeWithType()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();

        final MockJavaClass clazz = new MockJavaClass();
        clazz.setName( "Blah" );

        final Properties parameters = new Properties();
        parameters.setProperty( "type", "MeepSchemaLanguage" );
        final Attribute attribute = new Attribute( "dna.configuration", parameters );
        final Attribute result =
            interceptor.processConfigurationAttribute( clazz, attribute );
        assertNotNull( "attribute", result );
        assertEquals( "attribute.name", "dna.configuration", result.getName() );
        assertEquals( "attribute.value", null, result.getValue() );
        assertEquals( "attribute.parameterCount", 2, result.getParameterCount() );
        assertEquals( "attribute.parameter(location)",
                      "Blah-schema.xml", result.getParameter( "location" ) );
        assertEquals( "attribute.parameter(type)",
                      "MeepSchemaLanguage", result.getParameter( "type" ) );
    }

    public void testDetermineKeyWhenNothingSpecified()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();
        final Properties parameters = new Properties();
        final Attribute attribute = new Attribute( "dna.dependency", parameters );
        final String key = interceptor.determineKey( attribute, "com.biz.Foo" );
        assertEquals( "key", "com.biz.Foo", key );
    }

    public void testDetermineKeyWhenQualifierSpecified()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();
        final Properties parameters = new Properties();
        parameters.setProperty( "qualifier", "x" );
        final Attribute attribute = new Attribute( "dna.dependency", parameters );
        final String key = interceptor.determineKey( attribute, "com.biz.Foo" );
        assertEquals( "key", "com.biz.Foo/x", key );
    }

    public void testDetermineKeyWhenKeySpecified()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();
        final Properties parameters = new Properties();
        parameters.setProperty( "key", "x" );
        final Attribute attribute = new Attribute( "dna.dependency", parameters );
        final String key = interceptor.determineKey( attribute, "com.biz.Foo" );
        assertEquals( "key", "x", key );
    }

    public void testGetSchemaLocationForClassInDefaultPackage()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();
        final String location = interceptor.getSchemaLocationFor( "Foo" );
        assertEquals( "location", "Foo-schema.xml", location );
    }

    public void testGetSchemaLocationForClassInNonDefaultPackage()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();
        final String location = interceptor.getSchemaLocationFor( "com.biz.Foo" );
        assertEquals( "location", "Foo-schema.xml", location );
    }

    public void testResolveType()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();
        final String type = interceptor.resolveType( new MockJavaClass(), "X" );
        assertEquals( "type", MockJavaSource.PREFIX + "X", type );
    }

    public void testResolveTypeOnArray()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();
        final String type = interceptor.resolveType( new MockJavaClass(), "X[]" );
        assertEquals( "type", MockJavaSource.PREFIX + "X[]", type );
    }

    public void testResolveTypeOnMap()
        throws Exception
    {
        final DNAAttributeInterceptor interceptor = new DNAAttributeInterceptor();
        final String type = interceptor.resolveType( new MockJavaClass(), "X{}" );
        assertEquals( "type", MockJavaSource.PREFIX + "X{}", type );
    }
}
