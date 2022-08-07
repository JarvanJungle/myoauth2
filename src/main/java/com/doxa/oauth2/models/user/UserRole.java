//package com.doxa.oauth2.models.user;
//
//import com.doxa.oauth2.models.financialInstitution.UserFinancialInstitution;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.*;
//
//import javax.persistence.*;
//
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "user_roles", schema = "public")
//public class UserRole {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "role_id")
//    private Role role;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_companies_id")
//    private UserCompanies userCompanies;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_financial_institution_id")
//    private UserFinancialInstitution userFinancialInstitution;
//
//    public UserRole(Role role) {
//        this.role = role;
//    }
//
//    public boolean isAdminRole() {
//        return role.getRoleCode().equals("ENTITY_ADMIN") || role.getRoleCode().equals("COMPANY_ADMIN");
//    }
//}
