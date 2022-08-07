package com.doxa.oauth2.DTO.manageEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleEntityDto {
    private String entityName;
}
