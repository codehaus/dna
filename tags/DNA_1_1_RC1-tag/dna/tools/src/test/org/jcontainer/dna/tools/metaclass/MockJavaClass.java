/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.tools.metaclass;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;

/**
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.2 $ $Date: 2003-11-27 03:51:54 $
 */
class MockJavaClass
    extends JavaClass
{
    public MockJavaClass()
    {
        super( new MockJavaSource() );
    }

    public String getFullyQualifiedName()
    {
        return getName();
    }

    public JavaSource getParentSource()
    {
        return new MockJavaSource();
    }
}
