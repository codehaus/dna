/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.jcontainer.dna.AbstractLogEnabled;
import org.jcontainer.dna.Active;
import org.jcontainer.dna.Composable;
import org.jcontainer.dna.Configurable;
import org.jcontainer.dna.LogEnabled;
import org.jcontainer.dna.Parameterizable;
import org.realityforge.salt.i18n.ResourceManager;
import org.realityforge.salt.i18n.Resources;

/**
 * Utility class to help verify that component respects the
 * rules of an DNA component.
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.2 $ $Date: 2003-10-16 05:54:28 $
 */
public class ComponentVerifier
    extends AbstractLogEnabled
{
    /**
     * I18n utils.
     */
    private static final Resources REZ =
        ResourceManager.getPackageResources( ComponentVerifier.class );

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
        Parameterizable.class,
        Active.class
    };

    /**
     * Verify that the supplied implementation class
     * and service classes are valid for a component.
     *
     * @param name the name of component
     * @param implementation the implementation class of component
     * @param services the classes representing services
     * @throws VerifyException if error thrown on failure and
     *         component fails check
     */
    public void verifyComponent( final String name,
                                 final Class implementation,
                                 final Class[] services )
        throws VerifyException
    {
        verifyComponent( name, implementation, services, true );
    }

    /**
     * Verify that the supplied implementation class
     * and service classes are valid for a component.
     *
     * @param name the name of component
     * @param implementation the implementation class of component
     * @param services the classes representing services
     * @param buildable if true will verify that it is instantiateable
     *                  via class.newInstance(). May not be required for
     *                  some components that are created via a factory.
     * @throws VerifyException if error thrown on failure and
     *         component fails check
     */
    public void verifyComponent( final String name,
                                 final Class implementation,
                                 final Class[] services,
                                 final boolean buildable )
        throws VerifyException
    {
        if( buildable )
        {
            verifyClass( name, implementation );
        }
        verifyLifecycles( name, implementation );
        verifyServices( name, services );
        verifyImplementsServices( name, implementation, services );
    }

    /**
     * Verify that the supplied implementation implements the specified
     * services.
     *
     * @param name the name of component
     * @param implementation the class representign component
     * @param services the services that the implementation must provide
     * @throws VerifyException if error thrown on failure and
     *         component fails check
     */
    public void verifyImplementsServices( final String name,
                                          final Class implementation,
                                          final Class[] services )
        throws VerifyException
    {
        for( int i = 0; i < services.length; i++ )
        {
            if( !services[ i ].isAssignableFrom( implementation ) )
            {
                final String message =
                    REZ.format( "verifier.noimpl-service.error",
                                name,
                                implementation.getName(),
                                services[ i ].getName() );
                throw new VerifyException( message );
            }
        }
    }

    /**
     * Verify that the supplied class is a valid class for
     * a Component.
     *
     * @param name the name of component
     * @param clazz the class representing component
     * @throws VerifyException if error thrown on failure and
     *         component fails check
     */
    public void verifyClass( final String name,
                             final Class clazz )
        throws VerifyException
    {
        verifyNoArgConstructor( name, clazz );
        verifyNonAbstract( name, clazz );
        verifyNonArray( name, clazz );
        verifyNonInterface( name, clazz );
        verifyNonPrimitive( name, clazz );
        verifyPublic( name, clazz );
    }

    /**
     * Verify that the supplied classes are valid classes for
     * a service.
     *
     * @param name the name of component
     * @param classes the classes representign services
     * @throws VerifyException if error thrown on failure and
     *         component fails check
     */
    public void verifyServices( final String name,
                                final Class[] classes )
        throws VerifyException
    {
        for( int i = 0; i < classes.length; i++ )
        {
            verifyService( name, classes[ i ] );
        }
    }

    /**
     * Verify that the supplied class is a valid class for
     * a service.
     *
     * @param name the name of component
     * @param clazz the class representign service
     * @throws VerifyException if error thrown on failure and
     *         component fails check
     */
    public void verifyService( final String name,
                               final Class clazz )
        throws VerifyException
    {
        verifyServiceIsaInterface( name, clazz );
        verifyServiceIsPublic( name, clazz );
        verifyServiceNotALifecycle( name, clazz );
    }

    /**
     * Verify that the implementation class does not
     * implement incompatible lifecycle interfaces.
     *
     * @param name the name of component
     * @param implementation the implementation class
     * @throws VerifyException if error thrown on failure and
     *         component fails check
     */
    public void verifyLifecycles( final String name,
                                  final Class implementation )
        throws VerifyException
    {
        final boolean configurable =
            Configurable.class.isAssignableFrom( implementation );
        final boolean parameterizable =
            Parameterizable.class.isAssignableFrom( implementation );
        if( parameterizable && configurable )
        {
            final String message =
                REZ.format( "verifier.incompat-config.error",
                            name,
                            implementation.getName() );
            getLogger().error( message );
            throw new VerifyException( message );
        }
    }

    /**
     * Verify that the service implemented by
     * specified component is an interface.
     *
     * @param name the name of component
     * @param clazz the class representign service
     * @throws VerifyException if error thrown on failure and
     *         component fails check
     */
    public void verifyServiceIsaInterface( final String name,
                                           final Class clazz )
        throws VerifyException
    {
        if( !clazz.isInterface() )
        {
            final String message =
                REZ.format( "verifier.non-interface-service.error",
                            name,
                            clazz.getName() );
            getLogger().error( message );
            throw new VerifyException( message );
        }
    }

    /**
     * Verify that the service implemented by
     * specified component is public.
     *
     * @param name the name of component
     * @param clazz the class representign service
     * @throws VerifyException if error thrown on failure and
     *         component fails check
     */
    public void verifyServiceIsPublic( final String name,
                                       final Class clazz )
        throws VerifyException
    {
        final boolean isPublic =
            Modifier.isPublic( clazz.getModifiers() );
        if( !isPublic )
        {
            final String message =
                REZ.format( "verifier.non-public-service.error",
                            name,
                            clazz.getName() );
            getLogger().error( message );
            throw new VerifyException( message );
        }
    }

    /**
     * Verify that the service implemented by
     * specified component does not extend any lifecycle interfaces.
     *
     * @param name the name of component
     * @param clazz the class representign service
     * @throws VerifyException if error thrown on failure and
     *         component fails check
     */
    public void verifyServiceNotALifecycle( final String name,
                                            final Class clazz )
        throws VerifyException
    {
        for( int i = 0; i < FRAMEWORK_CLASSES.length; i++ )
        {
            final Class lifecycle = FRAMEWORK_CLASSES[ i ];
            if( lifecycle.isAssignableFrom( clazz ) )
            {
                final String message =
                    REZ.format( "verifier.service-isa-lifecycle.error",
                                name,
                                clazz.getName(),
                                lifecycle.getName() );
                getLogger().error( message );
                throw new VerifyException( message );
            }
        }
    }

    /**
     * Verify that the component has a no-arg aka default
     * constructor.
     *
     * @param name the name of component
     * @param clazz the class representign component
     * @throws VerifyException if error thrown on failure and
     *         component fails check
     */
    public void verifyNoArgConstructor( final String name,
                                        final Class clazz )
        throws VerifyException
    {
        try
        {
            final Constructor ctor = clazz.getConstructor( EMPTY_TYPES );
            if( !Modifier.isPublic( ctor.getModifiers() ) )
            {
                final String message =
                    REZ.format( "verifier.non-public-ctor.error",
                                name,
                                clazz.getName() );
                getLogger().error( message );
                throw new VerifyException( message );
            }
        }
        catch( final NoSuchMethodException nsme )
        {
            final String message =
                REZ.format( "verifier.missing-noargs-ctor.error",
                            name,
                            clazz.getName() );
            getLogger().error( message );
            throw new VerifyException( message );
        }
    }

    /**
     * Verify that the component is not represented by
     * abstract class.
     *
     * @param name the name of component
     * @param clazz the class representign component
     * @throws VerifyException if error thrown on failure and
     *         component fails check
     */
    public void verifyNonAbstract( final String name,
                                   final Class clazz )
        throws VerifyException
    {
        final boolean isAbstract =
            Modifier.isAbstract( clazz.getModifiers() );
        if( isAbstract )
        {
            final String message =
                REZ.format( "verifier.abstract-class.error",
                            name,
                            clazz.getName() );
            getLogger().error( message );
            throw new VerifyException( message );
        }
    }

    /**
     * Verify that the component is not represented by
     * abstract class.
     *
     * @param name the name of component
     * @param clazz the class representign component
     * @throws VerifyException if error thrown on failure and
     *         component fails check
     */
    public void verifyPublic( final String name,
                              final Class clazz )
        throws VerifyException
    {
        final boolean isPublic =
            Modifier.isPublic( clazz.getModifiers() );
        if( !isPublic )
        {
            final String message =
                REZ.format( "verifier.nonpublic-class.error",
                            name,
                            clazz.getName() );
            getLogger().error( message );
            throw new VerifyException( message );
        }
    }

    /**
     * Verify that the component is not represented by
     * primitive class.
     *
     * @param name the name of component
     * @param clazz the class representign component
     * @throws VerifyException if error thrown on failure and
     *         component fails check
     */
    public void verifyNonPrimitive( final String name,
                                    final Class clazz )
        throws VerifyException
    {
        if( clazz.isPrimitive() )
        {
            final String message =
                REZ.format( "verifier.primitive-class.error",
                            name,
                            clazz.getName() );
            getLogger().error( message );
            throw new VerifyException( message );
        }
    }

    /**
     * Verify that the component is not represented by
     * interface class.
     *
     * @param name the name of component
     * @param clazz the class representign component
     * @throws VerifyException if error thrown on failure and
     *         component fails check
     */
    public void verifyNonInterface( final String name,
                                    final Class clazz )
        throws VerifyException
    {
        if( clazz.isInterface() )
        {
            final String message =
                REZ.format( "verifier.interface-class.error",
                            name,
                            clazz.getName() );
            getLogger().error( message );
            throw new VerifyException( message );
        }
    }

    /**
     * Verify that the component is not represented by
     * an array class.
     *
     * @param name the name of component
     * @param clazz the class representign component
     * @throws VerifyException if error thrown on failure and
     *         component fails check
     */
    public void verifyNonArray( final String name,
                                final Class clazz )
        throws VerifyException
    {
        if( clazz.isArray() )
        {
            final String message =
                REZ.format( "verifier.array-class.error",
                            name,
                            clazz.getName() );
            getLogger().error( message );
            throw new VerifyException( message );
        }
    }
}
