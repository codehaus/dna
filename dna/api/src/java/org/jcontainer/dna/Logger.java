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
 * @version $Revision: 1.1 $ $Date: 2003-07-25 11:34:35 $
 */
public interface Logger
{
    void trace( String message );

    void trace( String message, Throwable throwable );

    boolean isTraceEnabled();

    void debug( String message );

    void debug( String message, Throwable throwable );

    boolean isDebugEnabled();

    void notice( String message );

    void notice( String message, Throwable throwable );

    boolean isNoticeEnabled();

    void warn( String message );

    void warn( String message, Throwable throwable );

    boolean isWarnEnabled();

    void error( String message );

    void error( String message, Throwable throwable );

    boolean isErrorEnabled();
}
