/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl;

/**
 * This interface is used internally to DNA framework
 * implementation to indicate which classes can be "frozen"
 * and be made read-only after being mutable.
 *
 * @version $Revision: 1.1 $ $Date: 2004-04-18 20:13:45 $
 */
public interface Freezable
{
    /**
     * Make resource read-only.
     */
    void makeReadOnly();
}