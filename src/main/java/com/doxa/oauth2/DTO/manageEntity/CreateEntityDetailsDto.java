package com.doxa.oauth2.DTO.manageEntity;

import com.doxa.oauth2.DTO.manageCompany.DocumentsMetaDataDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateEntityDetailsDto {

    @NotEmpty
    private String entityName;

    @NotEmpty
    private String gstNo;

    @NotEmpty
    private String companyRegistrationNumber;

    @NotEmpty
    private String entityType;

    @NotEmpty
    private String industryType;

    @NotNull
    @JsonProperty
    private boolean gstApplicable;

    @NotEmpty
    private String country;

    @NotEmpty
    private String countryCurrency;

    private List<DocumentsMetaDataDto> documentsMetaDataList;

    @NotNull
    private List<@Valid EntityRepresentativeDto> entityRepresentativeList;

    private List<String> features;

    @JsonProperty
    private boolean buyer;
    @JsonProperty
    private boolean supplier;
    @JsonProperty
    private boolean developer;

    private String remarks;

}
