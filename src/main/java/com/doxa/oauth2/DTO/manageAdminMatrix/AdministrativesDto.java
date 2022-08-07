package com.doxa.oauth2.DTO.manageAdminMatrix;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdministrativesDto {
    private String administrativeCode;
    private AdminCategoriesDto adminCategories;
}
