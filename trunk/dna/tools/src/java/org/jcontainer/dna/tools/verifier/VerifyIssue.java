/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.tools.verifier;

/**
 * Class defining a problem discovered when verifying a
 * DNA component type.
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.1 $ $Date: 2003-10-19 09:40:38 $
 */
public class VerifyIssue
{
    /**
     * Severity when issue is just a
     * notice such as going against a
     * convention.
     */
    public static final int NOTICE = 0;

    /**
     * Severity when issue is just a warning and
     * not an error.
     */
    public static final int WARNING = 5;

    /**
     * Severity when issue is an error that
     * will cause the component to fail to
     * load.
     */
    public static final int ERROR = 10;

    /**
     * The severity of the issue.
     */
    private final int m_severity;

    /**
     * The message describing issue.
     */
    private final String m_description;

    /**
     * Create a new VerifyIssue.
     *
     * @param severity the serverity
     * @param description the description
     */
    public VerifyIssue( final int severity,
                        final String description )
    {
        if( null == description )
        {
            throw new NullPointerException( "description" );
        }
        m_severity = severity;
        m_description = description;
    }

    /**
     * Return true if issue is a warning.
     *
     * @return true if issue is a warning.
     */
    public boolean isWarning()
    {
        return WARNING == m_severity;
    }

    /**
     * Return true if issue is an error.
     *
     * @return true if issue is an error.
     */
    public boolean isError()
    {
        return ERROR == m_severity;
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
}
