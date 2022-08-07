//package com.doxa.oauth2.models.authorities.core;
//
//import com.doxa.oauth2.enums.RbacRoleStatus;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.time.Instant;
//import java.util.List;
//
///**
// * @author vuducnoi
// */
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "role", schema = "authority")
//public class RbacRole {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@JsonIgnore
//	private Long id;
//
//	private String uuid;
//
//	private String name;
//
//	private String companyUuid;
//
//	private String description;
//
//	@Enumerated(value = EnumType.STRING)
//	private RbacRoleStatus status;
//
//	private String createdBy;
//
//	private Instant createdAt;
//
//	@Column(name = "is_deleted")
//	private boolean deleted;
//
//	@OneToMany(mappedBy = "role")
//	private List<RolePermission> permissions;
//
//}
