/*
 * Copyright (C) The Spice Group. All rights reserved.
 *
 * This software is published under the terms of the Spice
 * Software License version 1.1, a copy of which has been included
 * with this distribution in the LICENSE.txt file.
 */
package org.jcontainer.dna.tools.verifier;

import org.jcontainer.dna.Configurable;
import org.jcontainer.dna.Parameterizable;
import org.jcontainer.dna.Configuration;
import org.jcontainer.dna.ConfigurationException;
import org.jcontainer.dna.Parameters;
import org.jcontainer.dna.ParameterException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *
 * @author <a href="mailto:peter at realityforge.org">Peter Donald</a>
 * @version $Revision: 1.1 $ $Date: 2003-10-25 15:03:06 $
 */
class IncompatibleLifecycles
    implements Configurable, Parameterizable, ActionListener
{
    public void actionPerformed( ActionEvent e )
    {
    }

    public void configure( Configuration configuration )
        throws ConfigurationException
    {
    }

    public void parameterize( Parameters parameters )
        throws ParameterException
    {
    }
}
