/*
 * Copyright (C) The DNA Group. All rights reserved.
 *
 * This software is published under the terms of the DNA
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna;

import org.codehaus.dna.ReleaseUtil;

/**
 *
 * @author Peter Donald
 * @version $Revision: 1.2 $ $Date: 2004-05-01 09:51:48 $
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
