package com.tcmonitor.server.message;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;
import java.util.Properties;

public class SerializableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

    public Properties getAllProperties(Locale locale) {
        clearCacheIncludingAncestors();
        Properties properties = getMergedProperties(locale).getProperties();
        if (properties.isEmpty()) {
            return getMergedProperties(Locale.ENGLISH).getProperties();
        }
        return properties;
    }
}
