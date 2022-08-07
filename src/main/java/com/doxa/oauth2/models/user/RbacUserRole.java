//package com.doxa.oauth2.models.user;
//
//import com.doxa.oauth2.models.authorities.core.RbacRole;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.*;
//
//import javax.persistence.*;
//
///**
// * @author vuducnoi
// */
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "rbac_user_role", schema = "public")
//public class RbacUserRole {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "role_id")
//	private RbacRole role;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "user_company_id")
//	@JsonIgnore
//	private UserCompanies userCompanies;
//
//}
