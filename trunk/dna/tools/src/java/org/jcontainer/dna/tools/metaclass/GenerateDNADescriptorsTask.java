/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.tools.metaclass;

import org.realityforge.metaclass.tools.tasks.GenerateClassDescriptorsTask;
import org.realityforge.metaclass.tools.tasks.PluginElement;

/**
 * Custom Loom Descriptor creation task. This task replaces
 * the old Lomm metadata generation task and compiles the
 * information into MetaClass descriptors.
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.2 $ $Date: 2003-11-19 00:44:29 $
 */
public class GenerateDNADescriptorsTask
    extends GenerateClassDescriptorsTask
{
    /**
     * Overide execute to add in custom
     * Loom Filters and interceptors.
     */
    public void execute()
    {
        final PluginElement interceptor = new PluginElement();
        interceptor.setName( DNAAttributeInterceptor.class.getName() );
        addInterceptor( interceptor );
        super.execute();
    }
}
