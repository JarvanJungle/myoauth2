package com.doxa.oauth2.DTO.manageUser;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleCompanyDto {

    public String uuid;

    public String entityName;

    private String logoUrl;

    private boolean supplier;
    private boolean buyer;
    private boolean developer;

    public boolean main;

}
