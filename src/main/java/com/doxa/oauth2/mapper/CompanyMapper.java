package com.doxa.oauth2.mapper;

import com.doxa.oauth2.DTO.manageCompany.CompanyDetailDto;
import com.doxa.oauth2.DTO.manageCompany.CreateCompanyDetailsDto;
import com.doxa.oauth2.DTO.manageCompany.UpdateCompanyDto;
import com.doxa.oauth2.models.companies.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring"
//        uses = {DocumentsMetaDataMapper.class}
)
public interface CompanyMapper {

    Company createCompany(CreateCompanyDetailsDto createCompanyDetailsDto);

    Company updateCompany(UpdateCompanyDto updateCompanyDto);

    CompanyDetailDto companyDetailDto(Company company);
}
