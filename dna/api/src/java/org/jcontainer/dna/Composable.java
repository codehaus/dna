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
 * to be supplied with services via ResourceLocator.
 * The ResourceLocator contains the services that this
 * component depends upon under keys specified in the
 * components metadata.
 *
 * @version $Revision: 1.4 $ $Date: 2003-09-23 02:15:56 $
 */
public interface Composable
{
    /**
     * Supply the component with ResourceLocator object
     * via which they can access any dependency services.
     *
     * @param locator the ResourceLocator
     * @throws MissingResourceException if the ResourceLocator does not
     *         contain all the required services
     */
    void compose( ResourceLocator locator )
        throws MissingResourceException;
}
