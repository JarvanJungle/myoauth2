package com.doxa.oauth2.DTO.manageUser;

import com.doxa.oauth2.models.companies.Company;
import com.doxa.oauth2.models.user.Role;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
public class UserCreateDto {

    public String name;
    public String email;
    public String phone;
    public String password;

//    //temp while company DB is being set up
//    public List<SimpleCompanyDto> companies;

//    public List<DisplayUserRoleCompanyDto> companiesRoles;
    public DisplayUserRoleCompanyDto company;
}
