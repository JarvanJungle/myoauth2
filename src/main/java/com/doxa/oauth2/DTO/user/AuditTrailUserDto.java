package com.doxa.oauth2.DTO.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditTrailUserDto {

    @JsonProperty
    public String name;

    @JsonProperty
    public String designation;

}
