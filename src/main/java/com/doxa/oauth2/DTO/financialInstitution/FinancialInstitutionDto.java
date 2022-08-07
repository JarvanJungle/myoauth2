//package com.doxa.oauth2.DTO.financialInstitution;
//
//import java.util.List;
//
//import javax.persistence.Column;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//@Data
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class FinancialInstitutionDto {
//	@JsonProperty
//	private Long id;
//	@JsonProperty
//	private String fiCode;
//	@JsonProperty
//	private String fiName;
//	@JsonProperty
//	private String status;
//	@JsonProperty
//	private Boolean fiportal;
//	@JsonProperty
//	private Boolean developerFinancing;
//	@JsonProperty
//	private Boolean invoiceFinancing;
//	@JsonProperty
//	private String fiUuid;
//	@JsonProperty
//	private List<FiUserDto> users;
//	@JsonProperty
//	private List<ProjectDto> projects;
//	@JsonProperty
//	private List<CompanyDto> companies;
//	@JsonProperty
//	private List<String> features;
//	@JsonProperty
//	private String logoUrl;
//}
