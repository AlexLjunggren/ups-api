package io.ljunggren.ups.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UpsApiTest {

    @Mock
    private CloseableHttpClient httpClient;
    
    @Mock
    private CloseableHttpResponse httpResponse;
    
    @Mock
    private HttpEntity httpEntity;
    
    @Mock
    private StatusLine statusLine;
    
    private String readFromResources(String file) throws IOException {
        return IOUtils.toString(this.getClass().getResourceAsStream(file), "UTF-8");
    }

    private void setup(String json, int responseCode) throws ClientProtocolException, IOException {
        InputStream content = new ByteArrayInputStream(json.getBytes());
        when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(responseCode);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(content);
    }
    
    @Test
    public void trackTest() throws IOException {
        String json = readFromResources("/trackingResponse.json");
        setup(json, 200);
        UpsEnvironment environment = UpsEnvironment.CIE;
        UpsApi upsApi = new UpsApi(environment, "username", "password", "accessKey");
        UpsResponse upsResponse = upsApi.track("12345", httpClient);
        assertNotNull(upsResponse.getTrackingResponse());
        assertNull(upsResponse.getErrorResponse());
    }
    
    @Test
    public void trackUnknownResponseCodeTest() throws ClientProtocolException, IOException {
        String json = "notRealResponse";
        setup(json, 407);
        UpsEnvironment environment = UpsEnvironment.CIE;
        UpsApi upsApi = new UpsApi(environment, "username", "password", "accessKey");
        UpsResponse upsResponse = upsApi.track("12345", httpClient);
        assertNull(upsResponse.getTrackingResponse());
        assertEquals("Unknown response code 407", upsResponse.getErrorResponse().getErrors().get(0).getMessage());
    }
    
    @Test
    public void trackExceptionThrownTest() throws ClientProtocolException, IOException {
        String json = "notRealResponse";
        setup(json, 200);
        UpsEnvironment environment = UpsEnvironment.CIE;
        UpsApi upsApi = new UpsApi(environment, "username", "password", "accessKey");
        UpsResponse upsResponse = upsApi.track("12345", httpClient);
        assertNull(upsResponse.getTrackingResponse());
        assertEquals("Unable to parse response", upsResponse.getErrorResponse().getErrors().get(0).getMessage());
    }
    
}