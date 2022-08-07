//package com.doxa.oauth2.models.authorities.core;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "user_privileges", schema = "authority")
//public class UserPrivilege {
//
//    @Id
//    @JsonIgnore
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @JsonIgnore
//    private String uuid;
//
//    @JsonIgnore
//    private String userUuid;
//
//    @JsonIgnore
//    private String companyUuid;
//
//    @OneToOne(targetEntity = Feature.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    @JoinColumn(referencedColumnName = "id", name = "feature_id")
//    private Feature feature;
//
//    private String featureCode;
//
//    @OneToOne(targetEntity = UserPrivilegeAction.class, cascade = CascadeType.ALL)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(referencedColumnName = "id", name = "privilege_action_id")
//    private UserPrivilegeAction actions;
//
//    @JsonIgnore
//    private String fiUuid;
//}
