/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl.verifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;

/**
 * Task to validate a set of components.
 *
 * @author Peter Donald
 * @version $Revision: 1.1 $ $Date: 2005-03-31 09:41:42 $
 */
public class VerifyComponentsTask
    extends Task
{
    /**
     * List of components to process.
     */
    private final List m_components = new ArrayList();
    /**
     * The Classpath to load components from.
     */
    private Path m_classpath;

    /**
     * Add component to validate.
     *
     * @param component the component
     */
    public void addComponent( final NamedElement component )
    {
        m_components.add( component );
    }

    /**
     * Add a classpath element.
     *
     * @return the new path
     */
    public Path createClasspath()
    {
        return getClassPath().createPath();
    }

    /**
     * Execute VerifyComponents.
     */
    public void execute()
    {
        validateParameters();

        final AntClassLoader classLoader =
            new AntClassLoader( getClass().getClassLoader(), getProject(), m_classpath, true );
        boolean valid = true;

        log( "Verifying " + m_components.size() + " components." );
        final Iterator iterator = m_components.iterator();
        while( iterator.hasNext() )
        {
            final NamedElement component = (NamedElement)iterator.next();
            if( null == component.m_name )
            {
                throw new BuildException( "Component failed to specify name attribute" );
            }

            log( "Verifying " + component.m_name + ".", Project.MSG_DEBUG );
            valid &= validateComponent( classLoader, component.m_name );
        }

        if( !valid )
        {
            final String message = "Not all components validated.";
            throw new BuildException( message );
        }
    }

    /**
     * Validate specified component type.
     *
     * @param classLoader the classloader to load class from
     * @param classname the name of type
     * @return true if component is valid
     */
    boolean validateComponent( final AntClassLoader classLoader,
                               final String classname )
    {
        boolean valid = true;
        try
        {
            final Class type = classLoader.loadClass( classname );
            final ComponentVerifier verifier = new ComponentVerifier();
            final VerifyIssue[] issues = verifier.verifyType( type );
            if( 0 != issues.length )
            {
                log( "Component " + classname + " raised " + issues.length +
                     " verification issues", Project.MSG_ERR );
            }
            for( int i = 0; i < issues.length; i++ )
            {
                final VerifyIssue issue = issues[i];
                final String tail = " (" + classname + "): " + issue.getDescription();
                if( issue.isError() )
                {
                    log( "Error" + tail, Project.MSG_ERR );
                    valid = false;
                }
                else
                {
                    log( "Notice" + tail, Project.MSG_INFO );
                }
            }
        }
        catch( final Exception e )
        {
            final String message =
                "Failed to validate " + classname + " due to " + e;
            log( message, Project.MSG_ERR );
            valid = false;
        }
        return valid;
    }

    /**
     * Validate specified parameters.
     */
    void validateParameters()
    {
        if( null == m_classpath )
        {
            final String message = "User did not specify classpath";
            throw new BuildException( message );
        }
    }

    /**
     * Utility method to get a ClassPath instance.
     *
     * @return a Path object
     */
    private Path getClassPath()
    {
        if( m_classpath == null )
        {
            m_classpath = new Path( getProject() );
        }
        return m_classpath;
    }

    /**
     * Utilit class to hold name of component.
     */
    public static class NamedElement
    {
        String m_name;

        public void setName( final String name )
        {
            m_name = name;
        }
    }
}
