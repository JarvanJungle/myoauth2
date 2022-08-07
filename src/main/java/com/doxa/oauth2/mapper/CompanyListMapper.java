package com.doxa.oauth2.mapper;


import com.doxa.oauth2.DTO.manageCompany.DisplayCompanyListDto;
import com.doxa.oauth2.models.companies.Company;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyListMapper {
    DisplayCompanyListDto displayCompanyListDto(Company company);

    List<DisplayCompanyListDto> mapDisplayCompanyListDto(List<Company> companyList);
}
