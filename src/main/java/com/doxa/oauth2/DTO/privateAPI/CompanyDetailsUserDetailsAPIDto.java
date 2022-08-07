package com.doxa.oauth2.DTO.privateAPI;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDetailsUserDetailsAPIDto {

    private UserDetailsAPIDto userDetails;

    private CompanyDetailsAPIDto companyDetails;
}
