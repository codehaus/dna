/*
 * Copyright (C) The DNA Group. All rights reserved.
 *
 * This software is published under the terms of the DNA
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna;

/**
 * The component implements this interface if it wishes
 * to be supplied with value via ResourceLocator.
 * The ResourceLocator contains the value that this
 * component depends upon under keys specified in the
 * components metadata.
 *
 * @version $Revision: 1.3 $ $Date: 2005-03-31 09:41:41 $
 */
public interface Composable
{
    /**
     * Supply the component with ResourceLocator object
     * via which they can access any dependency value.
     *
     * @param locator the ResourceLocator
     * @throws MissingResourceException if the ResourceLocator does not
     *         contain all the required value
     */
    void compose( ResourceLocator locator )
        throws MissingResourceException;
}
