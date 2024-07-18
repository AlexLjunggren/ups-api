package io.ljunggren.ups.api.service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.http.Header;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import io.ljunggren.jsonUtils.JsonUtils;
import io.ljunggren.ups.api.UpsProperties;
import io.ljunggren.ups.api.model.AuthToken;
import io.ljunggren.ups.api.tracking.util.HttpUtils;

public class AuthorizationService {

    private UpsProperties upsProperties;
    
    public AuthorizationService(UpsProperties upsProperties) {
        this.upsProperties = upsProperties;
    }

    public AuthToken getAuthToken(String clientId, String clientSecret, String accountNumber) throws Exception {
        Header[] headers = generateHeaders(clientId, clientSecret, accountNumber);
        StringEntity entity = generateAuthEntity();
        String json = HttpUtils.post(upsProperties.getDomain() + "/security/v1/oauth/token", headers, entity);
        return JsonUtils.jsonToObject(json, AuthToken.class);
    }
    
    private Header[] generateHeaders(String clientId, String clientSecret, String accountNumber) throws UnsupportedEncodingException {
        String base64String = DatatypeConverter.printBase64Binary((clientId + ":" + clientSecret).getBytes("UTF-8"));
        return new Header[] { 
                new BasicHeader("x-merchant-id", accountNumber),
                new BasicHeader("content-type", "application/x-www-form-urlencoded"),
                new BasicHeader("authorization", "Basic " + base64String) 
        };
    }
    
    private StringEntity generateAuthEntity() throws UnsupportedEncodingException {
        List<BasicNameValuePair> urlParameters = Arrays.asList(
                new BasicNameValuePair("grant_type", "client_credentials")
        );
        return new UrlEncodedFormEntity(urlParameters);
    }
    
}
