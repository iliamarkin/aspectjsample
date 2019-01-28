package com.example.aspectjsample.extension;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Aspect
public class PropertiesExtension {

    public static class PropertiesProviderImpl implements PropertiesProvider {

        private final Map<String, Object> properties = new HashMap<>(0);

        @Override
        public Map<String, Object> getProperties() {
            return this.properties;
        }

        @Override
        public Object getProperty(final String propertyName) {
            return this.properties.get(propertyName);
        }

        @Override
        public boolean setProperty(final String propertyName, final Object value) {
            return this.properties.put(propertyName, value) == null;
        }

        @Override
        public boolean removeProperty(final String propertyName) {
            return this.properties.remove(propertyName) != null;
        }
    }

    @DeclareParents(value = "com.example.aspectjsample.bo..*", defaultImpl = PropertiesProviderImpl.class)
    private PropertiesProvider implementedInterface;
}
