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
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author Peter Donald
 * @version $Revision: 1.3 $ $Date: 2003-11-27 06:54:53 $
 */
public class RegistrationMetaClassAccessor
    implements MetaClassAccessor
{
    private final Map m_descriptors = new HashMap();

    public ClassDescriptor getClassDescriptor( final String classname,
                                               final ClassLoader classLoader,
                                               final MetaClassAccessor accessor )
        throws MetaClassException
    {
        final ClassDescriptor descriptor = (ClassDescriptor)m_descriptors.get( classname );
        if( null == descriptor )
        {
            final String message = "Missing descriptor for " + classname;
            throw new MetaClassException( message );
        }
        return descriptor;
    }

    public void registerDescriptor( final ClassDescriptor descriptor )
    {
        if( null == descriptor )
        {
            throw new NullPointerException( "descriptor" );
        }
        m_descriptors.put( descriptor.getName(), descriptor );
    }
}
