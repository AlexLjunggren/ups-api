package io.ljunggren.ups.api.model.tracking.response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity {

    private Location location;
    private Status status;
    private String date;
    private String time;
    
    @JsonIgnore
    public Date getTimestamp() {
        try {
            if (date == null || time == null) {
                return null;
            }
            return new SimpleDateFormat("yyyyMMddHHmmss").parse(date + time);
        } catch (ParseException e) {
            return null;
        }
    }
    
}
