package com.doxa.oauth2.DTO.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RoleDto {
    public String roleName;
    public String roleCode;

    public RoleDto() {
    }
    public RoleDto(String roleCode) {
        this.roleCode = roleCode;
    }
}
