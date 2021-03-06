/*
 * Copyright (C) The DNA Group. All rights reserved.
 *
 * This software is published under the terms of the DNA
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna;

import org.codehaus.dna.Logger;

/**
 *
 * @author Peter Donald
 * @version $Revision: 1.2 $ $Date: 2004-05-01 09:51:48 $
 */
class MockLogger
    implements Logger
{
    private final String m_name;

    MockLogger( String name )
    {
        m_name = name;
    }

    String getName()
    {
        return m_name;
    }

    public Logger getChildLogger( final String name )
    {
        return new MockLogger( getName() + "." + name );
    }

    public void trace( String message )
    {
    }

    public void trace( String message, Throwable throwable )
    {
    }

    public boolean isTraceEnabled()
    {
        return false;
    }

    public void debug( String message )
    {
    }

    public void debug( String message, Throwable throwable )
    {
    }

    public boolean isDebugEnabled()
    {
        return false;
    }

    public void info( String message )
    {
    }

    public void info( String message, Throwable throwable )
    {
    }

    public boolean isInfoEnabled()
    {
        return false;
    }

    public void warn( String message )
    {
    }

    public void warn( String message, Throwable throwable )
    {
    }

    public boolean isWarnEnabled()
    {
        return false;
    }

    public void error( String message )
    {
    }

    public void error( String message, Throwable throwable )
    {
    }

    public boolean isErrorEnabled()
    {
        return false;
    }
}
