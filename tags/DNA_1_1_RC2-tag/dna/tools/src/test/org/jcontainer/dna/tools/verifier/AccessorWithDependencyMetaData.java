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
import org.realityforge.metaclass.model.ParameterDescriptor;
import org.jcontainer.dna.ResourceLocator;
import java.util.Properties;
import java.awt.event.ActionListener;

/**
 *
 * @author Peter Donald
 * @version $Revision: 1.3 $ $Date: 2003-11-27 06:54:53 $
 */
class AccessorWithDependencyMetaData
    implements MetaClassAccessor
{
    public ClassDescriptor getClassDescriptor( final String classname,
                                               final ClassLoader classLoader,
                                               final MetaClassAccessor accessor )
        throws MetaClassException
    {
        final Properties parameters = new Properties();
        parameters.setProperty( "optional", "false" );
        parameters.setProperty( "type", ActionListener.class.getName() );
        parameters.setProperty( "key", ActionListener.class.getName() );
        final Attribute attribute = new Attribute( "dna.dependency", parameters );
        final Attribute[] attributes = new Attribute[]{attribute};
        final ParameterDescriptor param =
            new ParameterDescriptor("locator", ResourceLocator.class.getName());
        final ParameterDescriptor[] params = new ParameterDescriptor[]{param};
        final MethodDescriptor descriptor =
            new MethodDescriptor("compose","", params, attributes, attributes );
        return new ClassDescriptor( classname,
                                    Attribute.EMPTY_SET,
                                    Attribute.EMPTY_SET,
                                    FieldDescriptor.EMPTY_SET,
                                    new MethodDescriptor[]{descriptor} );
    }
}
