/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.tools.verifier;

import org.realityforge.metaclass.introspector.MetaClassAccessor;
import org.realityforge.metaclass.introspector.MetaClassException;
import org.realityforge.metaclass.model.Attribute;
import org.realityforge.metaclass.model.ClassDescriptor;
import org.realityforge.metaclass.model.FieldDescriptor;
import org.realityforge.metaclass.model.MethodDescriptor;

/**
 *
 * @author Peter Donald
 * @version $Revision: 1.4 $ $Date: 2003-11-27 06:54:53 $
 */
class SimpleAccessor
    implements MetaClassAccessor
{
    public ClassDescriptor getClassDescriptor( final String classname,
                                               final ClassLoader classLoader,
                                               final MetaClassAccessor accessor )
        throws MetaClassException
    {
        final Attribute[] attributes = new Attribute[]
        {
            new Attribute( "dna.component" ),
        };
        return new ClassDescriptor( classname,
                                    attributes,
                                    attributes,
                                    FieldDescriptor.EMPTY_SET,
                                    MethodDescriptor.EMPTY_SET );
    }
}
