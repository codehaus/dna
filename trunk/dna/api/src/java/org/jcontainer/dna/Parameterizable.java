/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna;

/**
 * The component implements this interface if it wishes
 * to be supplied with flat configuration data.
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.3 $ $Date: 2003-09-23 02:15:56 $
 */
public interface Parameterizable
{
    /**
     * Supply the component with configuration data in form
     * of a Parameters object.
     *
     * @param parameters the parameters object
     * @throws ParameterException if the configuration data
     *         specifies invalid configuration data or fails to
     *         match the expected schema.
     */
    void parameterize( Parameters parameters )
        throws ParameterException;
}
