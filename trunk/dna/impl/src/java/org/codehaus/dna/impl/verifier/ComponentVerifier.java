/*
 * Copyright (C) The DNA Group. All rights reserved.
 *
 * This software is published under the terms of the DNA
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl.verifier;

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
import org.codehaus.dna.annotation.ComponentDescriptor;
import org.codehaus.dna.annotation.ConfigurationDescriptor;
import org.codehaus.dna.annotation.DependencyDescriptor;
import org.codehaus.dna.annotation.DependencyDescriptorSet;
import org.codehaus.dna.annotation.ServiceDescriptor;
import org.codehaus.dna.annotation.ServiceDescriptorSet;

/**
 * Utility class to help verify that component respects the
 * rules of an DNA component.
 *
 * @author Peter Donald
 * @version $Revision: 1.1 $ $Date: 2005-03-31 09:41:42 $
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
    private static final Class[] EMPTY_TYPES = new Class[0];
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
     * Empty string used to test whether attributes have been specified.
     */
    private static final String EMPTY_STRING = "";

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
            verifyService( interfaces[i], issues );
        }

        return (VerifyIssue[])issues.
            toArray( new VerifyIssue[issues.size()] );
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
        if( !type.isAnnotationPresent( ComponentDescriptor.class ) )
        {
            final String message = getMessage( "CV001" );
            final VerifyIssue issue =
                new VerifyIssue( message, true );
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
        final ConfigurationDescriptor descriptor = getConfigurationMetaData( type );
        if( null != descriptor )
        {
            verifyLocation( type, descriptor.location(), issues );
        }
    }

    /**
     * Return the configuration metadata for component if any.
     * Protected to allow overiding in subclasses.
     *
     * @param type the component type
     * @return the metadata if any
     */
    protected ConfigurationDescriptor getConfigurationMetaData( final Class type )
    {
        try
        {
            final Method method = getConfigurationMethod( type );
            return method.getAnnotation( ConfigurationDescriptor.class );
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
                new VerifyIssue( message, true );
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

        final DependencyDescriptorSet set = getDependencyAttributes( type );
        if( null != set )
        {
            final DependencyDescriptor[] descriptors = set.value();
            for( int i = 0; i < descriptors.length; i++ )
            {
                final String key = determineKey( descriptors[i] );
                verifyDependencyKeyConforms( descriptors[i], key, issues );
            }
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
    protected DependencyDescriptorSet getDependencyAttributes( final Class type )
    {
        try
        {
            final Method method = getComposeMethod( type );
            return method.getAnnotation( DependencyDescriptorSet.class );
        }
        catch( NoSuchMethodException e )
        {
            return null;
        }
    }

    /**
     * Return the method via which component is passed value.
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
     * Determine the key for specified dependency.
     *
     * @param descriptor the dependency descriptor.
     * @return the key.
     */
    String determineKey( final DependencyDescriptor descriptor )
    {
        final String key = descriptor.key();
        final String qualifier = descriptor.qualifier();

        final String actualKey;
        if( !EMPTY_STRING.equals( key ) )
        {
            actualKey = key;
        }
        else if( !EMPTY_STRING.equals( qualifier ) )
        {
            actualKey = descriptor.type().getName() + "/" + qualifier;
        }
        else
        {
            actualKey = descriptor.type().getName();
        }
        return actualKey;
    }

    /**
     * Verify that the key conforms to the expectation
     * of being (type)[/(qualifier)]
     *
     * @param descriptor the descriptor descriptor/
     * @param key the dependency key
     * @param issues the list of issues
     */
    void verifyDependencyKeyConforms( final DependencyDescriptor descriptor,
                                      final String key,
                                      final List issues )
    {
        final String typeName = descriptor.type().getName();
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
            final VerifyIssue issue = new VerifyIssue( message, false );
            issues.add( issue );
        }
    }

    /**
     * Verify that the supplied implementation implements the specified
     * value.
     *
     * @param implementation the class representign component
     * @param services the value that the implementation must provide
     * @param issues the list of issues
     */
    void verifyImplementsServices( final Class implementation,
                                   final Class[] services,
                                   final List issues )
    {
        for( int i = 0; i < services.length; i++ )
        {
            if( !services[i].isAssignableFrom( implementation ) )
            {
                final Object[] args = new Object[]{services[i].getName()};
                final String message = getMessage( "CV002", args );
                final VerifyIssue issue =
                    new VerifyIssue( message, true );
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
                new VerifyIssue( message, true );
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
                new VerifyIssue( message, true );
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
            final Class lifecycle = FRAMEWORK_CLASSES[i];
            if( lifecycle.isAssignableFrom( clazz ) )
            {
                final Object[] args =
                    new Object[]{clazz.getName(), lifecycle.getName()};
                final String message = getMessage( "CV006", args );
                final VerifyIssue issue =
                    new VerifyIssue( message, true );
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
                new VerifyIssue( message, true );
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
                new VerifyIssue( message, true );
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
                new VerifyIssue( message, true );
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
                new VerifyIssue( message, true );
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
                new VerifyIssue( message, true );
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
                new VerifyIssue( message, true );
            issues.add( issue );
        }
    }

    /**
     * Retrieve an array of Classes for all the value that a Component
     * offers. This method also makes sure all value offered are
     * interfaces.
     *
     * @param type the component type
     * @param issues the list of issues
     * @return an array of Classes for all the value
     */
    Class[] getServiceClasses( final Class type, final List issues )
    {
        final ServiceDescriptorSet set =
            (ServiceDescriptorSet)type.getAnnotation( ServiceDescriptorSet.class );
        if( null != set )
        {
            final ServiceDescriptor[] descriptors = set.value();
            final Class[] services = new Class[descriptors.length];
            for( int i = 0; i < descriptors.length; i++ )
            {
                ServiceDescriptor descriptor = descriptors[i];
                services[i] = descriptor.type();
            }
            return services;
        }
        else
        {
            return new Class[0];
        }
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
