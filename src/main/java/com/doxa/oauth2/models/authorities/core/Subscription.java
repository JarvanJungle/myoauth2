//package com.doxa.oauth2.models.authorities.core;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//
//import javax.persistence.*;
//import java.time.Instant;
//
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "subscription", schema = "authority")
//public class Subscription {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JsonIgnore
//    private Long id;
//
//    @JsonIgnore
//    private String uuid;
//
//
//    private String moduleCode;
//
//    private String featureCode;
//
//    private String featureName;
//
//    private Instant startDate;
//
//    private Instant endDate;
//
//    private String companyUuid;
//
//    private String fiUuid;
//
//    @OneToOne(targetEntity = Feature.class, cascade = CascadeType.ALL)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(referencedColumnName = "id", name = "feature_id")
//    private Feature feature;
//}
