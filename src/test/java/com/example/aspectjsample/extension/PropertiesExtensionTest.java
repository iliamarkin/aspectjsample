package com.example.aspectjsample.extension;

import com.example.aspectjsample.bo.User;
import org.junit.Assert;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

public class PropertiesExtensionTest {

    @Test
    public void testGeneratePropertiesForBO() {
        final User user = new User();
        final PropertiesProvider propertiesProvider = PropertiesProvider.of(user);
        Assert.assertNotNull("Business object has 'null' properties!", propertiesProvider.getProperties());
    }

    @Test
    public void testEachBOHasDifferentPropertiesLink() {
        final User user = new User();
        final User user1 = new User();
        Assert.assertNotSame("Different business objects must not have the same properties!", PropertiesProvider.of(user), PropertiesProvider.of(user1));
    }

    @Test
    public void testEachBOImplementsPropertiesProvider() {
        final ConfigurationBuilder configuration = ConfigurationBuilder.build("com.example.aspectjsample.bo")
                .setScanners(new SubTypesScanner(false));

        final Set<Class<?>> allClasses = new Reflections(configuration)
                .getSubTypesOf(Object.class);

        final Set<Class<? extends PropertiesProvider>> propertiesProviderClasses = new Reflections(configuration)
                .getSubTypesOf(PropertiesProvider.class);

        if (allClasses.size() != propertiesProviderClasses.size()) {
            allClasses.removeAll(propertiesProviderClasses);
            Assert.fail("The following classes " + allClasses + " don't implement PropertiesProvider interface!");
        }
    }

    @Test
    public void testBOStoresProperties() {
        final User user = new User();

        final PropertiesProvider propertiesProvider = PropertiesProvider.of(user);
        final String propertyName = "test";
        propertiesProvider.removeProperty(propertyName);

        final int size = propertiesProvider.getProperties().size();

        propertiesProvider.setProperty(propertyName, propertyName);

        Assert.assertEquals("The size of a BO isn't incremented!", size + 1, propertiesProvider.getProperties().size());
        Assert.assertNotNull("The BO doesn't have test property!", propertiesProvider.getProperty(propertyName));
    }
}
