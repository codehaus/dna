/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
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
