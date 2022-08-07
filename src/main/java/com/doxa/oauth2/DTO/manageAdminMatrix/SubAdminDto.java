package com.doxa.oauth2.DTO.manageAdminMatrix;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubAdminDto {
    @NotEmpty
    String userUuid;
    String companyUuid;
    List<AdministrativesDto> administrativesDtoList;
}
