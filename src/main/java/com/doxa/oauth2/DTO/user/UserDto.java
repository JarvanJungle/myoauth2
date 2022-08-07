package com.doxa.oauth2.DTO.user;

import com.doxa.oauth2.DTO.manageUser.DisplayUserRoleCompanyDto;
import com.doxa.oauth2.config.Configs;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Data
public class UserDto {
    public Long id;
    public String name;
    public String email;
    public String uuid;
    public String designation;
    public UserSettingDto settings;
    public Set<RoleDto> roles;
    public boolean active;
    public boolean deleted;
    public List<DisplayUserRoleCompanyDto> companies;
    public UserDto() {
    }

    public Collection<? extends SimpleGrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleCode()))
                .collect(Collectors.toSet());
    }

    public Set<String> getRoles() {
        return roles.stream().map(RoleDto::getRoleCode).collect(Collectors.toSet());
    }

    public boolean isDoxaAdminOfCompany(String companyUuid) {
        for (DisplayUserRoleCompanyDto companyDto : companies) {
            List<String> roles = companyDto.getRole();
            if (companyDto.getCompanyUuid().equals(companyUuid) && roles.contains(Configs.DOXA_ADMIN.getValue())) {
                return true;
            }
        }
        return false;
    }

    public boolean isAdminOfCompany(String companyUuid) {
        for (DisplayUserRoleCompanyDto companyDto : companies) {
            List<String> roles = companyDto.getRole();
            if (companyDto.getCompanyUuid().equals(companyUuid) && roles.contains(Configs.COMPANY_ADMIN.getValue())) {
                return true;
            }
        }
        return false;
    }

    public boolean isEntityAdminOfCompany(String companyUuid) {
        for (DisplayUserRoleCompanyDto companyDto : companies) {
            List<String> roles = companyDto.getRole();
            if (companyDto.getCompanyUuid().equals(companyUuid) && roles.contains(Configs.ENTITY_ADMIN.getValue())) {
                return true;
            }
        }
        return false;
    }
}
