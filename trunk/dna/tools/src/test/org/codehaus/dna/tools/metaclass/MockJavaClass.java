/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.tools.metaclass;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;

/**
 *
 * @author Peter Donald
 * @version $Revision: 1.1 $ $Date: 2004-04-18 20:13:48 $
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