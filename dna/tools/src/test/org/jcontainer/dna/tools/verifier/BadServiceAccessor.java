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
import org.realityforge.metaclass.model.ClassDescriptor;
import org.realityforge.metaclass.model.Attribute;
import org.realityforge.metaclass.model.FieldDescriptor;
import org.realityforge.metaclass.model.MethodDescriptor;
import java.util.Properties;
import java.awt.event.ActionListener;

/**
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.1 $ $Date: 2003-10-25 15:03:06 $
 */
class BadServiceAccessor
    implements MetaClassAccessor
{
    static final String BAD_SERVICE = "I-No-Exist!";

    public ClassDescriptor getClassDescriptor( final String classname,
                                               final ClassLoader classLoader )
        throws MetaClassException
    {
        final Properties bad = new Properties();
        bad.setProperty( "type", ActionListener.class.getName() );

        final Properties good = new Properties();
        good.setProperty( "type", BAD_SERVICE );
        final Attribute[] attributes = new Attribute[]
        {
            new Attribute( "dna.component" ),
            new Attribute( "dna.service", bad ),
            new Attribute( "dna.service", good )
        };
        return new ClassDescriptor( classname,
                                    0,
                                    attributes,
                                    FieldDescriptor.EMPTY_SET,
                                    MethodDescriptor.EMPTY_SET );
    }
}
