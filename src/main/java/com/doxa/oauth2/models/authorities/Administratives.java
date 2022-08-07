//package com.doxa.oauth2.models.authorities;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "administratives", schema = "public")
//public class Administratives {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "administrative_name")
//    private String administrativeName;
//
//    @Column(name = "administrative_code")
//    private String administrativeCode;
//
//    @ManyToOne()
//    @JoinColumn(name = "admin_categories_id")
//    private AdminCategories adminCategories;
//
//}
