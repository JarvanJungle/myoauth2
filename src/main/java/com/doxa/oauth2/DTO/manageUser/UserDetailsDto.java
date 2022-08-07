package com.doxa.oauth2.DTO.manageUser;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

//import com.doxa.oauth2.DTO.financialInstitution.FinancialInstitutionDto;
//import com.doxa.oauth2.models.financialInstitution.FinancialInstitution;

@Getter
@Setter
@Data
@NoArgsConstructor
public class UserDetailsDto {
    private String name;
    private String email;
    private String id;
    private String workNumber;
    private String designation;
    private String countryCode;
    private Instant createdAt;
    private List<String> roles;
    private List<String> rbacRoles;
    private boolean isActive;
    private String remarks;
    private List<DisplayUserRoleCompanyDto> companies;
    private String avatarUrl;
    private String companyUuid;
    private String fiUuid;
//    private FinancialInstitutionDto financialInstitutionDto;
}
