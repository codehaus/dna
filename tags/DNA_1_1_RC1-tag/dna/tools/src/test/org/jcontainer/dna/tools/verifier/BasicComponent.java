/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.tools.verifier;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.jcontainer.dna.Configurable;
import org.jcontainer.dna.Configuration;
import org.jcontainer.dna.ConfigurationException;
import org.jcontainer.dna.Composable;
import org.jcontainer.dna.ResourceLocator;
import org.jcontainer.dna.MissingResourceException;

/**
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.2 $ $Date: 2003-10-26 07:08:13 $
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
