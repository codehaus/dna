/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna;

/**
 * This is the interface via which component
 * resources can be accessed via keys.
 *
 * @version $Revision: 1.4 $ $Date: 2003-09-23 08:10:14 $
 */
public interface ResourceLocator
{
    /**
     * Return resource registered with specified key.
     *
     * @param key the key
     * @return the resource
     * @throws MissingResourceException if unable to locate
     *         resource with specified key
     */
    Object lookup( String key )
        throws MissingResourceException;

    /**
     * Return true if a resource exists with specified key.
     *
     * @param key the key
     * @return true if a resource exists with specified key.
     */
    boolean contains( String key );
}
