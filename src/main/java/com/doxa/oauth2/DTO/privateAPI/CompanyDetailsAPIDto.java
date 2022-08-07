package com.doxa.oauth2.DTO.privateAPI;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDetailsAPIDto {

    private String uuid;

    private String entityName;

    private String gstNo;

    private boolean active;

    private Instant createdAt;

    private Instant updatedAt;

    private String companyRegistrationNumber;

    private String entityType;

    private String industryType;

    private boolean gstApplicable;

    private String country;

    private boolean mainCompany;

    private String onboardingStatus;

    private String logoUrl;
}
