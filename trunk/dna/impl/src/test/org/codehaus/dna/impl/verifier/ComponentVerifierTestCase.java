/*
 * Copyright (C) The DNA Group. All rights reserved.
 *
 * This software is published under the terms of the DNA
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl.verifier;

import junit.framework.TestCase;

public class ComponentVerifierTestCase
    extends TestCase
{
    public void testComponentVerifier_MyComponent()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final VerifyIssue[] issues = verifier.verifyType( MyComponent.class );

        expectIssue( issues,
                     "Service org.codehaus.dna.Active is assignable from lifecycle interface org.codehaus.dna.Active.",
                     true );
        expectIssue( issues,
                     "Service java.lang.Object must be an interface.",
                     true );
        expectIssue( issues,
                     "The metadata declares that the class supports the service java.util.Set but the class does not implement the service interface.",
                     true );
        expectIssue( issues,
                     "Service org.codehaus.dna.impl.verifier.MyService must be public.",
                     true );
        expectIssue( issues,
                     "The @DependencyDescriptor annotation specifies the key my-service which does not conform to recomendation of (type)[/(qualifier)].",
                     false );
        assertEquals( "issues.length", 5, issues.length );
    }

    public void testComponentVerifier_MyDodgyComponent()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final VerifyIssue[] issues = verifier.verifyType( MyDodgyComponent.class );

        expectIssue( issues,
                     "The class is not public.",
                     true );
        expectIssue( issues,
                     "The class is abstract.",
                     true );
        expectIssue( issues,
                     "The class does not specify correct metadata. Missing expected @ComponentDescriptor annotation.",
                     true );
        expectIssue( issues,
                     "The class does not have a public default constructor.",
                     true );
        expectIssue( issues,
                     "Unable to load configuration schema from location NoExist.dtd.",
                     true );
        assertEquals( "issues.length", 5, issues.length );
    }

    public void testComponentVerifier_Integer()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final VerifyIssue[] issues = verifier.verifyType( Integer.TYPE );

        expectIssue( issues,
                     "The class represents a primitive type.",
                     true );
    }

    public void testComponentVerifier_MyService()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final VerifyIssue[] issues = verifier.verifyType( MyService.class );

        expectIssue( issues,
                     "The class is an interface.",
                     true );
    }

    public void testComponentVerifier_ObjectArray()
        throws Exception
    {
        final ComponentVerifier verifier = new ComponentVerifier();
        final VerifyIssue[] issues = verifier.verifyType( Object[].class );

        expectIssue( issues,
                     "The class is an array.",
                     true );
    }

    private void expectIssue( final VerifyIssue[] issues,
                              final String description,
                              final boolean isError )
    {
        for( int i = 0; i < issues.length; i++ )
        {
            VerifyIssue issue = issues[i];
            if( description.equals( issue.getDescription() ) )
            {
                assertEquals( "Issue severity: " + description, isError, issue.isError() );
                return;
            }
        }
        fail( "Unable to find Issue : " + description );
    }
}
