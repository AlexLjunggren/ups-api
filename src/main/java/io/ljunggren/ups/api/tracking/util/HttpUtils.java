package io.ljunggren.ups.api.tracking.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpUtils {
    
    public static String get(String url, Header[] headers) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        if (headers != null && headers.length > 0) {
            httpGet.setHeaders(headers);
        }
        return execute(httpGet);
    }
    
    public static String post(String url, Header[] headers) throws Exception {
        return post(url, headers, null);
    }

    public static String post(String url, Header[] headers, StringEntity entity) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        if (headers != null && headers.length > 0) {
            httpPost.setHeaders(headers);
        }
        if (entity != null) {
            httpPost.setEntity(entity);
        }
        return execute(httpPost);
    }
    
    private static String execute(HttpRequestBase httpRequest) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            CloseableHttpResponse response = client.execute(httpRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new Exception(String.format("Response code %d returned for url: %s", 
                        response.getStatusLine().getStatusCode(), httpRequest.getURI()));
            }
            return getResult(response);
        } finally {
            client.close();
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
