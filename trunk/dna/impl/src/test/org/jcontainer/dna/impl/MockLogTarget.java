package org.jcontainer.dna.impl;

import org.apache.log.LogTarget;
import org.apache.log.Priority;
import org.apache.log.LogEvent;

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
