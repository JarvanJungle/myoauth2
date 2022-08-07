package com.doxa.oauth2.DTO.privateAPI;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailsAPIDto {

    public String name;

    public String email;

    public String uuid;

    public String workNumber;

    public String designation;
    
    public String countryCode;
}
