package io.ljunggren.ups.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import io.ljunggren.ups.api.model.AuthToken;
import io.ljunggren.ups.api.service.AuthorizationService;
import io.ljunggren.ups.api.service.TrackingService;

public class UpsApi {

    private AuthorizationService authorizationService;
    private TrackingService trackingService;
    
    private String clientId;
    private String clientSecret;
    private String accountNumber;
    private UpsProperties properties;
    
    public UpsApi(UpsEnvironment environment, String clientId, String clientSecret, String accountNumber) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accountNumber = accountNumber;
        this.properties = new UpsProperties(environment);
        this.authorizationService = new AuthorizationService(properties);
        this.trackingService = new TrackingService(properties);
    }
    
    public UpsResponse track(String trackingNumber) throws Exception {
        AuthToken authToken = getAuthToken();
        return track(trackingNumber, authToken);
    }
    
    public UpsResponse track(String trackingNumber, AuthToken authToken) throws Exception {
        if (!isValid(trackingNumber)) {
            return null;
        }
        
        String bearerToken = authToken.getAccessToken();
        return trackingService.track(trackingNumber, bearerToken);
    }
    
    public AuthToken getAuthToken() throws Exception {
        return authorizationService.getAuthToken(clientId, clientSecret, accountNumber);
    }
    
    private boolean isValid(String trackingNumber) {
        if (StringUtils.isBlank(trackingNumber)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher matcher = pattern.matcher(trackingNumber);
        return matcher.find();
    }
    
}
