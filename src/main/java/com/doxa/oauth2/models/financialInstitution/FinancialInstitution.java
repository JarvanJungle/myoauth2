//package com.doxa.oauth2.models.financialInstitution;
//
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//import com.doxa.oauth2.models.user.User;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonInclude;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "financial_institution")
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class FinancialInstitution {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	@Column(name = "fi_code")
//	private String fiCode;
//	@Column(name = "fi_name")
//	private String fiName;
//	@Column(name = "full_name")
//	private String name;
//	@Column(name = "email")
//	private String email;
//	@Column(name = "work_phone")
//	private String workNumber;
//	@Column(name = "status")
//	private String status;
//	@Column(name = "remarks")
//	private String remarks;
//	@Column(name = "contact")
//	private String contact;
//	@Column(name = "country_code")
//	private String countryCode;
//	@Column(name = "designation")
//	private String designation;
//	@Column(name = "fi_portal")
//	private Boolean fiportal;
//	@Column(name = "developer_financing")
//	private Boolean developerFinancing;
//	@Column(name = "invoice_financing")
//	private Boolean invoiceFinancing;
//	@Column(name = "fi_uuid")
//	private String fiUuid;
//	@OneToMany(mappedBy = "financialInstitution", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	private List<User> users;
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinTable(name = "fi_project", joinColumns = @JoinColumn(name = "fi_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
//	private List<Project> projects;
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinColumn(name = "fi_id")
//	private List<FiCompany> companies;
//	@Column(name = "logo_url")
//	private String logoUrl;
//}