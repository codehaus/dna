/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna;

/**
 * Components implement this interface to be supplied with a Logger.
 *
 * @version $Revision: 1.2 $ $Date: 2003-09-05 05:30:48 $
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
