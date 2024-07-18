package io.ljunggren.ups.api.tracking.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Package {

    private String trackingNumber;
    @JsonProperty("deliveryDate")
    private List<DeliveryDate> deliveryDates;
    private DeliveryTime deliveryTime;
    @JsonProperty("activity")
    private List<Activity> activities;
    
}
