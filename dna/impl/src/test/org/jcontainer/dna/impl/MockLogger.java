package org.jcontainer.dna.impl;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.LogRecord;

class MockLogger
   extends Logger
{
   boolean m_output;
   Level m_priority;
   String m_message;
   Throwable m_throwable;

   public MockLogger( final Level level )
   {
      super( "test", null );
      setLevel( level );
   }

   public void log( final LogRecord record )
   {
      m_output = true;
      m_priority = record.getLevel();
      m_message = record.getMessage();
      m_throwable = record.getThrown();
   }
}
