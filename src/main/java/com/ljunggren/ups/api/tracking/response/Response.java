package com.ljunggren.ups.api.tracking.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Response {

    private List<MessageCode> errors;
    
    public Response addError(MessageCode error) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(error);
        return this;
    }
    
}
