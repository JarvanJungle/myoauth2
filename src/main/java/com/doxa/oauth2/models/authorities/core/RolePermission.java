//package com.doxa.oauth2.models.authorities.core;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
///**
// * @author vuducnoi
// */
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "role_permission", schema = "authority")
//public class RolePermission {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@JsonIgnore
//	private Long id;
//
//	private boolean read;
//
//	private boolean write;
//
//	private boolean approve;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "feature_id")
//	private Feature feature;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JsonIgnore
//	@JoinColumn(name = "role_id")
//	private RbacRole role;
//}
