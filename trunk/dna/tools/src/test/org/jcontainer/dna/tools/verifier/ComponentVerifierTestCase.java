/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.tools.verifier;

import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Arrays;
import java.awt.event.ActionListener;
import org.jcontainer.dna.Configurable;
import org.jcontainer.dna.Configuration;
import org.codehaus.metaclass.introspector.MetaClassIntrospector;
import org.codehaus.metaclass.model.Attribute;
import org.codehaus.metaclass.model.ClassDescriptor;
import org.codehaus.metaclass.model.FieldDescriptor;
import org.codehaus.metaclass.model.MethodDescriptor;
import org.codehaus.metaclass.model.ParameterDescriptor;
import org.codehaus.metaclass.Attributes;

/**
 *
 * @author Peter Donald
 * @version $Revision: 1.11 $ $Date: 2004-04-18 14:44:19 $
 */
public class ComponentVerifierTestCase
    extends TestCase
{
    public void testVerifyNonArrayWithNonArray()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyNonArray( Object.class, issues );
        assertNoIssues( issues );
    }

    public void testVerifyNonArrayWithArray()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyNonArray( Object[].class, issues );
        assertSingleIssue( issues, "The class is an array.", true, false );
    }

    public void testVerifyNonInterfaceWithNonInterface()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyNonInterface( Object.class, issues );
        assertNoIssues( issues );
    }

    public void testVerifyNonInterfaceWithInterface()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyNonInterface( ActionListener.class, issues );
        assertSingleIssue( issues, "The class is an interface.", true, false );
    }

    public void testVerifyNonPrimitiveWithNonInterface()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyNonPrimitive( Object.class, issues );
        assertNoIssues( issues );
    }

    public void testVerifyNonPrimitiveWithPrimitive()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyNonPrimitive( Integer.TYPE, issues );
        assertSingleIssue( issues, "The class represents a primitive type.", true, false );
    }

    public void testVerifyPublicThatPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyPublic( Object.class, issues );
        assertNoIssues( issues );
    }

    public void testVerifyPublicThatNoPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyPublic( AbstractNonPublicClassWithNonPublicCtor.class, issues );
        assertSingleIssue( issues, "The class is not public.", true, false );
    }

    public void testVerifyNonAbstractThatPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyNonAbstract( Object.class, issues );
        assertNoIssues( issues );
    }

    public void testVerifyNonAbstractThatNoPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyNonAbstract( AbstractNonPublicClassWithNonPublicCtor.class, issues );
        assertSingleIssue( issues, "The class is abstract.", true, false );
    }

    public void testVerifyNoArgConstructorThatPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyNoArgConstructor( Object.class, issues );
        assertNoIssues( issues );
    }

    public void testVerifyNoArgConstructorThatNoPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyNoArgConstructor( AbstractNonPublicClassWithNonPublicCtor.class, issues );
        assertSingleIssue( issues, "The class does not have a public default constructor.", true, false );
    }

    public void testVerifyServiceNotALifecycleThatPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyServiceNotALifecycle( Object.class, issues );
        assertNoIssues( issues );
    }

    public void testVerifyServiceNotALifecycleThatNoPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyServiceNotALifecycle( LifecycleExtendingService.class, issues );
        assertSingleIssue( issues, "Service " + LifecycleExtendingService.class.getName() +
                                   " extends lifecycle interface " +
                                   Configurable.class.getName() + ".", true, false );
    }

    public void testVerifyServiceIsPublicThatPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyServiceIsPublic( Object.class, issues );
        assertNoIssues( issues );
    }

    public void testVerifyServiceIsPublicThatNoPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyServiceIsPublic( LifecycleExtendingService.class, issues );
        assertSingleIssue( issues, "Service " + LifecycleExtendingService.class.getName() +
                                   " must be public.", true, false );
    }

    public void testVerifyServiceIsaInterfaceThatPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyServiceIsaInterface( ActionListener.class, issues );
        assertNoIssues( issues );
    }

    public void testVerifyServiceIsaInterfaceThatNoPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyServiceIsaInterface( Object.class, issues );
        assertSingleIssue( issues, "Service " + Object.class.getName() +
                                   " must be an interface.", true, false );
    }

    public void testVerifyClass()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyClass( Object.class, issues );
        assertNoIssues( issues );
    }

    public void testVerifyService()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyService( ActionListener.class, issues );
        assertNoIssues( issues );
    }

    public void testVerifyImplementsServicesThatPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        final Class[] services = new Class[]{ActionListener.class};
        verifier.verifyImplementsServices( ActionListenerComponent.class, services, issues );
        assertNoIssues( issues );
    }

    public void testVerifyImplementsServicesThatNoPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        final Class[] services = new Class[]{ActionListener.class};
        verifier.verifyImplementsServices( Object.class, services, issues );
        assertSingleIssue( issues, "The metadata declares that the class " +
                                   "supports the service " +
                                   ActionListener.class.getName() +
                                   " but the class does not implement the " +
                                   "service interface.", true, false );
    }

    public void testVerifyMetaDataThatPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        MetaClassIntrospector.setAccessor( new SimpleAccessor() );
        MetaClassIntrospector.clearCompleteCache();
        final List issues = new ArrayList();
        verifier.verifyMetaData( ComponentVerifierTestCase.class, issues );
        assertNoIssues( issues );
    }

    public void testVerifyMetaDataThatNoPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        MetaClassIntrospector.setAccessor( new NullAccessor() );
        MetaClassIntrospector.clearCompleteCache();
        verifier.verifyMetaData( Object.class, issues );
        assertSingleIssue( issues,
                           "The class does not specify correct " +
                           "metadata. Missing expected dna.component " +
                           "attribute in the class attributes.",
                           true, false );
    }

    public void testGetServiceClasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        MetaClassIntrospector.setAccessor( new BadServiceAccessor() );
        MetaClassIntrospector.clearCompleteCache();
        final List issues = new ArrayList();
        verifier.getServiceClasses( ComponentVerifier.class, issues );
        assertSingleIssue( issues,
                           "Unable to load service interface " +
                           BadServiceAccessor.BAD_SERVICE +
                           " for class. Reason: " +
                           "java.lang.ClassNotFoundException: I-No-Exist!.",
                           true, false );
    }

    public void testVerifyType()
        throws Exception
    {
        final Properties parameters = new Properties();
        parameters.setProperty( "type", ActionListener.class.getName() );
        final Attribute[] attributes = new Attribute[]
        {
            new Attribute( "dna.component" ),
            new Attribute( "dna.service", parameters )
        };
        final ClassDescriptor descriptor =
            new ClassDescriptor( BasicComponent.class.getName(),
                                 attributes,
                                 attributes,
                                 FieldDescriptor.EMPTY_SET,
                                 MethodDescriptor.EMPTY_SET );
        final ComponentVerifier verifier = new ComponentVerifier();
        final RegistrationMetaClassAccessor accessor = new RegistrationMetaClassAccessor();
        accessor.registerDescriptor( descriptor );
        MetaClassIntrospector.setAccessor( accessor );
        MetaClassIntrospector.clearCompleteCache();
        final VerifyIssue[] issues = verifier.verifyType( BasicComponent.class );
        assertEquals( "issues.length", 0, issues.length );
    }

    public void testVerifyDependencyOptionalValidThatPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyDependencyOptionalValid( "true", issues );
        assertNoIssues( issues );
    }

    public void testVerifyDependencyOptionalValidThatNoPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyDependencyOptionalValid( "blah", issues );
        assertSingleIssue( issues,
                           "The dna.dependency attribute specifies " +
                           "optional parameter as \"blah\" that is not one " +
                           "of true or false.",
                           true, false );
    }

    public void testVerifyDependencyKeyConformsThatPassesWithQualifier()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyDependencyKeyConforms( "X", "X/X", issues );
        assertNoIssues( issues );
    }

    public void testVerifyDependencyKeyConformsThatPassesWithoutQualifier()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyDependencyKeyConforms( "X", "X", issues );
        assertNoIssues( issues );
    }

    public void testVerifyDependencyKeyConformsThatNoPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyDependencyKeyConforms( "X", "blah", issues );
        assertSingleIssue( issues,
                           "The dna.dependency attribute specifies the key " +
                           "blah which does not conform to recomendation of " +
                           "(type)[/(qualifier)].",
                           false, false );
    }

    public void testVerifyDependencyTypeThatPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyDependencyType( LifecycleExtendingService.class,
                                       ActionListener.class.getName(),
                                       issues );
        assertNoIssues( issues );
    }

    public void testVerifyDependencyTypeThatNoPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyDependencyType( LifecycleExtendingService.class,
                                       "INoExist!",
                                       issues );
        assertSingleIssue( issues,
                           "Unable to load dependency with type INoExist! " +
                           "for class. Reason: " +
                           "java.lang.ClassNotFoundException: INoExist!.",
                           true, false );
    }

    public void testVerifyOptionalParameterThatPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyOptionalParameter( "true", issues );
        assertNoIssues( issues );
    }

    public void testVerifyOptionalParameterThatNoPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyOptionalParameter( null, issues );
        assertSingleIssue( issues,
                           "The dna.dependency attribute does not " +
                           "specify the parameter optional.",
                           true, false );
    }

    public void testVerifyDependencyMetaDataThatPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        final Properties parameters = new Properties();
        parameters.setProperty( "optional", "false" );
        parameters.setProperty( "type", ActionListener.class.getName() );
        parameters.setProperty( "key", ActionListener.class.getName() );
        verifier.verifyDependencyMetaData( LifecycleExtendingService.class,
                                           new Attribute( "dna.dependency", parameters ),
                                           issues );
        assertNoIssues( issues );
    }

    public void testVerifyDependencyMetaDataThatNoPassesDueToMissingType()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        final Properties parameters = new Properties();
        parameters.setProperty( "optional", "false" );
        verifier.verifyDependencyMetaData( LifecycleExtendingService.class,
                                           new Attribute( "dna.dependency", parameters ),
                                           issues );
        assertSingleIssue( issues,
                           "The dna.dependency attribute does not specify the parameter type.",
                           true, false );
    }

    public void testVerifyDependencyMetaDataThatNoPassesDueToMissingKey()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        final Properties parameters = new Properties();
        parameters.setProperty( "optional", "false" );
        parameters.setProperty( "type", ActionListener.class.getName() );
        final Attribute attribute = new Attribute( "dna.dependency", parameters );
        verifier.verifyDependencyMetaData( LifecycleExtendingService.class,
                                           attribute,
                                           issues );
        assertSingleIssue( issues,
                           "The dna.dependency attribute does not specify the parameter key.",
                           true, false );
    }

    public void testVerifyDependencyMetaDataThatPassesDueToNotImplementingComposable()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyDependencyMetaData( Object.class, issues );
        assertNoIssues( issues );
    }

    public void testVerifyDependencyMetaDataThatPassesDueToNoMetaData()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        MetaClassIntrospector.setAccessor( new SimpleAccessor() );
        MetaClassIntrospector.clearCompleteCache();
        verifier.verifyDependencyMetaData( BasicComponent.class, issues );
        assertNoIssues( issues );
    }

    public void testVerifyDependencyMetaDataThatPassesWithMetaData()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        MetaClassIntrospector.setAccessor( new AccessorWithDependencyMetaData() );
        MetaClassIntrospector.clearCompleteCache();
        verifier.verifyDependencyMetaData( BasicComponent.class, issues );
        assertNoIssues( issues );
    }

    public void testVerifyConfigurationMetaDataThatPassesAsNotConfigurable()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        MetaClassIntrospector.setAccessor( new AccessorWithDependencyMetaData() );
        MetaClassIntrospector.clearCompleteCache();
        verifier.verifyConfigurationMetaData( Object.class, issues );
        assertNoIssues( issues );
    }

    public void testVerifyConfigurationMetaDataThatPassesAsNoMetaData()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        MetaClassIntrospector.setAccessor( new SimpleAccessor() );
        MetaClassIntrospector.clearCompleteCache();
        verifier.verifyConfigurationMetaData( BasicComponent.class, issues );
        assertNoIssues( issues );
    }

    public void testVerifyConfigurationMetaDataThatPassesAsValidMetaData()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();

        final Properties parameters = new Properties();
        parameters.setProperty( "location", "BasicComponent-schema.xml" );
        final Attribute[] attributes = new Attribute[]
        {
            new Attribute( "dna.configuration", parameters )
        };
        final ParameterDescriptor param =
            new ParameterDescriptor( "X", Configuration.class.getName() );
        final ParameterDescriptor[] params = new ParameterDescriptor[]{param};
        final MethodDescriptor method =
            new MethodDescriptor( "configure", "", params, attributes, attributes );
        final ClassDescriptor descriptor =
            new ClassDescriptor( BasicComponent.class.getName(),
                                 Attribute.EMPTY_SET,
                                 Attribute.EMPTY_SET,
                                 FieldDescriptor.EMPTY_SET,
                                 new MethodDescriptor[]{method} );
        final RegistrationMetaClassAccessor accessor = new RegistrationMetaClassAccessor();
        accessor.registerDescriptor( descriptor );
        MetaClassIntrospector.setAccessor( accessor );
        MetaClassIntrospector.clearCompleteCache();
        verifier.verifyConfigurationMetaData( BasicComponent.class, issues );
        assertNoIssues( issues );
    }

    public void testVerifyConfigurationMetaDataThatNoPassesAsInvalidMetaData()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();

        final Properties parameters = new Properties();
        final Attribute[] attributes = new Attribute[]
        {
            new Attribute( "dna.configuration", parameters )
        };
        final ParameterDescriptor param =
            new ParameterDescriptor( "X", Configuration.class.getName() );
        final ParameterDescriptor[] params = new ParameterDescriptor[]{param};
        final MethodDescriptor method =
            new MethodDescriptor( "configure", "", params, attributes, attributes );
        final ClassDescriptor descriptor =
            new ClassDescriptor( BasicComponent.class.getName(),
                                 Attribute.EMPTY_SET,
                                 Attribute.EMPTY_SET,
                                 FieldDescriptor.EMPTY_SET,
                                 new MethodDescriptor[]{method} );
        final RegistrationMetaClassAccessor accessor = new RegistrationMetaClassAccessor();
        accessor.registerDescriptor( descriptor );
        MetaClassIntrospector.setAccessor( accessor );
        MetaClassIntrospector.clearCompleteCache();
        verifier.verifyConfigurationMetaData( BasicComponent.class, issues );

        assertSingleIssue( issues,
                           "The dna.configuration attribute is missing the " +
                           "location parameter.",
                           true, false );
    }

    public void testverifyLocationThatPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        MetaClassIntrospector.setAccessor( new AccessorWithDependencyMetaData() );
        MetaClassIntrospector.clearCompleteCache();
        verifier.verifyLocation( BasicComponent.class,
                                 "BasicComponent-schema.xml",
                                 issues );
        assertNoIssues( issues );
    }

    public void testverifyLocationThatNoPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        MetaClassIntrospector.setAccessor( new AccessorWithDependencyMetaData() );
        MetaClassIntrospector.clearCompleteCache();
        verifier.verifyLocation( BasicComponent.class,
                                 "NoExist",
                                 issues );
        assertSingleIssue( issues,
                           "Unable to load configuration schema from location " +
                           "NoExist.",
                           true, false );
    }

    private void assertNoIssues( final List issues )
    {
        if( 0 != issues.size() )
        {
            final int count = issues.size();
            for( int i = 0; i < count; i++ )
            {
                final VerifyIssue issue = (VerifyIssue)issues.get( i );
                System.out.println(
                    "issue.getDescription() = " + issue.getDescription() );
            }
        }
        assertEquals( "issues.length", 0, issues.size() );
    }

    private void assertSingleIssue( final List issues,
                                    final String message,
                                    final boolean error,
                                    final boolean warning )
    {
        assertEquals( "issues.length", 1, issues.size() );
        final VerifyIssue issue = (VerifyIssue)issues.get( 0 );
        assertEquals( "issues[0].isWarning", warning, issue.isWarning() );
        assertEquals( "issues[0].isError", error, issue.isError() );
        assertEquals( "issues[0].description", message, issue.getDescription() );
    }
}
