/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna;

/**
 *
 * @version $Revision: 1.1 $ $Date: 2003-07-25 11:34:35 $
 */
public interface ResourceLocator
{
    Object lookup( String key )
        throws MissingResourceException;

    boolean contains( String key );
}
