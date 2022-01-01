package com.ljunggren.ups.api;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.ljunggren.jsonUtils.JsonUtils;

public class UpsResponseTest {

    private String readFromResources(String file) throws IOException {
        return IOUtils.toString(this.getClass().getResourceAsStream(file), "UTF-8");
    }

    @Test
    public void serializeTrackingResponseTest() throws IOException {
        String json = readFromResources("/trackingResponse.json");
        UpsResponse response = JsonUtils.jsonToObject(json, UpsResponse.class);
        String serializedResponse = JsonUtils.objectToJson(response);
        assertTrue(JsonUtils.areEqual(json, serializedResponse));
    }

    @Test
    public void serializeTrackingNotFoundResponseTest() throws IOException {
        String json = readFromResources("/trackingNotFoundResponse.json");
        UpsResponse response = JsonUtils.jsonToObject(json, UpsResponse.class);
        String serializedResponse = JsonUtils.objectToJson(response);
        assertTrue(JsonUtils.areEqual(json, serializedResponse));
    }

    @Test
    public void serializeInvalidAuthenticationResponseTest() throws IOException {
        String json = readFromResources("/invalidAuthentication.json");
        UpsResponse response = JsonUtils.jsonToObject(json, UpsResponse.class);
        String serializedResponse = JsonUtils.objectToJson(response);
        assertTrue(JsonUtils.areEqual(json, serializedResponse));
    }

}
