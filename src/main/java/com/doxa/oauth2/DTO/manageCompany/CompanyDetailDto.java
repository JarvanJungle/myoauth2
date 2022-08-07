package com.doxa.oauth2.DTO.manageCompany;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDetailDto {

    private String uuid;

    private String entityName;

    private String gstNo;

    private String companyRegistrationNumber;

    private String entityType;

    private String industryType;

    @JsonProperty
    private boolean gstApplicable;

    private String country;

    private String countryCurrency;

    private List<DocumentsMetaDataDto> documentsMetaDataList;

    @JsonProperty
    private boolean active;
    @JsonProperty
    private boolean buyer;
    @JsonProperty
    private boolean supplier;
    @JsonProperty
    private boolean developer;

    private String logoUrl;

    private List<String> features;

    private String remarks;

    private String countryCode;

    private String contactPersonEmail;

    private String contactPersonName;

    private String contactPersonWorkNumber;
}
