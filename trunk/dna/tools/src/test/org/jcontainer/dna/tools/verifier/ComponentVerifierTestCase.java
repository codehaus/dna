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

/**
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.1 $ $Date: 2003-10-25 11:35:17 $
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
