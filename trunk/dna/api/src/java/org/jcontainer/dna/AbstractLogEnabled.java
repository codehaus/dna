/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna;

/**
 *
 * @version $Revision: 1.1 $ $Date: 2003-07-27 10:38:27 $
 */
public class AbstractLogEnabled
    implements LogEnabled
{
    private Logger m_logger;

    public void enableLogging( final Logger logger )
    {
        m_logger = logger;
    }

    protected final Logger getLogger()
    {
        return m_logger;
    }

    protected final void setupLogger( final Object object )
    {
        setupLogger( object, getLogger() );
    }

    protected final void setupLogger( final Object object,
                                      final String name )
    {
        if( null == name )
        {
            throw new NullPointerException( "name" );
        }
        final Logger childLogger = getLogger().getChildLogger( name );
        setupLogger( object, childLogger );
    }

    private final void setupLogger( final Object object, final Logger logger )
    {
        if( object instanceof LogEnabled )
        {
            ( (LogEnabled)object ).enableLogging( logger );
        }
    }
}
