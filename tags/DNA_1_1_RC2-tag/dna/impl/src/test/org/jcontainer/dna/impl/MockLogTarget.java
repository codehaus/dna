/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.impl;

import org.apache.log.LogEvent;
import org.apache.log.LogTarget;
import org.apache.log.Priority;

class MockLogTarget
    implements LogTarget
{
    boolean m_output;
    Priority m_priority;
    String m_message;
    Throwable m_throwable;

    public void processEvent( final LogEvent event )
    {
        m_output = true;
        m_priority = event.getPriority();
        m_message = event.getMessage();
        m_throwable = event.getThrowable();
    }
}
