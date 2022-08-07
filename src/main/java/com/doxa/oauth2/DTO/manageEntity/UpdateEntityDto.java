package com.doxa.oauth2.DTO.manageEntity;

import com.doxa.oauth2.DTO.manageCompany.DocumentsMetaDataDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateEntityDto {

    private String entityName;

    private String uuid;

    private String gstNo;

    private String companyRegistrationNumber;

    private String entityType;

    private String industryType;

    @JsonProperty
    private boolean gstApplicable;

    private String country;

    private List<DocumentsMetaDataDto> documentsMetaDataList;

    private List<EntityRepresentativeDto> entityRepresentativeList;

    private List<String> features;

    @JsonProperty
    private boolean buyer;
    @JsonProperty
    private boolean supplier;
    @JsonProperty
    private boolean developer;

    private String remarks;
}
