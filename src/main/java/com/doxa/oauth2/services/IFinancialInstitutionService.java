//package com.doxa.oauth2.services;
//
//import javax.validation.Valid;
//
//import org.springframework.data.domain.PageRequest;
//
//import com.doxa.oauth2.DTO.financialInstitution.FinancialInstitutionDto;
//import com.doxa.oauth2.DTO.financialInstitution.FinancialInstitutionUpdateDto;
//import com.doxa.oauth2.DTO.financialInstitution.ListFinancialInstitution;
//import com.doxa.oauth2.exceptions.ObjectDoesNotExistException;
//import com.doxa.oauth2.responses.ApiResponse;
//
//public interface IFinancialInstitutionService {
//
//	ApiResponse createFinancialInstitution(@Valid FinancialInstitutionDto financialInstitutionDto);
//
//	ApiResponse updateFinancialInstitution(@Valid FinancialInstitutionUpdateDto financialInstitutionDto) throws ObjectDoesNotExistException, Exception;
//
//	ListFinancialInstitution findAllWithFilterV(String s, String q, PageRequest of) throws Exception;
//
//	FinancialInstitutionDto getFIById(Long id) throws Exception;
//
//	FinancialInstitutionDto getFIByLoggedInUser(String loggedInUserUuid) throws Exception;
//
//}
