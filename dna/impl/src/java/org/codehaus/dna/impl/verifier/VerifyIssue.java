/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl.verifier;

/**
 * Class defining a problem discovered when verifying a
 * DNA component type.
 *
 * @author Peter Donald
 * @version $Revision: 1.1 $ $Date: 2005-03-31 09:41:42 $
 */
public class VerifyIssue
{
    /**
     * The severity of the issue.
     */
    private final boolean m_error;

    /**
     * The message describing issue.
     */
    private final String m_description;

    /**
     * Create a new VerifyIssue.
     *
     * @param description the description
     @param error
     */
    public VerifyIssue( final String description,
                        boolean error )
    {
        if( null == description )
        {
            throw new NullPointerException( "description" );
        }
        m_error = error;
        m_description = description;
    }

    /**
     * Return true if issue is an error.
     *
     * @return true if issue is an error.
     */
    public boolean isError()
    {
        return m_error;
    }

    /**
     * Return a description of the issue.
     *
     * @return a description of the issue.
     */
    public String getDescription()
    {
        return m_description;
    }

    public String toString()
    {
        final String prefix = isError() ? "[Error]" : "[Warning]";
        return  prefix + " " + getDescription();
    }
}
