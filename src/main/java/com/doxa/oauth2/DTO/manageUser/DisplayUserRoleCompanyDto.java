package com.doxa.oauth2.DTO.manageUser;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Data
public class DisplayUserRoleCompanyDto {

    public List<String> role;
    public String companyName;
    public String companyId;
    public String companyType;

    private String logoUrl;
    public boolean main;
    public String companyUuid;
    private boolean supplier;
    private boolean buyer;
    private boolean developer;

    public DisplayUserRoleCompanyDto() {
        super();
    }

    public DisplayUserRoleCompanyDto(List<String> role, String companyName, String companyUuid) {
        this.role = role;
        this.companyName = companyName;
        this.companyUuid = companyUuid;
    }

    public DisplayUserRoleCompanyDto(List<SimpleRoleDto> role, SimpleCompanyDto companies) {
        this.role = role.stream().map(SimpleRoleDto::getRoleCode).collect(Collectors.toList());
        this.companyName = companies.getEntityName();
        this.companyUuid = companies.getUuid();
        this.supplier = companies.isSupplier();
        this.buyer = companies.isBuyer();
        this.developer = companies.isDeveloper();
        this.main = companies.isMain();
        this.logoUrl = companies.getLogoUrl();
    }
}
