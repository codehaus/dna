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
 * @version $Revision: 1.2 $ $Date: 2003-09-05 04:05:53 $
 */
public interface Active
{
    void initialize()
        throws Exception;

    void dispose()
        throws Exception;
}
