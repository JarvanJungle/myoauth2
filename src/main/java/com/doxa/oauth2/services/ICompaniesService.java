package com.doxa.oauth2.services;

import com.doxa.oauth2.DTO.manageCompany.CreateCompanyDetailsDto;
import com.doxa.oauth2.DTO.manageCompany.UpdateCompanyDto;
import com.doxa.oauth2.models.user.User;
import com.doxa.oauth2.responses.ApiResponse;

import java.util.List;
import java.util.Optional;

public interface ICompaniesService {

//    public ApiResponse listCompanies();

//    public ApiResponse createCompany(CreateCompanyDetailsDto createCompanyDetailsDto);
//
    public ApiResponse getCompanyDetails(String uuid);
//
//    public ApiResponse updateCompanies(UpdateCompanyDto updateCompanyDto);
//
//    public ApiResponse markCompanyDeleted(String uuid);
//
//    public ApiResponse reactivateCompany(String uuid);
//
//    public ApiResponse listAllCompaniesUnderEntity(String entityUuid);

    public Optional<User> getUser();

    public ApiResponse assignFeatureToCompany(String companyUuid, List<String> featureCodes) ;

}
