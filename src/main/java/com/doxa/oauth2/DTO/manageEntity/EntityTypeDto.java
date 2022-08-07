package com.doxa.oauth2.DTO.manageEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntityTypeDto {

    private String entityType;

}
