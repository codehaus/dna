/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna;

/**
 * Components should implement this interface if they need to
 * be initialize resources at startup or deallocate resources
 * during shutdown.
 *
 * <p>If the {@link #initialize()} method is invoked upon a
 * component then the container must invoke the
 * {@link #dispose()} even if the {@link #initialize()} throws
 * an Exception.</p>
 *
 * @version $Revision: 1.1 $ $Date: 2004-04-18 20:13:47 $
 */
public interface Active
{
    /**
     * Initialialize the component.
     * This method gives the component the ability to
     * perform processing or allocate any resources
     * before the component becomes operational.
     *
     * @throws Exception if unable to initialize component.
     */
    void initialize()
        throws Exception;

    /**
     * Dispose the component.
     * This method gives the component the ability to
     * perform processing or deallocate any resources
     * before the component is destroyed.
     *
     * @throws Exception if unable to dispose component.
     */
    void dispose()
        throws Exception;
}
