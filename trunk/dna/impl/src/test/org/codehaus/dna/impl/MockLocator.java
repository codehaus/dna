/*
 * Copyright (C) The JContainer Group. All rights reserved.
 *
 * This software is published under the terms of the JContainer
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.impl;

import org.xml.sax.Locator;

class MockLocator
    implements Locator
{
    private final String _systemId;
    private int _lineNumber = -1;
    private int _columnNumber = -1;

    public MockLocator( final String systemId )
    {
        _systemId = systemId;
    }

    public String getPublicId()
    {
        return null;
    }

    public String getSystemId()
    {
        return _systemId;
    }

    public int getLineNumber()
    {
        return _lineNumber;
    }

    public void setLineNumber( int lineNumber )
    {
        _lineNumber = lineNumber;
    }

    public int getColumnNumber()
    {
        return _columnNumber;
    }

    public void setColumnNumber( int columnNumber )
    {
        _columnNumber = columnNumber;
    }
}
