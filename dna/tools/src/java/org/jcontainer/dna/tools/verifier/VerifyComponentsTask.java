/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.tools.verifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.realityforge.metaclass.Attributes;
import org.realityforge.metaclass.introspector.DefaultMetaClassAccessor;
import org.realityforge.metaclass.io.MetaClassIOBinary;
import org.realityforge.metaclass.model.Attribute;
import org.realityforge.metaclass.model.ClassDescriptor;

/**
 * Task to validate a set of components.
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.1 $ $Date: 2003-10-26 03:22:17 $
 */
public class VerifyComponentsTask
    extends Task
{
    /**
     * IO used to read descriptors.
     */
    private static final MetaClassIOBinary IO = new MetaClassIOBinary();

    /**
     * Extension used to designate class files.
     */
    private static final String CLASS_EXT = ".class";

    /**
     * List of filesets to process.
     */
    private final List m_filesets = new ArrayList();

    /**
     * The Classpath to load components from.
     */
    private Path m_classpath;

    /**
     * Add fileset to list of files to be processed.
     *
     * @param fileSet fileset to list of files to be processed.
     */
    public void addFileset( final FileSet fileSet )
    {
        m_filesets.add( fileSet );
        getClassPath().addFileset( fileSet );
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
            new AntClassLoader( getProject(), m_classpath );
        boolean valid = true;

        final List classes = getClassesWithMetaData();
        log( "Verifying " + classes.size() + " components." );
        final Iterator iterator = classes.iterator();
        while( iterator.hasNext() )
        {
            final String name = (String)iterator.next();
            log( "Verifying " + name + ".", Project.MSG_DEBUG );
            valid &= validateComponent( classLoader, name );
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
            final Class type =
                classLoader.loadClass( classname );
            final ComponentVerifier verifier = new ComponentVerifier();
            final VerifyIssue[] issues = verifier.verifyType( type );
            for( int i = 0; i < issues.length; i++ )
            {
                final VerifyIssue issue = issues[ i ];
                final String tail = " (" + classname + "): " + issue.getDescription();
                if( issue.isError() )
                {
                    log( "Error" + tail, Project.MSG_ERR );
                    valid = false;
                }
                else if( issue.isWarning() )
                {
                    log( "Warning" + tail, Project.MSG_WARN );
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
     * Setup list of files compiler will compile.
     */
    private List getClassesWithMetaData()
    {
        final List list = new ArrayList();
        final int count = m_filesets.size();
        for( int i = 0; i < count; i++ )
        {
            final FileSet fileSet = (FileSet)m_filesets.get( i );
            scanFileSetForClassesWithMetaData( fileSet, list );
        }
        return list;
    }

    /**
     * Add all files contained in fileset to compilers file list.
     *
     * @param fileSet the fileset
     */
    private void scanFileSetForClassesWithMetaData( final FileSet fileSet,
                                                    final List list )
    {
        final File dir = fileSet.getDir( getProject() );
        final DirectoryScanner directoryScanner =
            fileSet.getDirectoryScanner( getProject() );
        directoryScanner.scan();
        final String[] includedFiles = directoryScanner.getIncludedFiles();
        for( int j = 0; j < includedFiles.length; j++ )
        {
            final String name = includedFiles[ j ];
            if( name.endsWith( CLASS_EXT ) )
            {
                checkClass( name, dir, list );
            }
        }
    }

    /**
     * Check if class represented by specified file
     * is a DNA component and classname to list.
     *
     * @param name the name of .class file
     * @param dir the base directory
     * @param list the list of classnames
     */
    void checkClass( final String name,
                     final File dir,
                     final List list )
    {
        final String basename =
            name.substring( 0, name.length() - CLASS_EXT.length() );
        final String metaName = basename + DefaultMetaClassAccessor.BINARY_EXT;
        final File file = new File( dir, metaName );
        ClassDescriptor descriptor = loadDescriptor( file );
        if( null != descriptor && isDNAComponent( descriptor ) )
        {
            list.add( descriptor.getName() );
        }
    }

    /**
     * Load descriptor from file.
     * If descriptor can not be loaded then return null
     *
     * @param file the file
     * @return the descriptor or null
     */
    ClassDescriptor loadDescriptor( final File file )
    {
        try
        {
            final FileInputStream input = new FileInputStream( file );
            return IO.deserializeClass( input );
        }
        catch( final IOException ioe )
        {
            return null;
        }
    }

    /**
     * Return true if specified descriptor represents DNA component.
     *
     * @param descriptor the descriptor
     * @return true if descriptor represents DNA component
     */
    boolean isDNAComponent( final ClassDescriptor descriptor )
    {
        final Attribute[] attributes = descriptor.getAttributes();
        final Attribute attribute =
            Attributes.getAttributeByName( attributes, "dna.component" );
        return null != attribute;
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
}
