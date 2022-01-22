package io.ljunggren.ups.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.ljunggren.ups.api.tracking.response.Response;
import io.ljunggren.ups.api.tracking.response.TrackingResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class UpsResponse {

    @JsonProperty("trackResponse")
    private TrackingResponse trackingResponse;
    private Response response;
    
}
