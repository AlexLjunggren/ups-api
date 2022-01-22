package io.ljunggren.ups.api;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class UpsPropertiesTest {

    @Test
    public void getTrackingUrlCieTest() {
        UpsProperties properties = new UpsProperties(UpsEnvironment.CIE);
        String url = properties.getTrackingUrl();
        assertNotNull(url);
    }

    @Test
    public void getTrackingUrlProductionTest() {
        UpsProperties properties = new UpsProperties(UpsEnvironment.PRODUCTION);
        String url = properties.getTrackingUrl();
        assertNotNull(url);
    }

}
