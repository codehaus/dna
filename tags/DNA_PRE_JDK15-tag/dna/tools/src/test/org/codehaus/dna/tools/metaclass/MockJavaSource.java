/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.tools.metaclass;

import com.thoughtworks.qdox.model.JavaSource;

/**
 *
 * @author Peter Donald
 * @version $Revision: 1.1 $ $Date: 2004-04-18 20:13:48 $
 */
class MockJavaSource
    extends JavaSource
{
    static final String PREFIX = "com.biz.";

    public String resolveType( final String name )
    {
        return PREFIX + name;
    }
}
