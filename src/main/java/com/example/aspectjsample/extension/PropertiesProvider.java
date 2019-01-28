package com.example.aspectjsample.extension;

import java.util.Map;

public interface PropertiesProvider {

    Map<String, Object> getProperties();

    Object getProperty(String propertyName);

    boolean setProperty(String propertyName, Object value);

    boolean removeProperty(String propertyName);

    static PropertiesProvider of(Object o) {
        if (!(o instanceof PropertiesProvider)) {
            throw new ExtensionException(o.getClass() + " is not instance of PropertiesProvider!");
        }
        return (PropertiesProvider) o;
    }
}