/*
 * Copyright (C) The DNA Group. All rights reserved.
 *
 * This software is published under the terms of the DNA
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna;

/**
 * Components implement this interface to be supplied with a Logger.
 *
 * @version $Revision: 1.2 $ $Date: 2004-05-01 09:51:48 $
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
