package org.jcontainer.dna.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.Assert;

class MockInvocationRecorder
   implements InvocationHandler
{
   private List m_invocations = new ArrayList();
   private int m_index;

   public void addInvocation( final Method method,
                              final Object[] args,
                              final Object result )
   {
      final InvocationRecord record = new InvocationRecord();
      record.m_method = method;
      record.m_args = args;
      record.m_result = result;
      m_invocations.add( record );
   }

   public Object invoke( final Object proxy,
                         final Method method,
                         final Object[] args )
      throws Throwable
   {
      InvocationRecord record = (InvocationRecord) m_invocations.get( m_index++ );
      if ( null == record )
      {
         Assert.fail( "Unexpected invocation " + method.getName() + " with args " + Arrays.asList( args ) +
                      " at index " + m_index + " when expecting " + m_invocations.size() + "invocations" );
      }
      Assert.assertEquals( "method", record.m_method, method );
      if ( args != null && record.m_args != null )
      {
         Assert.assertEquals( "args.length", record.m_args.length, args.length );
      }
      else if ( args == null && 0 != record.m_args.length )
      {
         Assert.fail( "Got empty args but expected " + Arrays.asList( record.m_args ) );
      }
      else if ( record.m_args == null && 0 != args.length )
      {
         Assert.fail( "Expected empty args but got " + Arrays.asList( args ) );
      }

      return record.m_result;
   }
}
