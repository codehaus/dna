/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.codehaus.dna.tools.verifier;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.codehaus.dna.Composable;
import org.codehaus.dna.Configurable;
import org.codehaus.dna.Configuration;
import org.codehaus.dna.ConfigurationException;
import org.codehaus.dna.MissingResourceException;
import org.codehaus.dna.ResourceLocator;

/**
 *
 * @author Peter Donald
 * @version $Revision: 1.1 $ $Date: 2004-04-18 20:13:44 $
 */
public class BasicComponent
    implements ActionListener, Composable, Configurable
{
    public void compose( ResourceLocator locator )
        throws MissingResourceException
    {
    }

    public void configure( Configuration configuration )
        throws ConfigurationException
    {
    }

    public void actionPerformed( ActionEvent e )
    {
    }
}
