/*
 * Copyright (C) The DNA Group. All rights reserved.
 *
 * This software is published under the terms of the DNA
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.tools.verifier;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.codehaus.dna.Active;
import org.codehaus.dna.Composable;
import org.codehaus.dna.Configurable;
import org.codehaus.dna.Configuration;
import org.codehaus.dna.LogEnabled;
import org.codehaus.dna.ResourceLocator;
import org.codehaus.metaclass.Attributes;
import org.codehaus.metaclass.model.Attribute;

/**
 * Utility class to help verify that component respects the
 * rules of an DNA component.
 *
 * @author Peter Donald
 * @version $Revision: 1.2 $ $Date: 2004-05-01 09:51:48 $
 */
public class ComponentVerifier
{
    /**
     * Key used to look up ResourceBundle.
     */
    private static final String BASE_NAME =
        ComponentVerifier.class.getName() + "Resources";

    /**
     * The resource bundle.
     */
    private static final ResourceBundle BUNDLE =
        ResourceBundle.getBundle( BASE_NAME );

    /**
     * Constant for array of 0 classes. Saves recreating array everytime
     * look up constructor with no args.
     */
    private static final Class[] EMPTY_TYPES = new Class[ 0 ];

    /**
     * The interfaces representing lifecycle stages.
     */
    private static final Class[] FRAMEWORK_CLASSES = new Class[]
    {
        LogEnabled.class,
        Composable.class,
        Configurable.class,
        Active.class
    };

    /**
     * The name of the Configurable.configure() method.
     */
    private static final String CONFIGURE_METHOD_NAME = "configure";

    /**
     * The parameter types of the Configurable.configure() method.
     */
    private static final Class[] CONFIGURE_PARAMETER_TYPES = new Class[]{Configuration.class};

    /**
     * The name of the Composable.compose() method.
     */
    private static final String COMPOSE_METHOD_NAME = "compose";

    /**
     * The parameter types of the Composable.compose() method.
     */
    private static final Class[] COMPOSE_PARAMETER_TYPES = new Class[]{ResourceLocator.class};

    /**
     * Verfiy that specified components designate classes that implement the
     * advertised interfaces.
     *
     * @param type the component type
     * @return an array of issues
     */
    public VerifyIssue[] verifyType( final Class type )
    {
        final List issues = new ArrayList();
        verifyMetaData( type, issues );
        verifyDependencyMetaData( type, issues );
        verifyConfigurationMetaData( type, issues );

        final Class[] interfaces = getServiceClasses( type, issues );

        verifyClass( type, issues );
        verifyImplementsServices( type, interfaces, issues );
        for( int i = 0; i < interfaces.length; i++ )
        {
            verifyService( interfaces[ i ], issues );
        }

        return (VerifyIssue[])issues.
            toArray( new VerifyIssue[ issues.size() ] );
    }

    /**
     * Verify that the component is annotated with the
     * dna.component metadata.
     *
     * @param type the type
     * @param issues the list of issues
     */
    void verifyMetaData( final Class type, final List issues )
    {
        final Attribute attribute =
            Attributes.getAttribute( type, "dna.component" );
        if( null == attribute )
        {
            final String message = getMessage( "CV001" );
            final VerifyIssue issue =
                new VerifyIssue( VerifyIssue.ERROR, message );
            issues.add( issue );
        }
    }

    /**
     * Verify that the configuration metadata for component is valid.
     *
     * @param type the type
     * @param issues the list of issues
     */
    void verifyConfigurationMetaData( final Class type, final List issues )
    {
        Attribute attribute = getConfigurationMetaData( type );
        if( null != attribute )
        {
            final String location = attribute.getParameter( "location" );
            if( null == location )
            {
                final Object[] args = new Object[]{"type"};
                final String message = getMessage( "CV019", args );
                final VerifyIssue issue =
                    new VerifyIssue( VerifyIssue.ERROR, message );
                issues.add( issue );
            }
            else
            {
                verifyLocation( type, location, issues );
            }
        }
    }

    /**
     * Return the configuration metadata for component if any.
     * Protected to allow overiding in subclasses.
     *
     * @param type the component type
     * @return the metadata if any
     */
    protected Attribute getConfigurationMetaData( final Class type )
    {
        try
        {
            final Method method = getConfigurationMethod( type );
            return Attributes.getAttribute( method, "dna.configuration" );
        }
        catch( final NoSuchMethodException nsme )
        {
            return null;
        }
    }

    /**
     * Return the method used to pass configuration to component.
     *
     * @param type the components type
     * @return the method
     * @throws NoSuchMethodException if unable to locate method
     */
    protected Method getConfigurationMethod( final Class type )
        throws NoSuchMethodException
    {
        if( !Configurable.class.isAssignableFrom( type ) )
        {
            throw new NoSuchMethodException();
        }
        return type.getMethod( CONFIGURE_METHOD_NAME,
                               CONFIGURE_PARAMETER_TYPES );
    }

    /**
     * Verify that the location specified for the schema actually exists.
     *
     * @param type the component type
     * @param location the location of schmea
     * @param issues the list of issues
     */
    void verifyLocation( final Class type,
                         final String location,
                         final List issues )
    {
        final URL url = type.getResource( location );
        if( null == url )
        {
            final Object[] args = new Object[]{location};
            final String message = getMessage( "CV020", args );
            final VerifyIssue issue =
                new VerifyIssue( VerifyIssue.ERROR, message );
            issues.add( issue );
        }
    }

    /**
     * Verify that the dependency metadata for component is valid.
     *
     * @param type the type
     * @param issues the list of issues
     */
    void verifyDependencyMetaData( final Class type, final List issues )
    {
        final Attribute[] attributes = getDependencyAttributes( type );
        for( int i = 0; i < attributes.length; i++ )
        {
            final Attribute attribute = attributes[ i ];
            verifyDependencyMetaData( type, attribute, issues );
        }
    }

    /**
     * Return the dependency attributes for component.
     * Made protected so that it is possible to overload
     * in subclasses.
     *
     * @param type the component type
     * @return the dependency attributes
     */
    protected Attribute[] getDependencyAttributes( final Class type )
    {
        try
        {
            final Method method = getComposeMethod( type );
            return Attributes.getAttributes( method, "dna.dependency" );
        }
        catch( NoSuchMethodException e )
        {
            return Attribute.EMPTY_SET;
        }
    }

    /**
     * Return the method via which component is passed services.
     * This method is protected so can overload in subclasses
     * and get alternative methods.
     *
     * @param type the components type
     * @return the method
     * @throws NoSuchMethodException if can not locate method
     */
    protected Method getComposeMethod( final Class type )
        throws NoSuchMethodException
    {
        if( !Composable.class.isAssignableFrom( type ) )
        {
            throw new NoSuchMethodException();
        }
        return type.getMethod( COMPOSE_METHOD_NAME, COMPOSE_PARAMETER_TYPES );
    }

    /**
     * Verify that the dependency metadata tag is valid.
     *
     * @param type the component type
     * @param attribute the metadata tag
     * @param issues the list of issues
     */
    void verifyDependencyMetaData( final Class type,
                                   final Attribute attribute,
                                   final List issues )
    {
        final String optional = attribute.getParameter( "optional" );
        verifyOptionalParameter( optional, issues );

        final String typeName = attribute.getParameter( "type" );
        if( null == typeName )
        {
            final Object[] args = new Object[]{"type"};
            final String message = getMessage( "CV015", args );
            final VerifyIssue issue =
                new VerifyIssue( VerifyIssue.ERROR, message );
            issues.add( issue );
        }
        else
        {
            verifyDependencyType( type, typeName, issues );
            final String key = attribute.getParameter( "key" );
            if( null == key )
            {
                final Object[] args = new Object[]{"key"};
                final String message = getMessage( "CV015", args );
                final VerifyIssue issue =
                    new VerifyIssue( VerifyIssue.ERROR, message );
                issues.add( issue );
            }
            else
            {
                verifyDependencyKeyConforms( typeName, key, issues );
            }
        }
    }

    /**
     * Verify optional parameter for dependency metadata.
     *
     * @param optional the value of parameter
     * @param issues the list of issues
     */
    void verifyOptionalParameter( final String optional, final List issues )
    {
        if( null == optional )
        {
            final Object[] args = new Object[]{"optional"};
            final String message = getMessage( "CV015", args );
            final VerifyIssue issue =
                new VerifyIssue( VerifyIssue.ERROR, message );
            issues.add( issue );
        }
        else
        {
            verifyDependencyOptionalValid( optional, issues );
        }
    }

    /**
     * Verify that value of optional value is valid.
     *
     * @param optional the optional value
     * @param issues the list of issues
     */
    void verifyDependencyOptionalValid( final String optional,
                                        final List issues )
    {
        if( !optional.equals( "true" ) && !optional.equals( "false" ) )
        {
            final Object[] args = new Object[]{optional};
            final String message = getMessage( "CV018", args );
            final VerifyIssue issue =
                new VerifyIssue( VerifyIssue.ERROR, message );
            issues.add( issue );
        }
    }

    /**
     * Verify that the key conforms to the expectation
     * of being (type)[/(qualifier)]
     *
     * @param typeName the name of dependency type
     * @param key the dependency key
     * @param issues the list of issues
     */
    void verifyDependencyKeyConforms( final String typeName,
                                      final String key,
                                      final List issues )
    {
        final int typeLength = typeName.length();
        final int keyLength = key.length();
        final String prefix;
        if( typeLength == keyLength )
        {
            prefix = typeName;
        }
        else
        {
            prefix = typeName + "/";
        }
        if( !key.startsWith( prefix ) )
        {
            final Object[] args = new Object[]{key};
            final String message = getMessage( "CV017", args );
            final VerifyIssue issue =
                new VerifyIssue( VerifyIssue.NOTICE, message );
            issues.add( issue );
        }
    }

    /**
     * Verify that the type specified dependency can be loaded.
     *
     * @param type the component type
     * @param typeName the type of dependency
     * @param issues the list of issues
     */
    void verifyDependencyType( final Class type,
                               final String typeName,
                               final List issues )
    {
        try
        {
            type.getClassLoader().loadClass( typeName );
        }
        catch( final Throwable t )
        {
            final Object[] args = new Object[]{typeName, t};
            final String message = getMessage( "CV016", args );
            final VerifyIssue issue =
                new VerifyIssue( VerifyIssue.ERROR, message );
            issues.add( issue );
        }
    }

    /**
     * Verify that the supplied implementation implements the specified
     * services.
     *
     * @param implementation the class representign component
     * @param services the services that the implementation must provide
     * @param issues the list of issues
     */
    void verifyImplementsServices( final Class implementation,
                                   final Class[] services,
                                   final List issues )
    {
        for( int i = 0; i < services.length; i++ )
        {
            if( !services[ i ].isAssignableFrom( implementation ) )
            {
                final Object[] args = new Object[]{services[ i ].getName()};
                final String message = getMessage( "CV002", args );
                final VerifyIssue issue =
                    new VerifyIssue( VerifyIssue.ERROR, message );
                issues.add( issue );
            }
        }
    }

    /**
     * Verify that the supplied class is a valid type for
     * a component.
     *
     * @param type the class representing component
     * @param issues the list of issues
     */
    void verifyClass( final Class type, final List issues )
    {
        verifyNoArgConstructor( type, issues );
        verifyNonAbstract( type, issues );
        verifyNonArray( type, issues );
        verifyNonInterface( type, issues );
        verifyNonPrimitive( type, issues );
        verifyPublic( type, issues );
    }

    /**
     * Verify that the supplied class is a valid class for
     * a service.
     *
     * @param clazz the class representign service
     * @param issues the list of issues
     */
    void verifyService( final Class clazz, final List issues )
    {
        verifyServiceIsaInterface( clazz, issues );
        verifyServiceIsPublic( clazz, issues );
        verifyServiceNotALifecycle( clazz, issues );
    }

    /**
     * Verify that the service implemented by
     * specified component is an interface.
     *
     * @param clazz the class representign service
     * @param issues the list of issues
     */
    void verifyServiceIsaInterface( final Class clazz, final List issues )
    {
        if( !clazz.isInterface() )
        {
            final Object[] args = new Object[]{clazz.getName()};
            final String message = getMessage( "CV004", args );
            final VerifyIssue issue =
                new VerifyIssue( VerifyIssue.ERROR, message );
            issues.add( issue );
        }
    }

    /**
     * Verify that the service implemented by
     * specified component is public.
     *
     * @param clazz the class representign service
     * @param issues the list of issues
     */
    void verifyServiceIsPublic( final Class clazz, final List issues )
    {
        final boolean isPublic =
            Modifier.isPublic( clazz.getModifiers() );
        if( !isPublic )
        {
            final Object[] args = new Object[]{clazz.getName()};
            final String message = getMessage( "CV005", args );
            final VerifyIssue issue =
                new VerifyIssue( VerifyIssue.ERROR, message );
            issues.add( issue );
        }
    }

    /**
     * Verify that the service implemented by
     * specified component does not extend any lifecycle interfaces.
     *
     * @param clazz the class representign service
     * @param issues the list of issues
     */
    void verifyServiceNotALifecycle( final Class clazz, final List issues )
    {
        for( int i = 0; i < FRAMEWORK_CLASSES.length; i++ )
        {
            final Class lifecycle = FRAMEWORK_CLASSES[ i ];
            if( lifecycle.isAssignableFrom( clazz ) )
            {
                final Object[] args =
                    new Object[]{clazz.getName(), lifecycle.getName()};
                final String message = getMessage( "CV006", args );
                final VerifyIssue issue =
                    new VerifyIssue( VerifyIssue.ERROR, message );
                issues.add( issue );
            }
        }
    }

    /**
     * Verify that the component has a no-arg aka default
     * constructor.
     *
     * @param clazz the class representign component
     * @param issues the list of issues
     */
    void verifyNoArgConstructor( final Class clazz, final List issues )
    {
        try
        {
            clazz.getConstructor( EMPTY_TYPES );
        }
        catch( final NoSuchMethodException nsme )
        {
            final String message = getMessage( "CV008" );
            final VerifyIssue issue =
                new VerifyIssue( VerifyIssue.ERROR, message );
            issues.add( issue );
        }
    }

    /**
     * Verify that the component is not represented by
     * abstract class.
     *
     * @param clazz the class representign component
     * @param issues the list of issues
     */
    void verifyNonAbstract( final Class clazz, final List issues )
    {
        final boolean isAbstract =
            Modifier.isAbstract( clazz.getModifiers() );
        if( isAbstract )
        {
            final String message = getMessage( "CV009" );
            final VerifyIssue issue =
                new VerifyIssue( VerifyIssue.ERROR, message );
            issues.add( issue );
        }
    }

    /**
     * Verify that the component is not represented by
     * abstract class.
     *
     * @param clazz the class representign component
     * @param issues the list of issues
     */
    void verifyPublic( final Class clazz, final List issues )
    {
        final boolean isPublic =
            Modifier.isPublic( clazz.getModifiers() );
        if( !isPublic )
        {
            final String message = getMessage( "CV010" );
            final VerifyIssue issue =
                new VerifyIssue( VerifyIssue.ERROR, message );
            issues.add( issue );
        }
    }

    /**
     * Verify that the component is not represented by
     * primitive class.
     *
     * @param clazz the class representign component
     * @param issues the list of issues
     */
    void verifyNonPrimitive( final Class clazz, final List issues )
    {
        if( clazz.isPrimitive() )
        {
            final String message = getMessage( "CV011" );
            final VerifyIssue issue =
                new VerifyIssue( VerifyIssue.ERROR, message );
            issues.add( issue );
        }
    }

    /**
     * Verify that the component is not represented by
     * interface class.
     *
     * @param clazz the class representign component
     * @param issues the list of issues
     */
    void verifyNonInterface( final Class clazz, final List issues )
    {
        if( clazz.isInterface() )
        {
            final String message = getMessage( "CV012" );
            final VerifyIssue issue =
                new VerifyIssue( VerifyIssue.ERROR, message );
            issues.add( issue );
        }
    }

    /**
     * Verify that the component is not represented by
     * an array class.
     *
     * @param clazz the class representign component
     * @param issues the list of issues
     */
    void verifyNonArray( final Class clazz, final List issues )
    {
        if( clazz.isArray() )
        {
            final String message = getMessage( "CV013" );
            final VerifyIssue issue =
                new VerifyIssue( VerifyIssue.ERROR, message );
            issues.add( issue );
        }
    }

    /**
     * Retrieve an array of Classes for all the services that a Component
     * offers. This method also makes sure all services offered are
     * interfaces.
     *
     * @param type the component type
     * @param issues the list of issues
     * @return an array of Classes for all the services
     */
    Class[] getServiceClasses( final Class type, final List issues )
    {
        final List services = new ArrayList();
        final ClassLoader classLoader = type.getClassLoader();
        final Attribute[] attributes =
            Attributes.getAttributes( type, "dna.service" );
        for( int i = 0; i < attributes.length; i++ )
        {
            final String classname = attributes[ i ].getParameter( "type" );
            try
            {
                final Class clazz = classLoader.loadClass( classname );
                services.add( clazz );
            }
            catch( final Throwable t )
            {
                final Object[] args = new Object[]{classname, t};
                final String message = getMessage( "CV014", args );
                final VerifyIssue issue =
                    new VerifyIssue( VerifyIssue.ERROR, message );
                issues.add( issue );
            }
        }

        return (Class[])services.toArray( new Class[ services.size() ] );
    }

    /**
     * Get message out of resource bundle with specified key.
     *
     * @param key the key
     * @return the message
     */
    String getMessage( final String key )
    {
        return BUNDLE.getString( key );
    }

    /**
     * Get message out of resource bungle with specified key
     * and format wit specified arguments.
     *
     * @param key the keys
     * @param args the arguments
     * @return the message
     */
    String getMessage( final String key, final Object[] args )
    {
        final String pattern = getMessage( key );
        return MessageFormat.format( pattern, args );
    }
}
