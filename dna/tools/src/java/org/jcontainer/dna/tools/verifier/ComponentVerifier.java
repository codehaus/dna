/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.tools.verifier;

import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.jcontainer.dna.Active;
import org.jcontainer.dna.Composable;
import org.jcontainer.dna.Configurable;
import org.jcontainer.dna.LogEnabled;
import org.jcontainer.dna.Parameterizable;
import org.realityforge.metaclass.Attributes;
import org.realityforge.metaclass.model.Attribute;

/**
 * Utility class to help verify that component respects the
 * rules of an DNA component.
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.6 $ $Date: 2003-10-25 14:20:47 $
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
        Parameterizable.class,
        Active.class
    };

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
        final Attribute attribute =
            Attributes.getAttribute( type, "dna.component" );
        if( null == attribute )
        {
            final String message = getMessage( "CV001E" );
            final VerifyIssue issue =
                new VerifyIssue( VerifyIssue.ERROR, message );
            issues.add( issue );
        }

        final Class[] interfaces = getServiceClasses( type, issues );

        verifyClass( type, issues );
        verifyLifecycles( type, issues );
        verifyImplementsServices( type, interfaces, issues );
        for( int i = 0; i < interfaces.length; i++ )
        {
            verifyService( interfaces[ i ], issues );
        }

        return (VerifyIssue[])issues.
            toArray( new VerifyIssue[ issues.size() ] );
    }

    /**
     * Verify that the supplied implementation implements the specified
     * services.
     *
     * @param implementation the class representign component
     * @param services the services that the implementation must provide
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
                final String message = getMessage( "CV002E", args );
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
     */
    void verifyService( final Class clazz, final List issues )
    {
        verifyServiceIsaInterface( clazz, issues );
        verifyServiceIsPublic( clazz, issues );
        verifyServiceNotALifecycle( clazz, issues );
    }

    /**
     * Verify that the implementation class does not
     * implement incompatible lifecycle interfaces.
     *
     * @param implementation the implementation class
     */
    void verifyLifecycles( final Class implementation, final List issues )
    {
        final boolean configurable =
            Configurable.class.isAssignableFrom( implementation );
        final boolean parameterizable =
            Parameterizable.class.isAssignableFrom( implementation );
        if( parameterizable && configurable )
        {
            final String message = getMessage( "CV003E" );
            final VerifyIssue issue =
                new VerifyIssue( VerifyIssue.ERROR, message );
            issues.add( issue );
        }
    }

    /**
     * Verify that the service implemented by
     * specified component is an interface.
     *
     * @param clazz the class representign service
     */
    void verifyServiceIsaInterface( final Class clazz, final List issues )
    {
        if( !clazz.isInterface() )
        {
            final Object[] args = new Object[]{clazz.getName()};
            final String message = getMessage( "CV004E", args );
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
     */
    void verifyServiceIsPublic( final Class clazz, final List issues )

    {
        final boolean isPublic =
            Modifier.isPublic( clazz.getModifiers() );
        if( !isPublic )
        {
            final Object[] args = new Object[]{clazz.getName()};
            final String message = getMessage( "CV005E", args );
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
                final String message = getMessage( "CV006E", args );
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
     */
    void verifyNoArgConstructor( final Class clazz, final List issues )
    {
        try
        {
            clazz.getConstructor( EMPTY_TYPES );
        }
        catch( final NoSuchMethodException nsme )
        {
            final String message = getMessage( "CV008E" );
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
     */
    void verifyNonAbstract( final Class clazz, final List issues )
    {
        final boolean isAbstract =
            Modifier.isAbstract( clazz.getModifiers() );
        if( isAbstract )
        {
            final String message = getMessage( "CV009E" );
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
     */
    void verifyPublic( final Class clazz, final List issues )
    {
        final boolean isPublic =
            Modifier.isPublic( clazz.getModifiers() );
        if( !isPublic )
        {
            final String message = getMessage( "CV010E" );
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
     */
    void verifyNonPrimitive( final Class clazz, final List issues )
    {
        if( clazz.isPrimitive() )
        {
            final String message = getMessage( "CV011E" );
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
     */
    void verifyNonInterface( final Class clazz, final List issues )
    {
        if( clazz.isInterface() )
        {
            final String message = getMessage( "CV012E" );
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
     */
    void verifyNonArray( final Class clazz, final List issues )
    {
        if( clazz.isArray() )
        {
            final String message = getMessage( "CV013E" );
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
     * @return an array of Classes for all the services
     */
    Class[] getServiceClasses( final Class type, final List issues )
    {
        final ClassLoader classLoader = type.getClassLoader();
        final Attribute[] attributes =
            Attributes.getAttributes( type, "dna.service" );
        final Class[] classes = new Class[ attributes.length ];
        for( int i = 0; i < attributes.length; i++ )
        {
            final String classname = attributes[ i ].getParameter( "type" );
            try
            {
                classes[ i ] = classLoader.loadClass( classname );
            }
            catch( final Throwable t )
            {
                final Object[] args = new Object[]{classname, t};
                final String message = getMessage( "CV014E", args );
                final VerifyIssue issue =
                    new VerifyIssue( VerifyIssue.ERROR, message );
                issues.add( issue );
            }
        }

        return classes;
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
