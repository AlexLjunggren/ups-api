package io.ljunggren.ups.api.service;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import io.ljunggren.json.utils.JsonUtils;
import io.ljunggren.ups.api.UpsProperties;
import io.ljunggren.ups.api.UpsResponse;
import io.ljunggren.ups.api.tracking.util.HttpUtils;

public class TrackingService {
    
    private UpsProperties properties;
    
    public TrackingService(UpsProperties upsProperties) {
        this.properties = upsProperties;
    }

    public UpsResponse track(String trackingNumber, String bearerToken) throws Exception {
        Header[] headers = generateHeaders(bearerToken);
        String json = HttpUtils.get(properties.getDomain() + "/api/track/v1/details/" + trackingNumber, headers);
        return JsonUtils.jsonToObject(json, UpsResponse.class);
    }

    private Header[] generateHeaders(String bearerToken) throws UnsupportedEncodingException {
        return new Header[] { 
                new BasicHeader("transId", "testing"),
                new BasicHeader("transactionSrc", "tracking.ljunggren.io"),
                new BasicHeader("Authorization", "Bearer " + bearerToken) 
        };
    }
    
}
