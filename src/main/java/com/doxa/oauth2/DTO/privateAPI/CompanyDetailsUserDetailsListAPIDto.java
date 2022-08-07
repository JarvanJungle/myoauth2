package com.doxa.oauth2.DTO.privateAPI;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDetailsUserDetailsListAPIDto {

    private List<CompanyDetailsUserDetailsAPIDto> companyUserDetailsList;
}
