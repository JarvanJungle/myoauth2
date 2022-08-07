//package com.doxa.oauth2.repositories.companies;
//
//import com.doxa.oauth2.models.companies.DoxaEntity;
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//
//public interface EntitiesRepository extends MongoRepository<DoxaEntity, Long>{
//	@Query("SELECT e FROM DoxaEntity  e WHERE UPPER(e.companyRegistrationNumber)=:companyRegistrationNumber")
//	DoxaEntity findByCompanyRegistrationNumber(String companyRegistrationNumber);
//	DoxaEntity findByUuid(String uuid);
//
//}
