package com.doxa.oauth2.DTO.manageEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntityRepresentativeDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String workNumber;

    @NotEmpty
    private String countryCode;

    @NotEmpty
    private String userRole;
}
