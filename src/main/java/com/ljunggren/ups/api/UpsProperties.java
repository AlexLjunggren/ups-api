package com.ljunggren.ups.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class UpsProperties {

    private static Properties properties = new Properties();
    
    private static final String API_TRACKING_URL = "api.tracking.url";

    public UpsProperties(UpsEnvironment environment) {
        Map<UpsEnvironment, String> fileMap = generateFileMap();
        setProperties(fileMap.get(environment));
    }
    
    private static Map<UpsEnvironment, String> generateFileMap() {
        Map<UpsEnvironment, String> fileMap = new HashMap<UpsEnvironment, String>();
        fileMap.put(UpsEnvironment.CIE, "/properties/cie/ups.properties");
        fileMap.put(UpsEnvironment.PRODUCTION, "/properties/production/ups.properties");
        return fileMap;
    }
    
    private static void setProperties(String file) {
        try {
            InputStream is = UpsProperties.class.getResourceAsStream(file);
            properties.load(is);
            is.close();
        } catch(IOException e) {
            throw new RuntimeException("Error loading properties", e);
        }
    }
    
    public String getTrackingUrl() {
        return properties.getProperty(API_TRACKING_URL);
    }
    
}
