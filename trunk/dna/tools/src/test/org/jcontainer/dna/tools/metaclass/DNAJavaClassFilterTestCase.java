/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.tools.metaclass;

import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import java.util.ArrayList;
import junit.framework.TestCase;

/**
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.1 $ $Date: 2003-10-16 06:41:58 $
 */
public class DNAJavaClassFilterTestCase
    extends TestCase
{
    public void testClassWithoutDNAMetaData()
        throws Exception
    {
        final DNAJavaClassFilter filter = new DNAJavaClassFilter();
        final JavaClass javaClass = new JavaClass();
        final JavaClass result = filter.filterClass( javaClass );
        assertNull( "javaClass", result );
    }

    public void testClassWithDNAMetaData()
        throws Exception
    {
        final DNAJavaClassFilter filter = new DNAJavaClassFilter();
        final JavaClass javaClass = new JavaClass();
        final ArrayList tags = new ArrayList();
        tags.add( new DocletTag( "dna.component", "" ) );
        javaClass.setTags( tags );
        final JavaClass result = filter.filterClass( javaClass );
        assertEquals( "javaClass", javaClass, result );
    }
}
