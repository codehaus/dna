package org.codehaus.dna.impl.verifier;

import java.util.Set;
import org.codehaus.dna.Active;
import org.codehaus.dna.Composable;
import org.codehaus.dna.Configurable;
import org.codehaus.dna.Configuration;
import org.codehaus.dna.ConfigurationException;
import org.codehaus.dna.LogEnabled;
import org.codehaus.dna.Logger;
import org.codehaus.dna.MissingResourceException;
import org.codehaus.dna.ResourceLocator;
import org.codehaus.dna.annotation.Compatibility;
import org.codehaus.dna.annotation.ComponentDescriptor;
import org.codehaus.dna.annotation.ConfigurationDescriptor;
import org.codehaus.dna.annotation.DependencyDescriptor;
import org.codehaus.dna.annotation.DependencyDescriptorSet;
import org.codehaus.dna.annotation.ExtensionDescriptor;
import org.codehaus.dna.annotation.ExtensionDescriptorSet;
import org.codehaus.dna.annotation.LoggerDescriptor;
import org.codehaus.dna.annotation.LoggerDescriptorSet;
import org.codehaus.dna.annotation.ServiceDescriptor;
import org.codehaus.dna.annotation.ServiceDescriptorSet;

@ComponentDescriptor()
    @ServiceDescriptorSet({
    @ServiceDescriptor(type = MyService.class),
    @ServiceDescriptor(type = Active.class),
    @ServiceDescriptor(type = Object.class),
    @ServiceDescriptor(type = Set.class)
    } )
    @ExtensionDescriptorSet( {
    @ExtensionDescriptor(name = "mx", compatibility = Compatibility.SUPPORTS)
    } )
    public class MyComponent
    implements LogEnabled, Configurable, Composable, Active, MyService
{
    @LoggerDescriptorSet(value =
        {
        @LoggerDescriptor(name = "")
        }
        )
        public void enableLogging( Logger logger )
    {
    }

    @ConfigurationDescriptor(location = "MyComponent-schema.dtd")
        public void configure( Configuration configuration )
        throws ConfigurationException
    {
    }

    @DependencyDescriptorSet({
        @DependencyDescriptor(type = MyService.class),
        @DependencyDescriptor(type = MyService.class, qualifier = "Foo"),
        @DependencyDescriptor(type = MyService.class, key = "my-service", optional = true)
        }
        )
        public void compose( ResourceLocator locator )
        throws MissingResourceException
    {
    }

    public void initialize()
        throws Exception
    {
    }

    public void dispose()
        throws Exception
    {
    }
}
