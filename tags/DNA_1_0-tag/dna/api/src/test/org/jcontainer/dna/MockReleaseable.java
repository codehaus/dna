/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna;

/**
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.2 $ $Date: 2003-09-23 08:10:14 $
 */
class MockReleaseable
    implements ReleaseUtil.Releaseable
{
    private boolean m_released;

    public void release()
    {
        m_released = true;
    }

    public boolean isReleased()
    {
        return m_released;
    }
}
