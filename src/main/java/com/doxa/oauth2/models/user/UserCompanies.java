//package com.doxa.oauth2.models.user;
//
//import com.doxa.oauth2.models.companies.Companies;
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.List;
//import java.util.UUID;
//
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "user_companies", schema = "public")
//public class UserCompanies {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne()
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne()
//    @JoinColumn(name = "companies_id")
//    private Companies companies;
//
//    @Column(name = "user_companies_uuid")
//    private String userCompaniesUuid;
//
//    @OneToMany(targetEntity = UserRole.class, fetch = FetchType.LAZY, mappedBy = "userCompanies")
//    private List<UserRole> userRoles;
//
//    public UserCompanies(User user, Companies companies) {
//        this.user = user;
//        this.companies = companies;
//        this.userCompaniesUuid = UUID.randomUUID().toString();
//    }
//
//    @Override
//    public String toString() {
//        return "UserCompanies{" +
//                "user=" + user.getUserName() +
//                "company=" + companies.getEntityName() +
//                '}';
//    }
//}
