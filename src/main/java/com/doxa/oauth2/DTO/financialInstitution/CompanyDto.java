package com.doxa.oauth2.DTO.financialInstitution;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyDto {
	@JsonProperty
	private Long id;
	@JsonProperty
	private String uuid;
	@JsonProperty
	private String entityName;
	@JsonProperty
	private String gstNo;
	@JsonProperty
	private boolean active;
	@JsonProperty
	private Instant createdAt;
	@JsonProperty
	private Instant updatedAt;
	@JsonProperty
	private String companyRegistrationNumber;
	@JsonProperty
	private Long fiid;
	@JsonProperty
	private String companyStatus;
}
