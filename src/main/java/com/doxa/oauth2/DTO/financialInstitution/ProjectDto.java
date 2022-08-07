package com.doxa.oauth2.DTO.financialInstitution;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDto {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String projectCode;
    @JsonProperty
    private String projectTitle;
    @JsonProperty
    private String company;
    @JsonProperty
    private String projectStatus;
    @JsonProperty
    private String status;
    @JsonProperty
    private String uuid;
    @JsonProperty
    private String companyName;
    @JsonProperty
    private String companyUuid;
    @JsonProperty
    private String UserName;
    @JsonProperty
    private String userUuid;
}
