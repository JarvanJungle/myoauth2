//package com.doxa.oauth2.models.authorities;
//
//import com.doxa.oauth2.models.user.UserCompanies;
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
//@Table(name = "user_administratives", schema = "public")
//public class UserAdministratives {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne()
//    @JoinColumn(name = "user_companies_id")
//    private UserCompanies userCompanies;
//
//    @ManyToOne()
//    @JoinColumn(name = "administratives_id")
//    private Administratives administratives;
//}
