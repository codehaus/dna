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
 * to be supplied with hierarchial configuration data.
 *
 * @version $Revision: 1.2 $ $Date: 2003-09-05 04:57:57 $
 */
public interface Configurable
{
   /**
    * Supply the component with configuration data in form
    * of a Configuration object.
    *
    * @param configuration the Configuration object
    * @throws ConfigurationException if the configuration data
    *         specifies invalid configuration data or fails to
    *         match the expected schema.
    */
    void configure( Configuration configuration )
        throws ConfigurationException;
}
