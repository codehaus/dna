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
 * @todo initialize --> init?
 * @todo dispose --> destroy?
 * @todo should we support separate start/stop???
 */
public interface Active
{
    void initialize()
        throws Exception;

    void dispose()
        throws Exception;
}