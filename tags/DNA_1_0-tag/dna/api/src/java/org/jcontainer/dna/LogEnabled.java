/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna;

/**
 * Components implement this interface to be supplied with a Logger.
 *
 * @version $Revision: 1.4 $ $Date: 2003-09-23 08:10:14 $
 */
public interface LogEnabled
{
    /**
     * Supply the component with the logger.
     *
     * @param logger the logger.
     */
    void enableLogging( Logger logger );
}