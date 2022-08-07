package com.doxa.oauth2.repositories.companies;

import com.doxa.oauth2.models.companies.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends MongoRepository <Company, String>{

//	@Query("SELECT c from Companies c where upper(c.companyRegistrationNumber)=:companyRegistrationNumber")
//	Company findByCompanyRegistrationNumber (String companyRegistrationNumber);

//	@Query("SELECT c FROM Companies c WHERE c.uuid =:companyUuid")
//	Company findByUuid (@Param("companyUuid") String companyUuid);

	//@Query("SELECT c FROM Companies c WHERE c.entity.id =:entityId")
//	List<Company> findCompaniesByEntityId(@Param("entityId") Long id);

	//	@Query("SELECT c FROM Companies c WHERE c.entity.id =:entityId AND c.mainCompany is true")
//	Company findDefaultCompanyByEntityId(@Param("entityId") Long id);

	//@Query("SELECT c FROM Companies c WHERE c.entity.id =:entityId")
//	List<Company> findAllCompaniesByEntityId(@Param("entityId") Long id);

	//@Query("SELECT c.id FROM Companies c WHERE c.uuid =:companyUuid")
//	Long findIdByUuid(@Param("companyUuid") String companyUuid);

	//@Query("SELECT c from Companies c where upper(c.companyRegistrationNumber)=:companyRegistrationNumber and upper(c.country) =:country")
//	Company findByCompanyRegistrationNumberAndCountry (String companyRegistrationNumber, String country);
	@Query(value = "{ 'registrationNo' : ?0, 'addressDetails.$address.$country' : ?1 }")
	Company findByCompanyRegistrationNumberAndCountry (String companyRegistrationNumber, String country);

}
