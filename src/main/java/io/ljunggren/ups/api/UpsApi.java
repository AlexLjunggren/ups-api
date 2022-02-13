package io.ljunggren.ups.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.ljunggren.jsonUtils.JsonUtils;
import io.ljunggren.ups.api.tracking.response.MessageCode;
import io.ljunggren.ups.api.tracking.response.Response;

public class UpsApi {

    private String username;
    private String password;
    private String accessKey;
    private UpsProperties properties;
    
    public static final List<Integer> KNOWN_REQUEST_CODES = Arrays.asList(new Integer[] {
            200, 400, 401, 404, 405, 500, 503
    });
    
    public UpsApi(UpsEnvironment environment, String username, String password, String accessKey) {
        this.username = username;
        this.password = password;
        this.accessKey = accessKey;
        this.properties = new UpsProperties(environment);
    }
    
    public UpsResponse track(String trackingNumber) throws IOException {
        return track(trackingNumber, HttpClients.createDefault());
    }
    
    // package private for unit testing
    UpsResponse track(String trackingNumber, CloseableHttpClient httpClient) throws IOException {
        String url = properties.getTrackingUrl().replace("{inquiryNumber}", trackingNumber);
        HttpGet get = new HttpGet(url);
        Header[] headers = new Header[] {
                new BasicHeader("username", username),
                new BasicHeader("password", password),
                new BasicHeader("accessLicenseNumber", accessKey),
                new BasicHeader("content-type", "application/json"),
                new BasicHeader("accept", "application/json")
        };
        try {
            get.setHeaders(headers);
            CloseableHttpResponse httpResponse = httpClient.execute(get);
            int responseCode = httpResponse.getStatusLine().getStatusCode();
            if (KNOWN_REQUEST_CODES.contains(responseCode)) {
                String json = getResult(httpResponse);
                UpsResponse response = parse(json, UpsResponse.class);
                return response;
            }
            return generateErrorReponse("Tracking Error", String.format("Unknown response code %d", responseCode));
        } catch (Exception e) {
            return generateErrorReponse("Tracking Error", e.getMessage());
        } finally {
            httpClient.close();
        }
    }
    
    private UpsResponse generateErrorReponse(String code, String message) {
        UpsResponse upsResponse = new UpsResponse();
        Response response = new Response().addError(new MessageCode(code, message));
        upsResponse.setErrorResponse(response);
        return upsResponse;
    }
    
    private <T> T parse(String json, Class<T> clazz) throws Exception {
        try {
            return JsonUtils.jsonToObject(json, clazz);
        } catch (JsonProcessingException e) {
            throw new Exception("Unable to parse response");
        }
    }
    
    private static String getResult(HttpResponse response) throws UnsupportedOperationException, IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line = new String();
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }
    
}
