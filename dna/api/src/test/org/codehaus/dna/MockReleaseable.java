/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna;

import org.codehaus.dna.ReleaseUtil;

/**
 *
 * @author Peter Donald
 * @version $Revision: 1.1 $ $Date: 2004-04-18 20:13:46 $
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
