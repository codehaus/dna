/*
 * Copyright (C) The DNA Group. All rights reserved.
 *
 * This software is published under the terms of the DNA
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl.verifier;

import junit.framework.TestCase;

public class VerifyIssueTestCase
    extends TestCase
{
    public void testConsoleWithEmptyOutput()
        throws Exception
    {
        try
        {
            new VerifyIssue( null, true );
        }
        catch( final NullPointerException npe )
        {
            assertEquals( "npe.message", "description", npe.getMessage() );
            return;
        }
        fail( "Expected to fail due to NPE in ctor" );
    }

    public void testMockConsoleEmptyCtor()
        throws Exception
    {
        final String description = "BlahBlahBlah";
        final VerifyIssue logger = new VerifyIssue( description, false );
        assertEquals( "getDescription()", description, logger.getDescription() );
        assertEquals( "isError()", false, logger.isError() );
        assertEquals( "toString()", "[Warning] " + description, logger.toString() );
    }
}
