package org.jcontainer.dna.impl;

class MockConsoleLogger
   extends ConsoleLogger
{
   public MockConsoleLogger()
   {
   }

   public MockConsoleLogger( final int level )
   {
      super( level );
   }

   boolean m_output;
   String m_type;
   String m_message;
   Throwable m_throwable;

   void doOutput( String type,
                  String message,
                  Throwable throwable )
   {
      m_output = true;
      m_type = type;
      m_message = message;
      m_throwable = throwable;
   }
}
