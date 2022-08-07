//package com.doxa.oauth2.repositories.financialInstitution;
//
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//
//import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.data.repository.query.Param;
//
//import com.doxa.oauth2.models.financialInstitution.FinancialInstitution;
//
//public interface FinancialInstitutionRepository extends PagingAndSortingRepository<FinancialInstitution, Long>, JpaSpecificationExecutor<FinancialInstitution> {
//
//	@Query("SELECT FI FROM FinancialInstitution FI WHERE FI.fiName = :fiName")
//	FinancialInstitution findByName(@Param("fiName") String fiName);
//
//	@Query("SELECT FI FROM FinancialInstitution FI WHERE FI.fiUuid = :fiUuid")
//	FinancialInstitution findByUuid(@Param("fiUuid") String fiUuid);
//}
