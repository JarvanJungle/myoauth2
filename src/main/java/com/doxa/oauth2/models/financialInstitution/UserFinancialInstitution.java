//package com.doxa.oauth2.models.financialInstitution;
//
//import java.util.List;
//import java.util.UUID;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//import com.doxa.oauth2.models.user.User;
//import com.doxa.oauth2.models.user.UserCompanies;
//import com.doxa.oauth2.models.user.UserRole;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "user_financial_institution", schema = "public")
//public class UserFinancialInstitution {
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne()
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne()
//    @JoinColumn(name = "fi_id")
//    private FinancialInstitution financialInstitution;
//
//    @Column(name = "user_financialInstitution_uuid")
//    private String userFinancialInstitutionUuid;
//
//    @OneToMany(targetEntity = UserRole.class, fetch = FetchType.LAZY, mappedBy = "userFinancialInstitution")
//    private List<UserRole> userRoles;
//
//    public UserFinancialInstitution(User user, FinancialInstitution financialInstitution) {
//        this.user = user;
//        this.financialInstitution = financialInstitution;
//        this.userFinancialInstitutionUuid = UUID.randomUUID().toString();
//    }
//
//    @Override
//    public String toString() {
//        return "UserCompanies{" +
//                "user=" + user.getUserName() +
//                "financialInstitution=" + financialInstitution.getFiName() +
//                '}';
//    }}
