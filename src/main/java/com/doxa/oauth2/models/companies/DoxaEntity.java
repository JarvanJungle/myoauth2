//package com.doxa.oauth2.models.companies;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@Table(name = "entities", schema = "public")
//public class DoxaEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @Column(name = "uuid")
//    private String uuid;
//
//    //    //Newly added for Create Entity
//    @Column(name = "entity_registration_no")
//    private String companyRegistrationNumber;
//
////	@OneToMany (mappedBy="entityId", cascade = CascadeType.ALL)
////	@JsonIgnoreProperties("entityId")
////	private List<Companies> companiesList;
//
//    @OneToMany(mappedBy = "entityId", cascade = CascadeType.ALL)
//    @JsonIgnoreProperties("entityId")
//    private List<EntityRepresentative> entityRepresentativeList;
//
//}
