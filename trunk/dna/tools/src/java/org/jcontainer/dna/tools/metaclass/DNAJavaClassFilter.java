/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.tools.metaclass;

import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import org.realityforge.metaclass.tools.compiler.JavaClassFilter;

/**
 * Filter that only accepts classes annotated with DNA
 * metadata. The only classes processed are those with the
 * JavaDoc tag "dna.component" at the class level.
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.1 $ $Date: 2003-10-16 06:41:58 $
 */
public class DNAJavaClassFilter
    implements JavaClassFilter
{
    /**
     * @see JavaClassFilter#filterClass(JavaClass)
     */
    public JavaClass filterClass( final JavaClass javaClass )
    {
        final DocletTag tag = javaClass.getTagByName( "dna.component" );
        if( null != tag )
        {
            return javaClass;
        }
        else
        {
            return null;
        }
    }
}
