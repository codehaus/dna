/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna;

/**
 * Abstract utility class that components can extend to
 * make it easy to implement logging.
 *
 * @version $Revision: 1.1 $ $Date: 2004-04-18 20:13:47 $
 */
public abstract class AbstractLogEnabled
    implements LogEnabled
{
    /**
     * The components logger.
     */
    private Logger m_logger;

    /**
     * Set the components logger.
     *
     * @param logger the logger
     */
    public void enableLogging( final Logger logger )
    {
        m_logger = logger;
    }

    /**
     * Return the components logger.
     *
     * @return the components logger.
     */
    protected final Logger getLogger()
    {
        return m_logger;
    }

    /**
     * Utility method to setup specified object
     * with current components logger.
     *
     * @param object the object
     */
    protected final void setupLogger( final Object object )
    {
        setupLogger( object, getLogger() );
    }

    /**
     * Utility method to setup specified object
     * with a child logger of components current
     * logger with specified name.
     *
     * @param object the object
     * @param name the name of child logger
     */
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

    /**
     * Internal implementation method to setup object
     * with specified logger. If the object implements
     * {@link LogEnabled} it will be supplied with logger
     * via the {@link LogEnabled} interface.
     *
     * @param object the object
     * @param logger the logger
     */
    private final void setupLogger( final Object object, final Logger logger )
    {
        if( object instanceof LogEnabled )
        {
            ( (LogEnabled)object ).enableLogging( logger );
        }
    }
}
