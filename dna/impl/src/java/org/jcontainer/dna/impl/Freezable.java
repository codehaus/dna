/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.impl;

/**
 * This interface is used internally to DNA framework
 * implementation to indicate which classes can be "frozen"
 * and be made read-only after being mutable.
 *
 * @version $Revision: 1.3 $ $Date: 2003-09-23 08:10:14 $
 */
public interface Freezable
{
   /**
    * Make resource read-only.
    */
   void makeReadOnly();
}
