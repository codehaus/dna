/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.tools.metaclass;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import java.util.Properties;
import org.realityforge.metaclass.model.Attribute;
import org.realityforge.metaclass.tools.qdox.DefaultQDoxAttributeInterceptor;
import org.realityforge.metaclass.tools.qdox.QDoxAttributeInterceptor;

/**
 * This is an Attribute interceptor that invoked during construction
 * of ClassDescriptors that will process DNA metadata. The processing
 * involves setting default values for required attributes and resolving
 * types in type-based attributes.
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.1 $ $Date: 2003-10-16 07:57:14 $
 */
public class DNAAttributeInterceptor
    extends DefaultQDoxAttributeInterceptor
    implements QDoxAttributeInterceptor
{
    /**
     * @see QDoxAttributeInterceptor#processClassAttribute(JavaClass, Attribute)
     */
    public Attribute processClassAttribute( final JavaClass clazz,
                                            final Attribute attribute )
    {
        final String name = attribute.getName();
        if( name.equals( "dna.service" ) )
        {
            final Properties parameters = new Properties();
            final String type = attribute.getParameter( "type", null );
            if( null == type )
            {
                final String message =
                    "dna.service attribute on class " +
                    clazz.getFullyQualifiedName() +
                    " must specify the parameter 'type'";
                throw new IllegalArgumentException( message );
            }
            final String resolvedType = resolveType( clazz, type );
            parameters.setProperty( "type", resolvedType );
            return new Attribute( "dna.service", parameters );
        }
        else
        {
            return attribute;
        }
    }

    /**
     * @see QDoxAttributeInterceptor#processMethodAttribute(JavaMethod, Attribute)
     */
    public Attribute processMethodAttribute( final JavaMethod method,
                                             final Attribute attribute )
    {
        final JavaClass clazz = method.getParentClass();
        final String name = attribute.getName();
        if( name.equals( "dna.configuration" ) ||
            name.equals( "dna.parameters" ) )
        {
            return processConfigurationAttribute( clazz, attribute );
        }
        else if( name.equals( "dna.dependency" ) )
        {
            final Properties parameters = new Properties();
            final String type = attribute.getParameter( "type", null );
            if( null == type )
            {
                final String message =
                    "dna.dependency attribute on class " +
                    clazz.getFullyQualifiedName() +
                    " must specify the parameter 'type'";
                throw new IllegalArgumentException( message );
            }
            final String resolvedType = resolveType( clazz, type );
            parameters.setProperty( "type", resolvedType );

            final String optional = attribute.getParameter( "optional", "false" );
            parameters.setProperty( "optional", optional );

            final String actualKey = determineKey( attribute, resolvedType );
            parameters.setProperty( "key", actualKey );

            return new Attribute( "dna.dependency", parameters );
        }
        else
        {
            return attribute;
        }
    }

    /**
     * Calculate key used to lookup dependency.
     *
     * @param attribute the attribute
     * @param resolvedType the type of dependency
     * @return the key
     */
    String determineKey( final Attribute attribute,
                         final String resolvedType )
    {
        final String key = attribute.getParameter( "key", null );
        final String qualifier = attribute.getParameter( "qualifier", null );
        if( null != key )
        {
            return key;
        }
        else if( null != qualifier )
        {
            return resolvedType + "/" + qualifier;
        }
        else
        {
            return resolvedType;
        }
    }

    /**
     * Process a Configuration or Parameters Attribute.
     *
     * @param clazz the associated JavaClass
     * @param attribute the attribute
     * @return the resultant attribute
     */
    Attribute processConfigurationAttribute( final JavaClass clazz,
                                             final Attribute attribute )
    {
        final String type =
            attribute.getParameter( "type", null );
        final String classname = clazz.getName();
        final String defaultLocation = getSchemaLocationFor( classname );
        final String location =
            attribute.getParameter( "location", defaultLocation );
        final Properties parameters = new Properties();
        if( null != type )
        {
            parameters.setProperty( "type", type );
        }
        parameters.setProperty( "location", location );
        return new Attribute( attribute.getName(), parameters );
    }

    /**
     * Get the location of the schema. By default it is "Foo-schema.xml"
     * for the com.biz.Foo component.
     *
     * @param classname the classname of component
     * @return the location of the schema
     */
    String getSchemaLocationFor( final String classname )
    {
        final int index = classname.lastIndexOf( "." );
        String location = classname;
        if( -1 != index )
        {
            location = classname.substring( index + 1 );
        }
        location += "-schema.xml";
        return location;
    }

    /**
     * Resolve the specified type.
     * Resolving essentially means finding the fully qualified name of
     * a class from just it's short name.
     *
     * @param javaClass the java class relative to which the type must be resolved
     * @param type the unresolved type
     * @return the resolved type
     */
    String resolveType( final JavaClass javaClass,
                        final String type )
    {
        return javaClass.getParentSource().resolveType( type );
    }
}
