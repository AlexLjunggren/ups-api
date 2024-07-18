package io.ljunggren.ups.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthToken {

    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("issued_at")
    private long issuedAt;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("expires_in")
    private long expiresIn;
    @JsonProperty("refresh_count")
    private long refreshCount;
    @JsonProperty("status")
    private String status;

}
