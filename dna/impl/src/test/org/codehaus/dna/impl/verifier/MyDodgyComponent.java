package org.codehaus.dna.impl.verifier;

import org.codehaus.dna.Configurable;
import org.codehaus.dna.Configuration;
import org.codehaus.dna.ConfigurationException;
import org.codehaus.dna.annotation.ConfigurationDescriptor;

abstract class MyDodgyComponent
    implements Configurable
{
    int m_myField;

    protected MyDodgyComponent(int myParameter)
    {
        m_myField = myParameter;
    }

    @ConfigurationDescriptor(location = "NoExist.dtd")
    public void configure( Configuration configuration )
        throws ConfigurationException
    {
    }
}
