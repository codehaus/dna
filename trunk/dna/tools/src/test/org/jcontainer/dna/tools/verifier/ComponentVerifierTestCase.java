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
import java.awt.event.ActionListener;
import org.jcontainer.dna.Configurable;
import org.realityforge.metaclass.introspector.MetaClassIntrospector;

/**
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.3 $ $Date: 2003-10-25 15:03:06 $
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

    public void testVerifyLifecyclesThatPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyLifecycles( Object.class, issues );
        assertNoIssues( issues );
    }

    public void testVerifyLifecyclesThatNoPasses()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final List issues = new ArrayList();
        verifier.verifyLifecycles( IncompatibleLifecycles.class, issues );
        assertSingleIssue( issues, "The class can not implement both " +
                                   "Configurable and Parameterizable " +
                                   "lifecycle interfaces.", true, false );
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
        verifier.verifyImplementsServices( IncompatibleLifecycles.class, services, issues );
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
        verifier.verifyMetaData( Object.class, issues );
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
        final ComponentVerifier verifier = new ComponentVerifier();
        MetaClassIntrospector.setAccessor( new SimpleAccessor() );
        MetaClassIntrospector.clearCompleteCache();
        final List issues = new ArrayList();
        verifier.getServiceClasses( Object.class, issues );
        assertNoIssues( issues );
    }

    private void assertNoIssues( final List issues )
    {
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
