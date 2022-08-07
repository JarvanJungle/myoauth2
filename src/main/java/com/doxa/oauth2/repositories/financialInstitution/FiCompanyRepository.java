//package com.doxa.oauth2.repositories.financialInstitution;
//
//import java.util.Optional;
//
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//import org.springframework.data.repository.query.Param;
//
//import com.doxa.oauth2.models.financialInstitution.FiCompany;
//
//public interface FiCompanyRepository extends MongoRepository<FiCompany, Long>{
//
//    @Query("SELECT a from FiCompany a where a.uuid = :uuid AND a.fiid = :fiid")
//	Optional<FiCompany> findByUuid(@Param("uuid") String uuid, @Param("fiid") Long fiid);
//
//}
