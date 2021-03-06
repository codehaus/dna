/*
 * Copyright (C) The DNA Group. All rights reserved.
 *
 * This software is published under the terms of the DNA
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl;

/**
 * Abstract utility class for resources that can be "frozen"
 * and made read-only after being constructed.
 *
 * @version $Revision: 1.2 $ $Date: 2004-05-01 09:51:48 $
 */
abstract class AbstractFreezable
    implements Freezable
{
    /**
     * Flag indicating whether resource has been
     * made read-only yet.
     */
    private boolean m_readOnly;

    /**
     * Mark the resource as read only.
     */
    public void makeReadOnly()
    {
        m_readOnly = true;
    }

    /**
     * Check if the resource has been "frozen"
     * and thus is read only. If read-only then
     * throw an IllegalStateException.
     *
     * @throws IllegalStateException if resource is read-only
     */
    protected final void checkWriteable()
    {
        if( m_readOnly )
        {
            final String message =
                "Resource (" + this + ") is read only and can not be modified";
            throw new IllegalStateException( message );
        }
    }

    /**
     * Return true if resource has been made read-only or frozen.
     *
     * @return true if resource has been made read-only or
     *         frozen, false otherwise.
     */
    protected final boolean isReadOnly()
    {
        return m_readOnly;
    }
}
